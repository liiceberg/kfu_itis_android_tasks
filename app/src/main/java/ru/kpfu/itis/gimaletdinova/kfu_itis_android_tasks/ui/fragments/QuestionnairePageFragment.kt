package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ParamsKey
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter.QuestionnairePageAdapter
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentQuestionnairePageBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.QuestionGenerator

class QuestionnairePageFragment : Fragment(R.layout.fragment_questionnaire_page) {

    private var binding: FragmentQuestionnairePageBinding? = null
    private var count = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionnairePageBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {
            val number = requireArguments().getInt(ParamsKey.QUESTIONS_NUMBER_KEY)
            val questions = QuestionGenerator.getQuestions(number)

            questionnaireVp.apply {
                adapter = QuestionnairePageAdapter(questions, childFragmentManager, lifecycle)

                registerOnPageChangeCallback(object : OnPageChangeCallback() {

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        if (positionOffset == 0.0f) {
                            when (position) {
                                0 -> setCurrentItem(number, false)
                                number + 1 -> setCurrentItem(1, false)
                            }
                        }
                    }

                    override fun onPageSelected(position: Int) {
                        val realPosition = when (position) {
                            0 -> number
                            number + 1 -> 1
                            else -> position
                        }
                        binding?.run {
                            currentNumberTv.text = "$realPosition/$number"

                            if (realPosition == number) {
                                saveBtn.visibility = View.VISIBLE
                                saveBtn.isEnabled = (count == number)

                            } else {
                                saveBtn.visibility = View.INVISIBLE
                            }
                        }
                    }

                })
                setCurrentItem(1, false)

                binding?.saveBtn?.setOnClickListener {
                    Toast.makeText(requireContext(), "Results saved", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    fun updateCount() {
        count++
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        const val QUESTIONNAIRE_PAGE_FRAGMENT_TAG = "QUESTIONNAIRE_PAGE_FRAGMENT_TAG"
        fun newInstance(number: Int) = QuestionnairePageFragment().apply {
            arguments = bundleOf(
                ParamsKey.QUESTIONS_NUMBER_KEY to number
            )
        }
    }
}



