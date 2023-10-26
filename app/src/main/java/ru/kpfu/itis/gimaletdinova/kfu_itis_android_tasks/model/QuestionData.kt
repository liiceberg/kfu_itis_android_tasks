package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model

import java.io.Serializable

data class QuestionData(
    val formulation: String,
    val answers: List<AnswerData>,
    val realAnswerPosition: Int
): Serializable
