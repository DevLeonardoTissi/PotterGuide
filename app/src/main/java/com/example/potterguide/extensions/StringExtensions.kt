package com.example.potterguide.extensions

fun String.addCaractere(char: Char, index: Int) =
    StringBuilder(this).apply {
        insert(index, char)
    }.toString()
