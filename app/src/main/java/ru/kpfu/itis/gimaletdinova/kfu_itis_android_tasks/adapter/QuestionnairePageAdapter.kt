package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.QuestionData
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments.QuestionnaireFragment

class QuestionnairePageAdapter(
    private val questions: List<QuestionData>,
    manager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(manager, lifecycle) {
    override fun getItemCount(): Int {
        return questions.size + 2
    }

    override fun createFragment(position: Int): Fragment {
        val realPosition = when (position) {
            0 -> questions.size - 1
            questions.size + 1 -> 0
            else -> position - 1
        }
        return QuestionnaireFragment.newInstance(realPosition, questions[realPosition])
    }
}