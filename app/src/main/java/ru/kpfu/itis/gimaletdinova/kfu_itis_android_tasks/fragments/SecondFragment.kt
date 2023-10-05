package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ActionType
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.MainActivity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ParamsKey
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {

    private var binding: FragmentSecondBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val enteredText = arguments?.getString(ParamsKey.TEXT_KEY)

        binding?.run {

            if (!enteredText.isNullOrEmpty()) {
                textTv.text = enteredText
            }

            nextBtn.setOnClickListener {
                parentFragmentManager.popBackStack()

                (requireActivity() as? MainActivity)?.goToScreen(
                    actionType = ActionType.REPLACE,
                    destination = ThirdFragment.newInstance(enteredText),
                    tag = ThirdFragment.THIRD_FRAGMENT_TAG,
                    isAddToBackStack = true,
                )
            }

            previousBtn.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        const val SECOND_FRAGMENT_TAG = "SECOND_FRAGMENT_TAG"
        fun newInstance(text: String?) = SecondFragment().apply {
            arguments = bundleOf(ParamsKey.TEXT_KEY to text)
        }
    }

}