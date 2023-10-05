package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ParamsKey
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(R.layout.fragment_third) {

    private var binding: FragmentThirdBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val enteredText = arguments?.getString(ParamsKey.TEXT_KEY)

        binding?.run {

            if (!enteredText.isNullOrEmpty()) {
                textTv.text = enteredText
            }

        }
    }

    companion object {
        const val THIRD_FRAGMENT_TAG = "THIRD_FRAGMENT_TAG"
        fun newInstance(text: String?) = ThirdFragment().apply {
            arguments = bundleOf(ParamsKey.TEXT_KEY to text)
        }
    }

}