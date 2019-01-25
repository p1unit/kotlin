/*
 * Copyright 2010-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license 
 * that can be found in the license/LICENSE.txt file.
 */
package org.jetbrains.kotlin.fir.visitors

import org.jetbrains.kotlin.fir.*
import org.jetbrains.kotlin.fir.declarations.*
import org.jetbrains.kotlin.fir.declarations.impl.*
import org.jetbrains.kotlin.fir.expressions.*
import org.jetbrains.kotlin.fir.expressions.impl.*
import org.jetbrains.kotlin.fir.types.*


/** This file generated by :compiler:fir:tree:generateVisitors. DO NOT MODIFY MANUALLY! */
abstract class FirTransformer<in D> : FirVisitor<CompositeTransformResult<FirElement>, D>() {
    abstract fun <E : FirElement> transformElement(element: E, data: D): CompositeTransformResult<E>

    open fun <E : FirElement> transformCatch(catch: E, data: D): CompositeTransformResult<E> {
        return transformElement(catch, data)
    }

    open fun transformDeclaration(declaration: FirDeclaration, data: D): CompositeTransformResult<FirDeclaration> {
        return transformElement(declaration, data)
    }

    open fun transformCallableMember(callableMember: FirCallableMember, data: D): CompositeTransformResult<FirDeclaration> {
        return transformDeclaration(callableMember, data)
    }

    open fun transformDeclarationWithBody(declarationWithBody: FirDeclarationWithBody, data: D): CompositeTransformResult<FirDeclaration> {
        return transformDeclaration(declarationWithBody, data)
    }

    open fun transformAnonymousInitializer(anonymousInitializer: FirAnonymousInitializer, data: D): CompositeTransformResult<FirDeclaration> {
        return transformDeclarationWithBody(anonymousInitializer, data)
    }

    open fun transformFunction(function: FirFunction, data: D): CompositeTransformResult<FirDeclaration> {
        return transformDeclarationWithBody(function, data)
    }

    open fun transformAnonymousFunction(anonymousFunction: FirAnonymousFunction, data: D): CompositeTransformResult<FirDeclaration> {
        return transformFunction(anonymousFunction, data)
    }

    open fun transformConstructor(constructor: FirConstructor, data: D): CompositeTransformResult<FirDeclaration> {
        return transformFunction(constructor, data)
    }

    open fun transformModifiableFunction(modifiableFunction: FirModifiableFunction, data: D): CompositeTransformResult<FirDeclaration> {
        return transformFunction(modifiableFunction, data)
    }

    open fun transformNamedFunction(namedFunction: FirNamedFunction, data: D): CompositeTransformResult<FirDeclaration> {
        return transformFunction(namedFunction, data)
    }

    open fun transformPropertyAccessor(propertyAccessor: FirPropertyAccessor, data: D): CompositeTransformResult<FirDeclaration> {
        return transformFunction(propertyAccessor, data)
    }

    open fun transformErrorDeclaration(errorDeclaration: FirErrorDeclaration, data: D): CompositeTransformResult<FirDeclaration> {
        return transformDeclaration(errorDeclaration, data)
    }

    open fun transformNamedDeclaration(namedDeclaration: FirNamedDeclaration, data: D): CompositeTransformResult<FirDeclaration> {
        return transformDeclaration(namedDeclaration, data)
    }

    open fun transformMemberDeclaration(memberDeclaration: FirMemberDeclaration, data: D): CompositeTransformResult<FirDeclaration> {
        return transformNamedDeclaration(memberDeclaration, data)
    }

    open fun transformRegularClass(regularClass: FirRegularClass, data: D): CompositeTransformResult<FirDeclaration> {
        return transformMemberDeclaration(regularClass, data)
    }

    open fun transformEnumEntry(enumEntry: FirEnumEntry, data: D): CompositeTransformResult<FirDeclaration> {
        return transformRegularClass(enumEntry, data)
    }

    open fun transformTypeAlias(typeAlias: FirTypeAlias, data: D): CompositeTransformResult<FirDeclaration> {
        return transformMemberDeclaration(typeAlias, data)
    }

    open fun transformTypeParameter(typeParameter: FirTypeParameter, data: D): CompositeTransformResult<FirDeclaration> {
        return transformNamedDeclaration(typeParameter, data)
    }

    open fun transformProperty(property: FirProperty, data: D): CompositeTransformResult<FirDeclaration> {
        return transformDeclaration(property, data)
    }

