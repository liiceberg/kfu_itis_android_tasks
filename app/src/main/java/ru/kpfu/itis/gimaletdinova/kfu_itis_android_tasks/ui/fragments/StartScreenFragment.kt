package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentStartScreenBinding


class StartScreenFragment : Fragment(R.layout.fragment_start_screen) {
    private var binding: FragmentStartScreenBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.run {

            phoneEt.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    phoneEt.setText("+7 (9")
                }
            }
            phoneEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    input: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    input: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    input?.let {
                        if (input.length == 4) {
                            phoneEt.setText("${input}9")
                            phoneEt.setSelection(phoneEt.text?.length ?: 0)
                        }

                        if (input.length == 7 && start > 0 && before == 0) {
                            phoneEt.removeTextChangedListener(this)
                            phoneEt.setText("${input})-")
                            phoneEt.setSelection(phoneEt.text?.length ?: 0)
                            phoneEt.addTextChangedListener(this)
                        }
                        if ((input.length == 12 || input.length == 15) && start > 0 && before == 0) {
                            phoneEt.removeTextChangedListener(this)
                            phoneEt.setText("${input}-")
                            phoneEt.setSelection(phoneEt.text?.length ?: 0)
                            phoneEt.addTextChangedListener(this)
                        }

                        if (input.length == 8 && before > 0) {
                            phoneEt.removeTextChangedListener(this)
                            phoneEt.setText(input.subSequence(0, 6))
                            phoneEt.setSelection(phoneEt.text?.length ?: 0)
                            phoneEt.addTextChangedListener(this)
                        }
                        if (input.length == 12 && before > 0) {
                            phoneEt.removeTextChangedListener(this)
                            phoneEt.setText(input.subSequence(0, 11))
                            phoneEt.setSelection(phoneEt.text?.length ?: 0)
                            phoneEt.addTextChangedListener(this)
                        }
                        if (input.length == 15 && before > 0) {
                            phoneEt.removeTextChangedListener(this)
                            phoneEt.setText(input.subSequence(0, 14))
                            phoneEt.setSelection(phoneEt.text?.length ?: 0)
                            phoneEt.addTextChangedListener(this)
                        }

                    }
                }

                override fun afterTextChanged(input: Editable?) {
                    if (!verifyPhone()) {
                        phoneEt.error = context?.getString(R.string.phone_error)
                    } else {
                        phoneEt.error = null
                    }
                    verifyAll()
                }

            })

            countEt.addTextChangedListener{
                if (!verifyCount()) {
                    countEt.error = context?.getString(R.string.count_error)
                } else {
                    countEt.error = null
                }
                verifyAll()
            }

            startBtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        PageFragment.newInstance(countEt.text.toString().toInt()),
                    )
                    .addToBackStack(null)
                    .commit()
            }

        }
    }
    private fun verifyPhone(): Boolean {
        val len = binding?.phoneEt?.text?.length
        return len == 18
    }

    private fun verifyCount(): Boolean {
        val count = binding?.countEt?.text.toString().toIntOrNull()
        return count != null && count in 9..30
    }

    private fun verifyAll() {
        binding?.startBtn?.isEnabled = verifyPhone() && verifyCount()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}