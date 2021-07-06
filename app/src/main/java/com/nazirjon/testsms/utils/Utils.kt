package com.nazirjon.testsms.utils


fun String.digitsOnly(): String{
    val regex = Regex("[^0-9]")
    return regex.replace(this, "")
}
fun String.alphaNumericOnly(): String{
    val regex = Regex("[^A-Za-z0-9 ]")
    return regex.replace(this, "")
}