    open fun transformTypedDeclaration(typedDeclaration: FirTypedDeclaration, data: D): CompositeTransformResult<FirDeclaration> {
        return transformDeclaration(typedDeclaration, data)
    }

    open fun transformValueParameter(valueParameter: FirValueParameter, data: D): CompositeTransformResult<FirDeclaration> {
        return transformDeclaration(valueParameter, data)
    }

    open fun transformVariable(variable: FirVariable, data: D): CompositeTransformResult<FirDeclaration> {
        return transformDeclaration(variable, data)
    }

    open fun transformDeclarationStatus(declarationStatus: FirDeclarationStatus, data: D): CompositeTransformResult<FirDeclarationStatus> {
        return transformElement(declarationStatus, data)
    }

    open fun transformResolvedDeclarationStatus(resolvedDeclarationStatus: FirResolvedDeclarationStatus, data: D): CompositeTransformResult<FirDeclarationStatus> {
        return transformDeclarationStatus(resolvedDeclarationStatus, data)
    }

    open fun transformImport(import: FirImport, data: D): CompositeTransformResult<FirImport> {
        return transformElement(import, data)
    }

    open fun transformResolvedImport(resolvedImport: FirResolvedImport, data: D): CompositeTransformResult<FirImport> {
        return transformImport(resolvedImport, data)
    }

    open fun <E : FirElement> transformLabel(label: E, data: D): CompositeTransformResult<E> {
        return transformElement(label, data)
    }

    open fun <E : FirElement> transformPackageFragment(packageFragment: E, data: D): CompositeTransformResult<E> {
        return transformElement(packageFragment, data)
    }

    open fun transformFile(file: FirFile, data: D): CompositeTransformResult<FirFile> {
        return transformPackageFragment(file, data)
    }

    open fun <E : FirElement> transformReference(reference: E, data: D): CompositeTransformResult<E> {
        return transformElement(reference, data)
    }

    open fun <E : FirReference> transformNamedReference(namedReference: E, data: D): CompositeTransformResult<E> {
        return transformReference(namedReference, data)
    }

    open fun <E : FirReference> transformSuperReference(superReference: E, data: D): CompositeTransformResult<E> {
        return transformReference(superReference, data)
    }

    open fun <E : FirReference> transformThisReference(thisReference: E, data: D): CompositeTransformResult<E> {
        return transformReference(thisReference, data)
    }

    open fun transformStatement(statement: FirStatement, data: D): CompositeTransformResult<FirStatement> {
        return transformElement(statement, data)
    }

    open fun transformClass(klass: FirClass, data: D): CompositeTransformResult<FirStatement> {
        return transformStatement(klass, data)
    }

    open fun transformAnonymousObject(anonymousObject: FirAnonymousObject, data: D): CompositeTransformResult<FirStatement> {
        return transformClass(anonymousObject, data)
    }

    open fun transformModifiableClass(modifiableClass: FirModifiableClass, data: D): CompositeTransformResult<FirStatement> {
        return transformClass(modifiableClass, data)
    }

    open fun transformExpression(expression: FirExpression, data: D): CompositeTransformResult<FirStatement> {
        return transformStatement(expression, data)
    }

    open fun transformAccess(access: FirAccess, data: D): CompositeTransformResult<FirStatement> {
        return transformExpression(access, data)
    }

    open fun transformAssignment(assignment: FirAssignment, data: D): CompositeTransformResult<FirStatement> {
        return transformAccess(assignment, data)
    }

    open fun transformPropertyAssignment(propertyAssignment: FirPropertyAssignment, data: D): CompositeTransformResult<FirStatement> {
        return transformAssignment(propertyAssignment, data)
    }

    open fun transformCallableReferenceAccess(callableReferenceAccess: FirCallableReferenceAccess, data: D): CompositeTransformResult<FirStatement> {
        return transformAccess(callableReferenceAccess, data)
    }

    open fun transformModifiableAccess(modifiableAccess: FirModifiableAccess, data: D): CompositeTransformResult<FirStatement> {
        return transformAccess(modifiableAccess, data)
    }

    open fun transformBlock(block: FirBlock, data: D): CompositeTransformResult<FirStatement> {
        return transformExpression(block, data)
    }

    open fun transformCall(call: FirCall, data: D): CompositeTransformResult<FirStatement> {
        return transformExpression(call, data)
    }

