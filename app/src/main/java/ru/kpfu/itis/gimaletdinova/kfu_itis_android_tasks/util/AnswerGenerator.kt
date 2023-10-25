package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util

import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.AnswerData
import kotlin.random.Random

object AnswerGenerator {
    private const val e = 20;
    fun getAnswers(count: Int, realAnswer: Int): List<AnswerData> {
        val set = setOf(realAnswer)
        while (set.size != count) {
            set.plus(
                Random.nextInt(realAnswer - e, realAnswer + e)
            )
        }
        return set.map { num -> AnswerData(num) }
    }
}