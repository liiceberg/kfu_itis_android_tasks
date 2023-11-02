package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentStartScreenBinding

class StartScreenFragment: Fragment(R.layout.fragment_start_screen) {

    private var binding: FragmentStartScreenBinding? = null
    private var cardsNumber: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartScreenBinding.inflate(layoutInflater, container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {

            applyBtn.setOnClickListener {

                if (verifyNumber()) {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.container, GalleryFragment.newInstance(cardsNumber))
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    private fun verifyNumber(): Boolean {
        binding?.numberEt?.run {
            val number = text.toString().toIntOrNull()
            if (number == null) {
                error = getString(R.string.start_page_error_text_1)
                return false
            }
            if (number !in 0..45) {
                error = getString(R.string.start_page_error_text_2)
                return false
            }
            error = null
            cardsNumber = number
        }

        return true
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}