    open fun transformAnnotationCall(annotationCall: FirAnnotationCall, data: D): CompositeTransformResult<FirStatement> {
        return transformCall(annotationCall, data)
    }

    open fun transformArrayGetCall(arrayGetCall: FirArrayGetCall, data: D): CompositeTransformResult<FirStatement> {
        return transformCall(arrayGetCall, data)
    }

    open fun transformArrayOfCall(arrayOfCall: FirArrayOfCall, data: D): CompositeTransformResult<FirStatement> {
        return transformCall(arrayOfCall, data)
    }

    open fun transformArraySetCall(arraySetCall: FirArraySetCall, data: D): CompositeTransformResult<FirStatement> {
        return transformCall(arraySetCall, data)
    }

    open fun transformComponentCall(componentCall: FirComponentCall, data: D): CompositeTransformResult<FirStatement> {
        return transformCall(componentCall, data)
    }

    open fun transformDelegatedConstructorCall(delegatedConstructorCall: FirDelegatedConstructorCall, data: D): CompositeTransformResult<FirStatement> {
        return transformCall(delegatedConstructorCall, data)
    }

    open fun transformFunctionCall(functionCall: FirFunctionCall, data: D): CompositeTransformResult<FirStatement> {
        return transformCall(functionCall, data)
    }

    open fun transformGetClassCall(getClassCall: FirGetClassCall, data: D): CompositeTransformResult<FirStatement> {
        return transformCall(getClassCall, data)
    }

    open fun transformOperatorCall(operatorCall: FirOperatorCall, data: D): CompositeTransformResult<FirStatement> {
        return transformCall(operatorCall, data)
    }

    open fun transformTypeOperatorCall(typeOperatorCall: FirTypeOperatorCall, data: D): CompositeTransformResult<FirStatement> {
        return transformOperatorCall(typeOperatorCall, data)
    }

    open fun <T> transformConstExpression(constExpression: FirConstExpression<T>, data: D): CompositeTransformResult<FirStatement> {
        return transformExpression(constExpression, data)
    }

    open fun transformErrorExpression(errorExpression: FirErrorExpression, data: D): CompositeTransformResult<FirStatement> {
        return transformExpression(errorExpression, data)
    }

    open fun <E : FirTargetElement> transformJump(jump: FirJump<E>, data: D): CompositeTransformResult<FirStatement> {
        return transformExpression(jump, data)
    }

    open fun transformBreakStatement(breakStatement: FirBreakStatement, data: D): CompositeTransformResult<FirStatement> {
        return transformJump(breakStatement, data)
    }

    open fun transformContinueStatement(continueStatement: FirContinueStatement, data: D): CompositeTransformResult<FirStatement> {
        return transformJump(continueStatement, data)
    }

    open fun transformReturnStatement(returnStatement: FirReturnStatement, data: D): CompositeTransformResult<FirStatement> {
        return transformJump(returnStatement, data)
    }

    open fun transformThrowExpression(throwExpression: FirThrowExpression, data: D): CompositeTransformResult<FirStatement> {
        return transformExpression(throwExpression, data)
    }

    open fun transformTryExpression(tryExpression: FirTryExpression, data: D): CompositeTransformResult<FirStatement> {
        return transformExpression(tryExpression, data)
    }

    open fun transformWhenExpression(whenExpression: FirWhenExpression, data: D): CompositeTransformResult<FirStatement> {
        return transformExpression(whenExpression, data)
    }

    open fun transformLoop(loop: FirLoop, data: D): CompositeTransformResult<FirStatement> {
        return transformStatement(loop, data)
    }

    open fun transformDoWhileLoop(doWhileLoop: FirDoWhileLoop, data: D): CompositeTransformResult<FirStatement> {
        return transformLoop(doWhileLoop, data)
    }

    open fun transformWhileLoop(whileLoop: FirWhileLoop, data: D): CompositeTransformResult<FirStatement> {
        return transformLoop(whileLoop, data)
    }

    open fun <E : FirElement> transformTargetElement(targetElement: E, data: D): CompositeTransformResult<E> {
        return transformElement(targetElement, data)
    }

    open fun <E : FirTargetElement> transformLabeledElement(labeledElement: E, data: D): CompositeTransformResult<E> {
        return transformTargetElement(labeledElement, data)
    }

    open fun transformType(type: FirType, data: D): CompositeTransformResult<FirType> {
        return transformElement(type, data)
    }

