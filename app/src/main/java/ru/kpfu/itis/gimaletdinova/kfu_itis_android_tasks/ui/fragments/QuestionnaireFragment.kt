package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.AnswerData
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ParamsKey
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter.QuestionnaireAdapter
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentQuestionnaireBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.QuestionData

class QuestionnaireFragment : Fragment(R.layout.fragment_questionnaire) {
    private var binding: FragmentQuestionnaireBinding? = null
    private var adapterRv: QuestionnaireAdapter? = null
    private var answersList = mutableListOf<AnswerData>()
    private var checkedPosition = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionnaireBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val question = arguments?.getSerializable(ParamsKey.QUESTION_KEY) as? QuestionData

        question?.apply {
            answersList = answers.toMutableList()
        }

        adapterRv = QuestionnaireAdapter(
            items = answersList,
            onItemChecked = { position -> updateCheckedAnswerPosition(position) },
            onRootClicked = { position -> updateCheckedAnswerPosition(position) }
        )

        savedInstanceState?.let { bundle ->
            val position = bundle.getInt(ParamsKey.CHECKED_ANSWER)

            adapterRv?.apply {
                if (position != -1) {
                    items[position].isChecked = true
                    checkedPosition = position
                }
            }
        }

        binding?.run {
            questionTv.text = question?.formulation
            questionnaireRv.adapter = adapterRv
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(ParamsKey.CHECKED_ANSWER, checkedPosition)
    }

    private fun updateCheckedAnswerPosition(position: Int) {
        if (checkedPosition != -1) {
            answersList[checkedPosition].isChecked = false
            adapterRv?.notifyItemChanged(checkedPosition)
        } else {
            val f = requireActivity()
                .supportFragmentManager
                .findFragmentByTag(QuestionnairePageFragment.QUESTIONNAIRE_PAGE_FRAGMENT_TAG)
                    as QuestionnairePageFragment
            f.updateCount()
        }
        answersList[position].isChecked = true
        checkedPosition = position
        adapterRv?.notifyItemChanged(position)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(position: Int, question: QuestionData) = QuestionnaireFragment().apply {
            arguments = bundleOf(
                ParamsKey.QUESTION_POSITION_KEY to position,
                ParamsKey.QUESTION_KEY to question
            )
        }
    }

}