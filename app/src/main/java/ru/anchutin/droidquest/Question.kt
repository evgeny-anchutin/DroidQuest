package ru.anchutin.droidquest

data class Question (
    val textResId: Int,
    val answerTrue: Boolean,
    var isDeceit: Boolean = false
)
