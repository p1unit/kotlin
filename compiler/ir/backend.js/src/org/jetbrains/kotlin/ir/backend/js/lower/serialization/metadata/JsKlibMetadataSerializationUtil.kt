/*
 * Copyright 2010-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ir.backend.js.lower.serialization.metadata

import org.jetbrains.kotlin.config.AnalysisFlags
import org.jetbrains.kotlin.config.LanguageVersionSettings
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.incremental.components.LookupTracker
import org.jetbrains.kotlin.incremental.components.NoLookupLocation
import org.jetbrains.kotlin.metadata.ProtoBuf
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.protobuf.CodedInputStream
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker
import org.jetbrains.kotlin.resolve.descriptorUtil.filterOutSourceAnnotations
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameSafe
import org.jetbrains.kotlin.resolve.descriptorUtil.module
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter
import org.jetbrains.kotlin.resolve.scopes.MemberScope
import org.jetbrains.kotlin.serialization.AnnotationSerializer
import org.jetbrains.kotlin.serialization.DescriptorSerializer
import org.jetbrains.kotlin.serialization.StringTableImpl
import org.jetbrains.kotlin.serialization.deserialization.DeserializationConfiguration
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedPropertyDescriptor
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedSimpleFunctionDescriptor
import org.jetbrains.kotlin.storage.StorageManager
import org.jetbrains.kotlin.utils.JsMetadataVersion
import org.jetbrains.kotlin.utils.KotlinJavascriptMetadataUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

internal object JsKlibMetadataSerializationUtil {
    const val CLASS_METADATA_FILE_EXTENSION: String = "kjsm"

    fun readDescriptors(
        metadata: PackagesWithHeaderMetadata,
        storageManager: StorageManager,
        module: ModuleDescriptor,
        configuration: DeserializationConfiguration,
        lookupTracker: LookupTracker
    ): PackageFragmentProvider {
        val scopeProto = metadata.packages.map {
            ProtoBuf.PackageFragment.parseFrom(it, JsKlibMetadataSerializerProtocol.extensionRegistry)
        }
        val headerProto = JsKlibMetadataProtoBuf.Header.parseFrom(CodedInputStream.newInstance(metadata.header), JsKlibMetadataSerializerProtocol.extensionRegistry)
        return createJsKlibMetadataPackageFragmentProvider(
            storageManager, module, headerProto, scopeProto, metadata.metadataVersion, configuration, lookupTracker
        )
    }

    fun serializeMetadata(
        bindingContext: BindingContext,
        jsDescriptor: JsKlibMetadataModuleDescriptor<ModuleDescriptor>,
        languageVersionSettings: LanguageVersionSettings,
        metadataVersion: JsMetadataVersion,
        declarationTableHandler: ((DeclarationDescriptor) -> JsKlibMetadataProtoBuf.DescriptorUniqId?)? = null
    ): SerializedMetadata {
        val serializedFragments = HashMap<FqName, ProtoBuf.PackageFragment>()
        val module = jsDescriptor.data

        for (fqName in getPackagesFqNames(module).sortedBy { it.asString() }) {
            val fragment = serializeDescriptors(
                bindingContext, module,
                module.getPackage(fqName).memberScope.getContributedDescriptors(),
                fqName, languageVersionSettings, metadataVersion, declarationTableHandler
            )

            if (!fragment.isEmpty()) {
                serializedFragments[fqName] = fragment
            }
        }

        return SerializedMetadata(serializedFragments, jsDescriptor, languageVersionSettings, metadataVersion)
    }

    class SerializedMetadata(
        private val serializedFragments: Map<FqName, ProtoBuf.PackageFragment>,
        private val jsDescriptor: JsKlibMetadataModuleDescriptor<ModuleDescriptor>,
        private val languageVersionSettings: LanguageVersionSettings,
        private val metadataVersion: JsMetadataVersion
    ) {
        class SerializedPackage(val fqName: FqName, val bytes: ByteArray)

        fun serializedPackages(): List<SerializedPackage> {
            val packages = arrayListOf<SerializedPackage>()

            for ((fqName, part) in serializedFragments) {
                val stream = ByteArrayOutputStream()
                with(DataOutputStream(stream)) {
                    val version = metadataVersion.toArray()
                    writeInt(version.size)
                    version.forEach(this::writeInt)
                }

                serializeHeader(jsDescriptor.data, fqName, languageVersionSettings).writeDelimitedTo(stream)
                part.writeTo(stream)

                packages.add(SerializedPackage(fqName, stream.toByteArray()))
            }

            return packages
        }

        fun asString(): String =
            KotlinJavascriptMetadataUtils.formatMetadataAsString(jsDescriptor.name, asByteArray(), metadataVersion)

        private fun asByteArray(): ByteArray =
            ByteArrayOutputStream().apply {
                GZIPOutputStream(this).use { stream ->
                    serializeHeader(
                        jsDescriptor.data,
                        packageFqName = null,
                        languageVersionSettings = languageVersionSettings
                    ).writeDelimitedTo(stream)
                    asLibrary().writeTo(stream)
                }
            }.toByteArray()

        private fun asLibrary(): JsKlibMetadataProtoBuf.Library {
            val moduleKind = jsDescriptor.kind
            jsDescriptor.imported
            val builder = JsKlibMetadataProtoBuf.Library.newBuilder()

            val moduleProtoKind = when (moduleKind) {
                ModuleKind.PLAIN -> JsKlibMetadataProtoBuf.Library.Kind.PLAIN
                ModuleKind.AMD -> JsKlibMetadataProtoBuf.Library.Kind.AMD
                ModuleKind.COMMON_JS -> JsKlibMetadataProtoBuf.Library.Kind.COMMON_JS
                ModuleKind.UMD -> JsKlibMetadataProtoBuf.Library.Kind.UMD
            }
            if (builder.kind != moduleProtoKind) {
                builder.kind = moduleProtoKind
            }

            jsDescriptor.imported.forEach { builder.addImportedModule(it) }

            for ((_, fragment) in serializedFragments.entries.sortedBy { (fqName, _) -> fqName.asString() }) {
                builder.addPackageFragment(fragment)
            }

            return builder.build()
        }
    }

    fun serializeDescriptors(
        bindingContext: BindingContext,
        module: ModuleDescriptor,
        scope: Collection<DeclarationDescriptor>,
        fqName: FqName,
        languageVersionSettings: LanguageVersionSettings,
        metadataVersion: BinaryVersion,
        declarationTableHandler: ((DeclarationDescriptor) -> JsKlibMetadataProtoBuf.DescriptorUniqId?)? = null
    ): ProtoBuf.PackageFragment {
        val builder = ProtoBuf.PackageFragment.newBuilder()

        val skip = fun(descriptor: DeclarationDescriptor): Boolean {
            // TODO: ModuleDescriptor should be able to return the package only with the contents of that module, without dependencies
            if (descriptor.module != module) return true

            if (descriptor is MemberDescriptor && descriptor.isExpect) {
                return !(descriptor is ClassDescriptor && ExpectedActualDeclarationChecker.shouldGenerateExpectClass(descriptor))
            }

            return false
        }

        val fileRegistry = JsKlibMetadataFileRegistry()
        val extension = JsKlibMetadataSerializerExtension(fileRegistry, languageVersionSettings, metadataVersion, declarationTableHandler)

        val classDescriptors = scope.filterIsInstance<ClassDescriptor>().sortedBy { it.fqNameSafe.asString() }

        fun serializeClasses(descriptors: Collection<DeclarationDescriptor>, parentSerializer: DescriptorSerializer) {
            for (descriptor in descriptors) {
                if (descriptor !is ClassDescriptor || skip(descriptor)) continue

                val serializer = DescriptorSerializer.create(descriptor, extension, parentSerializer)
                serializeClasses(descriptor.unsubstitutedInnerClassesScope.getContributedDescriptors(), serializer)
                val classProto = serializer.classProto(descriptor).build() ?: error("Class not serialized: $descriptor")
                builder.addClass_(classProto)
            }
        }

        val serializer = DescriptorSerializer.createTopLevel(extension)
        serializeClasses(classDescriptors, serializer)

        val stringTable = extension.stringTable

        val members = scope.filterNot(skip)
        builder.`package` = serializer.packagePartProto(fqName, members).build()

        builder.setExtension(
            JsKlibMetadataProtoBuf.packageFragmentFiles,
            serializeFiles(fileRegistry, bindingContext, AnnotationSerializer(stringTable))
        )

        val (strings, qualifiedNames) = stringTable.buildProto()
        builder.strings = strings
        builder.qualifiedNames = qualifiedNames

        return builder.build()
    }

    private fun serializeFiles(
        fileRegistry: JsKlibMetadataFileRegistry,
        bindingContext: BindingContext,
        serializer: AnnotationSerializer
    ): JsKlibMetadataProtoBuf.Files {
        val filesProto = JsKlibMetadataProtoBuf.Files.newBuilder()
        for ((file, id) in fileRegistry.fileIds.entries.sortedBy { it.value }) {
            val fileProto = JsKlibMetadataProtoBuf.File.newBuilder()
            if (id != filesProto.fileCount) {
                fileProto.id = id
            }
            val annotations = when (file) {
                is KotlinPsiFileMetadata -> file.ktFile.annotationEntries.map { bindingContext[BindingContext.ANNOTATION, it]!! }
                is KotlinDeserializedFileMetadata -> file.packageFragment.fileMap[file.fileId]!!.annotations
            }
            for (annotation in annotations.filterOutSourceAnnotations()) {
                fileProto.addAnnotation(serializer.serializeAnnotation(annotation))
            }
            filesProto.addFile(fileProto)
        }
        return filesProto.build()
    }

    private fun ProtoBuf.PackageFragment.isEmpty(): Boolean =
        class_Count == 0 && `package`.let { it.functionCount == 0 && it.propertyCount == 0 && it.typeAliasCount == 0 }

    fun serializeHeader(
        module: ModuleDescriptor, packageFqName: FqName?, languageVersionSettings: LanguageVersionSettings
    ): JsKlibMetadataProtoBuf.Header {
        val header = JsKlibMetadataProtoBuf.Header.newBuilder()

        if (packageFqName != null) {
            header.packageFqName = packageFqName.asString()
        }

        if (languageVersionSettings.isPreRelease()) {
            header.flags = 1
        }

        val experimentalAnnotationFqNames = languageVersionSettings.getFlag(AnalysisFlags.experimental)
        if (experimentalAnnotationFqNames.isNotEmpty()) {
            val stringTable = StringTableImpl()
            for (fqName in experimentalAnnotationFqNames) {
                val descriptor = module.resolveClassByFqName(FqName(fqName), NoLookupLocation.FOR_ALREADY_TRACKED) ?: continue
                header.addAnnotation(ProtoBuf.Annotation.newBuilder().apply {
                    id = stringTable.getFqNameIndex(descriptor)
                })
            }
            val (strings, qualifiedNames) = stringTable.buildProto()
            header.strings = strings
            header.qualifiedNames = qualifiedNames
        }

        // TODO: write JS code binary version

        return header.build()
    }

    private fun getPackagesFqNames(module: ModuleDescriptor): Set<FqName> {
        return mutableSetOf<FqName>().apply {
            getSubPackagesFqNames(module.getPackage(FqName.ROOT), this)
            add(FqName.ROOT)
        }
    }

    private fun getSubPackagesFqNames(packageView: PackageViewDescriptor, result: MutableSet<FqName>) {
        val fqName = packageView.fqName
        if (!fqName.isRoot) {
            result.add(fqName)
        }

        for (descriptor in packageView.memberScope.getContributedDescriptors(DescriptorKindFilter.PACKAGES, MemberScope.ALL_NAME_FILTER)) {
            if (descriptor is PackageViewDescriptor) {
                getSubPackagesFqNames(descriptor, result)
            }
        }
    }

    @JvmStatic
    fun readModuleAsProto(metadata: ByteArray, metadataVersion: JsMetadataVersion): JsKlibMetadataParts {
        val (header, content) = GZIPInputStream(ByteArrayInputStream(metadata)).use { stream ->
            JsKlibMetadataProtoBuf.Header.parseDelimitedFrom(stream, JsKlibMetadataSerializerProtocol.extensionRegistry) to
                    JsKlibMetadataProtoBuf.Library.parseFrom(stream, JsKlibMetadataSerializerProtocol.extensionRegistry)
        }

        val moduleKind = when (content.kind) {
            null, JsKlibMetadataProtoBuf.Library.Kind.PLAIN -> ModuleKind.PLAIN
            JsKlibMetadataProtoBuf.Library.Kind.AMD -> ModuleKind.AMD
            JsKlibMetadataProtoBuf.Library.Kind.COMMON_JS -> ModuleKind.COMMON_JS
            JsKlibMetadataProtoBuf.Library.Kind.UMD -> ModuleKind.UMD
        }

        return JsKlibMetadataParts(header, content.packageFragmentList, moduleKind, content.importedModuleList, metadataVersion)
    }
}

data class JsKlibMetadataParts(
    val header: JsKlibMetadataProtoBuf.Header,
    val body: List<ProtoBuf.PackageFragment>,
    val kind: ModuleKind,
    val importedModules: List<String>,
    val metadataVersion: JsMetadataVersion
)

internal fun DeclarationDescriptor.extractFileId(): Int? = when (this) {
    is DeserializedClassDescriptor -> classProto.getExtension(JsKlibMetadataProtoBuf.classContainingFileId)
    is DeserializedSimpleFunctionDescriptor -> proto.getExtension(JsKlibMetadataProtoBuf.functionContainingFileId)
    is DeserializedPropertyDescriptor -> proto.getExtension(JsKlibMetadataProtoBuf.propertyContainingFileId)
    else -> null
}
