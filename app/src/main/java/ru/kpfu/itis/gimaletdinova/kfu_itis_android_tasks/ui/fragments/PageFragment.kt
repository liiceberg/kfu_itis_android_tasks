package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ParamsKey
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter.FragmentAdapter
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentPageBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.AnswerData
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.QuestionData
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.QuestionGenerator

class PageFragment : Fragment(R.layout.fragment_page) {

    private var binding: FragmentPageBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            val number = requireArguments().getInt(ParamsKey.QUESTIONS_NUMBER_KEY)
            val questions = QuestionGenerator.getQuestions(number)
//TODO which FragmentManager??
            questionnaireVp.adapter = FragmentAdapter(questions, childFragmentManager, lifecycle)

            questionnaireVp.registerOnPageChangeCallback(object : OnPageChangeCallback() {

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    }

                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                    }

                })

        }

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        const val PAGE_FRAGMENT_TAG = "PAGE_FRAGMENT_TAG"
        fun newInstance(number: Int) = PageFragment().apply {
            arguments = bundleOf(
                ParamsKey.QUESTIONS_NUMBER_KEY to number
            )
        }
    }
}



