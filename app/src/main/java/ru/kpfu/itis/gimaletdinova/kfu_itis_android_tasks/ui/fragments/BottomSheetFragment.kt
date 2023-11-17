package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter.GalleryAdapter
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentBottomSheetBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.DataRepository

class BottomSheetFragment(private val adapter: GalleryAdapter?) : BottomSheetDialogFragment(R.layout.fragment_bottom_sheet) {
    private var binding: FragmentBottomSheetBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        calculateViewHeight()
        binding?.run {

            addBtn.setOnClickListener {
                if (verifyNumber()) {
                    val number = numberEt.text.toString().toInt()
                    adapter?.setItems(DataRepository.addCards(number))
                    dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun calculateViewHeight() {
        val displayMetrics = requireContext().resources.displayMetrics
        val dialogHeight = displayMetrics.heightPixels / 3

        val layoutParams =
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                    height = dialogHeight
                }

        binding?.root?.layoutParams = layoutParams
    }

    private fun verifyNumber(): Boolean {
        binding?.numberEt?.run {
            val number = text.toString().toIntOrNull()
            if (number == null) {
                error = getString(R.string.start_page_error_text_1)
                return false
            }
            if (number !in 1..5) {
                error = getString(R.string.bottom_sheet_error_text)
                return false
            }
            error = null
        }
        return true
    }

    companion object {
        const val FRAGMENT_TAG = "BOTTOM_SHEET_FRAGMENT_TAG"
    }
}