    open fun transformDelegatedType(delegatedType: FirDelegatedType, data: D): CompositeTransformResult<FirType> {
        return transformType(delegatedType, data)
    }

    open fun transformErrorType(errorType: FirErrorType, data: D): CompositeTransformResult<FirType> {
        return transformType(errorType, data)
    }

    open fun transformImplicitType(implicitType: FirImplicitType, data: D): CompositeTransformResult<FirType> {
        return transformType(implicitType, data)
    }

    open fun transformTypeWithNullability(typeWithNullability: FirTypeWithNullability, data: D): CompositeTransformResult<FirType> {
        return transformType(typeWithNullability, data)
    }

    open fun transformDynamicType(dynamicType: FirDynamicType, data: D): CompositeTransformResult<FirType> {
        return transformTypeWithNullability(dynamicType, data)
    }

    open fun transformFunctionType(functionType: FirFunctionType, data: D): CompositeTransformResult<FirType> {
        return transformTypeWithNullability(functionType, data)
    }

    open fun transformResolvedType(resolvedType: FirResolvedType, data: D): CompositeTransformResult<FirType> {
        return transformTypeWithNullability(resolvedType, data)
    }

    open fun transformResolvedFunctionType(resolvedFunctionType: FirResolvedFunctionType, data: D): CompositeTransformResult<FirType> {
        return transformResolvedType(resolvedFunctionType, data)
    }

    open fun transformUserType(userType: FirUserType, data: D): CompositeTransformResult<FirType> {
        return transformTypeWithNullability(userType, data)
    }

    open fun transformTypeProjection(typeProjection: FirTypeProjection, data: D): CompositeTransformResult<FirTypeProjection> {
        return transformElement(typeProjection, data)
    }

    open fun transformStarProjection(starProjection: FirStarProjection, data: D): CompositeTransformResult<FirTypeProjection> {
        return transformTypeProjection(starProjection, data)
    }

    open fun transformTypeProjectionWithVariance(typeProjectionWithVariance: FirTypeProjectionWithVariance, data: D): CompositeTransformResult<FirTypeProjection> {
        return transformTypeProjection(typeProjectionWithVariance, data)
    }

    open fun <E : FirElement> transformWhenBranch(whenBranch: E, data: D): CompositeTransformResult<E> {
        return transformElement(whenBranch, data)
    }

    final override fun visitAccess(access: FirAccess, data: D): CompositeTransformResult<FirElement> {
        return transformAccess(access, data)
    }

    final override fun visitAnnotationCall(annotationCall: FirAnnotationCall, data: D): CompositeTransformResult<FirElement> {
        return transformAnnotationCall(annotationCall, data)
    }

    final override fun visitAnonymousFunction(anonymousFunction: FirAnonymousFunction, data: D): CompositeTransformResult<FirElement> {
        return transformAnonymousFunction(anonymousFunction, data)
    }

    final override fun visitAnonymousInitializer(anonymousInitializer: FirAnonymousInitializer, data: D): CompositeTransformResult<FirElement> {
        return transformAnonymousInitializer(anonymousInitializer, data)
    }

    final override fun visitAnonymousObject(anonymousObject: FirAnonymousObject, data: D): CompositeTransformResult<FirElement> {
        return transformAnonymousObject(anonymousObject, data)
    }

    final override fun visitArrayGetCall(arrayGetCall: FirArrayGetCall, data: D): CompositeTransformResult<FirElement> {
        return transformArrayGetCall(arrayGetCall, data)
    }

    final override fun visitArrayOfCall(arrayOfCall: FirArrayOfCall, data: D): CompositeTransformResult<FirElement> {
        return transformArrayOfCall(arrayOfCall, data)
    }

    final override fun visitArraySetCall(arraySetCall: FirArraySetCall, data: D): CompositeTransformResult<FirElement> {
        return transformArraySetCall(arraySetCall, data)
    }

    final override fun visitAssignment(assignment: FirAssignment, data: D): CompositeTransformResult<FirElement> {
        return transformAssignment(assignment, data)
    }

    final override fun visitBlock(block: FirBlock, data: D): CompositeTransformResult<FirElement> {
        return transformBlock(block, data)
    }

    final override fun visitBreakStatement(breakStatement: FirBreakStatement, data: D): CompositeTransformResult<FirElement> {
        return transformBreakStatement(breakStatement, data)
    }

