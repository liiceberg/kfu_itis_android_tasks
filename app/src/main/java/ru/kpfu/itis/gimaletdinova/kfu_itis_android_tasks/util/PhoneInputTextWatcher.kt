package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
class PhoneInputTextWatcher(private val phoneEt: AppCompatEditText): TextWatcher {
    override fun beforeTextChanged(
        input: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {}

    @SuppressLint("SetTextI18n")
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

            if (input.length == 7 && before == 0) {
                phoneEt.removeTextChangedListener(this)
                phoneEt.setText("${input})-")
                phoneEt.setSelection(phoneEt.text?.length ?: 0)
                phoneEt.addTextChangedListener(this)
            }
            if ((input.length == 12 || input.length == 15) && before == 0) {
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
            if (input.length == 19) {
                phoneEt.removeTextChangedListener(this)
                phoneEt.setText(input.subSequence(0, 18))
                phoneEt.setSelection(phoneEt.text?.length ?: 0)
                phoneEt.addTextChangedListener(this)
            }
        }
    }

    override fun afterTextChanged(input: Editable?) {}

}