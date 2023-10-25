package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util

import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.QuestionData
import kotlin.random.Random

object QuestionGenerator {
    private const val from = 5
    private const val until = 11
    private const val bound = 20
    fun getQuestions(count: Int): List<QuestionData> {
        val list = mutableListOf<QuestionData>()
        var question = ""
        var answer = 0
        repeat(count) {
            val a = Random.nextInt(bound)
            val b = Random.nextInt(bound)
            when(Random.nextInt(0, 2)) {
                0 -> {
                    question = String.format("$a + $b")
                    answer = a + b
                }
                1-> {
                    question = String.format("$a x $b")
                    answer = a + b
                }

            }
            val answers = AnswerGenerator.getAnswers(
                Random.nextInt(from, until),
                answer
            )
            list.add(
                QuestionData(question, answers, answer)
            )

        }
        return list
    }
}