    final override fun visitCall(call: FirCall, data: D): CompositeTransformResult<FirElement> {
        return transformCall(call, data)
    }

    final override fun visitCallableMember(callableMember: FirCallableMember, data: D): CompositeTransformResult<FirElement> {
        return transformCallableMember(callableMember, data)
    }

    final override fun visitCallableReferenceAccess(callableReferenceAccess: FirCallableReferenceAccess, data: D): CompositeTransformResult<FirElement> {
        return transformCallableReferenceAccess(callableReferenceAccess, data)
    }

    final override fun visitCatch(catch: FirCatch, data: D): CompositeTransformResult<FirElement> {
        return transformCatch(catch, data)
    }

    final override fun visitClass(klass: FirClass, data: D): CompositeTransformResult<FirElement> {
        return transformClass(klass, data)
    }

    final override fun visitComponentCall(componentCall: FirComponentCall, data: D): CompositeTransformResult<FirElement> {
        return transformComponentCall(componentCall, data)
    }

    final override fun <T> visitConstExpression(constExpression: FirConstExpression<T>, data: D): CompositeTransformResult<FirElement> {
        return transformConstExpression(constExpression, data)
    }

    final override fun visitConstructor(constructor: FirConstructor, data: D): CompositeTransformResult<FirElement> {
        return transformConstructor(constructor, data)
    }

    final override fun visitContinueStatement(continueStatement: FirContinueStatement, data: D): CompositeTransformResult<FirElement> {
        return transformContinueStatement(continueStatement, data)
    }

    final override fun visitDeclaration(declaration: FirDeclaration, data: D): CompositeTransformResult<FirElement> {
        return transformDeclaration(declaration, data)
    }

    final override fun visitDeclarationStatus(declarationStatus: FirDeclarationStatus, data: D): CompositeTransformResult<FirElement> {
        return transformDeclarationStatus(declarationStatus, data)
    }

    final override fun visitDeclarationWithBody(declarationWithBody: FirDeclarationWithBody, data: D): CompositeTransformResult<FirElement> {
        return transformDeclarationWithBody(declarationWithBody, data)
    }

    final override fun visitDelegatedConstructorCall(delegatedConstructorCall: FirDelegatedConstructorCall, data: D): CompositeTransformResult<FirElement> {
        return transformDelegatedConstructorCall(delegatedConstructorCall, data)
    }

    final override fun visitDelegatedType(delegatedType: FirDelegatedType, data: D): CompositeTransformResult<FirElement> {
        return transformDelegatedType(delegatedType, data)
    }

    final override fun visitDoWhileLoop(doWhileLoop: FirDoWhileLoop, data: D): CompositeTransformResult<FirElement> {
        return transformDoWhileLoop(doWhileLoop, data)
    }

    final override fun visitDynamicType(dynamicType: FirDynamicType, data: D): CompositeTransformResult<FirElement> {
        return transformDynamicType(dynamicType, data)
    }

    final override fun visitEnumEntry(enumEntry: FirEnumEntry, data: D): CompositeTransformResult<FirElement> {
        return transformEnumEntry(enumEntry, data)
    }

    final override fun visitErrorDeclaration(errorDeclaration: FirErrorDeclaration, data: D): CompositeTransformResult<FirElement> {
        return transformErrorDeclaration(errorDeclaration, data)
    }

    final override fun visitErrorExpression(errorExpression: FirErrorExpression, data: D): CompositeTransformResult<FirElement> {
        return transformErrorExpression(errorExpression, data)
    }

    final override fun visitErrorType(errorType: FirErrorType, data: D): CompositeTransformResult<FirElement> {
        return transformErrorType(errorType, data)
    }

    final override fun visitExpression(expression: FirExpression, data: D): CompositeTransformResult<FirElement> {
        return transformExpression(expression, data)
    }

    final override fun visitFile(file: FirFile, data: D): CompositeTransformResult<FirElement> {
        return transformFile(file, data)
    }

    final override fun visitFunction(function: FirFunction, data: D): CompositeTransformResult<FirElement> {
        return transformFunction(function, data)
    }

    final override fun visitFunctionCall(functionCall: FirFunctionCall, data: D): CompositeTransformResult<FirElement> {
        return transformFunctionCall(functionCall, data)
    }

    final override fun visitFunctionType(functionType: FirFunctionType, data: D): CompositeTransformResult<FirElement> {
        return transformFunctionType(functionType, data)
    }

