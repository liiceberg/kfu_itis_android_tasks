package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ParamsKey
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentFourthBinding


class FourthFragment: Fragment(R.layout.fragment_fourth) {
    private var binding: FragmentFourthBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstText = arguments?.getString(ParamsKey.TEXT_KEY_1)
        val secondText = arguments?.getString(ParamsKey.TEXT_KEY_2)
        val thirdText = arguments?.getString(ParamsKey.TEXT_KEY_3)

        binding?.run {
            if (!firstText.isNullOrEmpty()) {
                firstTv.text = firstText
            }
            if (!secondText.isNullOrEmpty()) {
                secondTv.text = secondText
            }
            if (!thirdText.isNullOrEmpty()) {
                thirdTv.text = thirdText
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object{
        const val FOURTH_FRAGMENT_TAG = "FOURTH_FRAGMENT_TAG"
        fun newInstance(text1: String?, text2: String?, text3: String?) = FourthFragment().apply {
            arguments = bundleOf(
                ParamsKey.TEXT_KEY_1 to text1,
                ParamsKey.TEXT_KEY_2 to text2,
                ParamsKey.TEXT_KEY_3 to text3)
        }
    }
}