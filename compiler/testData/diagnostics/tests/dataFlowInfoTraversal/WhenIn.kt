fun foo(x: Int, list: List<Int>?) {
    when (x) {
        in list!! -> list : List<Int>
        else -> {}
    }
}

fun whenWithoutSubject(x: Int, list: List<Int>?) {
    when {
        x in list!! -> list : List<Int>
        else -> {}
    }
}