    final override fun visitGetClassCall(getClassCall: FirGetClassCall, data: D): CompositeTransformResult<FirElement> {
        return transformGetClassCall(getClassCall, data)
    }

    final override fun visitImplicitType(implicitType: FirImplicitType, data: D): CompositeTransformResult<FirElement> {
        return transformImplicitType(implicitType, data)
    }

    final override fun visitImport(import: FirImport, data: D): CompositeTransformResult<FirElement> {
        return transformImport(import, data)
    }

    final override fun <E : FirTargetElement> visitJump(jump: FirJump<E>, data: D): CompositeTransformResult<FirElement> {
        return transformJump(jump, data)
    }

    final override fun visitLabel(label: FirLabel, data: D): CompositeTransformResult<FirElement> {
        return transformLabel(label, data)
    }

    final override fun visitLabeledElement(labeledElement: FirLabeledElement, data: D): CompositeTransformResult<FirElement> {
        return transformLabeledElement(labeledElement, data)
    }

    final override fun visitLoop(loop: FirLoop, data: D): CompositeTransformResult<FirElement> {
        return transformLoop(loop, data)
    }

    final override fun visitMemberDeclaration(memberDeclaration: FirMemberDeclaration, data: D): CompositeTransformResult<FirElement> {
        return transformMemberDeclaration(memberDeclaration, data)
    }

    final override fun visitModifiableAccess(modifiableAccess: FirModifiableAccess, data: D): CompositeTransformResult<FirElement> {
        return transformModifiableAccess(modifiableAccess, data)
    }

    final override fun visitModifiableClass(modifiableClass: FirModifiableClass, data: D): CompositeTransformResult<FirElement> {
        return transformModifiableClass(modifiableClass, data)
    }

    final override fun visitModifiableFunction(modifiableFunction: FirModifiableFunction, data: D): CompositeTransformResult<FirElement> {
        return transformModifiableFunction(modifiableFunction, data)
    }

    final override fun visitNamedDeclaration(namedDeclaration: FirNamedDeclaration, data: D): CompositeTransformResult<FirElement> {
        return transformNamedDeclaration(namedDeclaration, data)
    }

    final override fun visitNamedFunction(namedFunction: FirNamedFunction, data: D): CompositeTransformResult<FirElement> {
        return transformNamedFunction(namedFunction, data)
    }

    final override fun visitNamedReference(namedReference: FirNamedReference, data: D): CompositeTransformResult<FirElement> {
        return transformNamedReference(namedReference, data)
    }

    final override fun visitOperatorCall(operatorCall: FirOperatorCall, data: D): CompositeTransformResult<FirElement> {
        return transformOperatorCall(operatorCall, data)
    }

    final override fun visitPackageFragment(packageFragment: FirPackageFragment, data: D): CompositeTransformResult<FirElement> {
        return transformPackageFragment(packageFragment, data)
    }

    final override fun visitProperty(property: FirProperty, data: D): CompositeTransformResult<FirElement> {
        return transformProperty(property, data)
    }

    final override fun visitPropertyAccessor(propertyAccessor: FirPropertyAccessor, data: D): CompositeTransformResult<FirElement> {
        return transformPropertyAccessor(propertyAccessor, data)
    }

    final override fun visitPropertyAssignment(propertyAssignment: FirPropertyAssignment, data: D): CompositeTransformResult<FirElement> {
        return transformPropertyAssignment(propertyAssignment, data)
    }

    final override fun visitReference(reference: FirReference, data: D): CompositeTransformResult<FirElement> {
        return transformReference(reference, data)
    }

    final override fun visitRegularClass(regularClass: FirRegularClass, data: D): CompositeTransformResult<FirElement> {
        return transformRegularClass(regularClass, data)
    }

    final override fun visitResolvedDeclarationStatus(resolvedDeclarationStatus: FirResolvedDeclarationStatus, data: D): CompositeTransformResult<FirElement> {
        return transformResolvedDeclarationStatus(resolvedDeclarationStatus, data)
    }

    final override fun visitResolvedFunctionType(resolvedFunctionType: FirResolvedFunctionType, data: D): CompositeTransformResult<FirElement> {
        return transformResolvedFunctionType(resolvedFunctionType, data)
    }

