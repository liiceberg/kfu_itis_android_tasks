package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ActionType
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.MainActivity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    private var binding : FragmentFirstBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {

            textTv.text = getString(R.string.app_name)

            nextBtn.setOnClickListener {
                val enteredText = enterTextEt.text.toString()

                (requireActivity() as? MainActivity)?.goToScreen(
                    actionType = ActionType.REPLACE,
                    destination = SecondFragment.newInstance(enteredText),
                    tag = SecondFragment.SECOND_FRAGMENT_TAG,
                    isAddToBackStack = true,
                )

                (requireActivity() as? MainActivity)?.goToScreen(
                    actionType = ActionType.REPLACE,
                    destination = ThirdFragment.newInstance(enteredText),
                    tag = ThirdFragment.THIRD_FRAGMENT_TAG,
                    isAddToBackStack = true,
                )
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        const val FIRST_FRAGMENT_TAG = "FIRST_FRAGMENT_TAG"
    }

}