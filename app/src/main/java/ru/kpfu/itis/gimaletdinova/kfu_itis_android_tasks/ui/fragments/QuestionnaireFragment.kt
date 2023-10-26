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
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.AnswerGenerator
import kotlin.random.Random

class QuestionnaireFragment : Fragment(R.layout.fragment_questionnaire) {
    private var binding: FragmentQuestionnaireBinding? = null
    private var adapterRv: QuestionnaireAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionnaireBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRv()
    }

    private fun initRv() {
        val question = requireArguments().getSerializable(ParamsKey.QUESTION_KEY) as QuestionData
        adapterRv = QuestionnaireAdapter(
            items = question.answers.toMutableList(),
            onItemChecked = { position -> updateAnswer(position)
            },
            onRootClicked = {position -> updateAnswer(position)}
            )
        binding?.run {
            questionTv.text = question.formulation
            questionnaireRv.adapter = adapterRv

        }
    }

    private fun updateAnswer(position: Int) {
// TODO
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        const val QUESTIONNAIRE_FRAGMENT_TAG = "QUESTIONNAIRE_FRAGMENT_TAG"
        fun newInstance(position: Int, question: QuestionData) = QuestionnaireFragment().apply {
            arguments = bundleOf(
                ParamsKey.QUESTION_POSITION_KEY to position,
                ParamsKey.QUESTION_KEY to question
            )
        }
    }

}