    final override fun visitResolvedImport(resolvedImport: FirResolvedImport, data: D): CompositeTransformResult<FirElement> {
        return transformResolvedImport(resolvedImport, data)
    }

    final override fun visitResolvedType(resolvedType: FirResolvedType, data: D): CompositeTransformResult<FirElement> {
        return transformResolvedType(resolvedType, data)
    }

    final override fun visitReturnStatement(returnStatement: FirReturnStatement, data: D): CompositeTransformResult<FirElement> {
        return transformReturnStatement(returnStatement, data)
    }

    final override fun visitStarProjection(starProjection: FirStarProjection, data: D): CompositeTransformResult<FirElement> {
        return transformStarProjection(starProjection, data)
    }

    final override fun visitStatement(statement: FirStatement, data: D): CompositeTransformResult<FirElement> {
        return transformStatement(statement, data)
    }

    final override fun visitSuperReference(superReference: FirSuperReference, data: D): CompositeTransformResult<FirElement> {
        return transformSuperReference(superReference, data)
    }

    final override fun visitTargetElement(targetElement: FirTargetElement, data: D): CompositeTransformResult<FirElement> {
        return transformTargetElement(targetElement, data)
    }

    final override fun visitThisReference(thisReference: FirThisReference, data: D): CompositeTransformResult<FirElement> {
        return transformThisReference(thisReference, data)
    }

    final override fun visitThrowExpression(throwExpression: FirThrowExpression, data: D): CompositeTransformResult<FirElement> {
        return transformThrowExpression(throwExpression, data)
    }

    final override fun visitTryExpression(tryExpression: FirTryExpression, data: D): CompositeTransformResult<FirElement> {
        return transformTryExpression(tryExpression, data)
    }

    final override fun visitType(type: FirType, data: D): CompositeTransformResult<FirElement> {
        return transformType(type, data)
    }

    final override fun visitTypeAlias(typeAlias: FirTypeAlias, data: D): CompositeTransformResult<FirElement> {
        return transformTypeAlias(typeAlias, data)
    }

    final override fun visitTypeOperatorCall(typeOperatorCall: FirTypeOperatorCall, data: D): CompositeTransformResult<FirElement> {
        return transformTypeOperatorCall(typeOperatorCall, data)
    }

    final override fun visitTypeParameter(typeParameter: FirTypeParameter, data: D): CompositeTransformResult<FirElement> {
        return transformTypeParameter(typeParameter, data)
    }

    final override fun visitTypeProjection(typeProjection: FirTypeProjection, data: D): CompositeTransformResult<FirElement> {
        return transformTypeProjection(typeProjection, data)
    }

    final override fun visitTypeProjectionWithVariance(typeProjectionWithVariance: FirTypeProjectionWithVariance, data: D): CompositeTransformResult<FirElement> {
        return transformTypeProjectionWithVariance(typeProjectionWithVariance, data)
    }

    final override fun visitTypeWithNullability(typeWithNullability: FirTypeWithNullability, data: D): CompositeTransformResult<FirElement> {
        return transformTypeWithNullability(typeWithNullability, data)
    }

    final override fun visitTypedDeclaration(typedDeclaration: FirTypedDeclaration, data: D): CompositeTransformResult<FirElement> {
        return transformTypedDeclaration(typedDeclaration, data)
    }

    final override fun visitUserType(userType: FirUserType, data: D): CompositeTransformResult<FirElement> {
        return transformUserType(userType, data)
    }

    final override fun visitValueParameter(valueParameter: FirValueParameter, data: D): CompositeTransformResult<FirElement> {
        return transformValueParameter(valueParameter, data)
    }

    final override fun visitVariable(variable: FirVariable, data: D): CompositeTransformResult<FirElement> {
        return transformVariable(variable, data)
    }

    final override fun visitWhenBranch(whenBranch: FirWhenBranch, data: D): CompositeTransformResult<FirElement> {
        return transformWhenBranch(whenBranch, data)
    }

    final override fun visitWhenExpression(whenExpression: FirWhenExpression, data: D): CompositeTransformResult<FirElement> {
        return transformWhenExpression(whenExpression, data)
    }

    final override fun visitWhileLoop(whileLoop: FirWhileLoop, data: D): CompositeTransformResult<FirElement> {
        return transformWhileLoop(whileLoop, data)
    }

    final override fun visitElement(element: FirElement, data: D): CompositeTransformResult<FirElement> {
        return transformElement(element, data)
    }

}
