package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentQuestionnaireBinding

class QuestionnaireFragment: Fragment(R.layout.fragment_questionnaire) {
    private var binding: FragmentQuestionnaireBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionnaireBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}