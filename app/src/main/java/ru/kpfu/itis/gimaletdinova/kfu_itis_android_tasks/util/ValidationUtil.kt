package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R

object ValidationUtil {

    fun validatePassword(passwordEt: AppCompatEditText, passwordRepeatEt: AppCompatEditText, context: Context?): Boolean {
        passwordEt.text?.let { password ->
            if (!password.matches(Regex("\\w{8,}"))) {
                passwordEt.error = context?.getString(R.string.password_error)
                return false
            }
            if (password.matches(Regex("\\d+"))) {
                passwordEt.error = context?.getString(R.string.password_digits_match_error)
                return false
            }
            if (password.matches(Regex("[A-Za-z]+"))) {
                passwordEt.error = context?.getString(R.string.password_letters_match_error)
                return false
            }
        }
        if (passwordEt.text.toString() != passwordRepeatEt.text.toString()) {
            passwordRepeatEt.error = context?.getString(R.string.passwords_not_equals_error)
            return false
        }
        return true
    }

    fun validatePhone(phoneEt: AppCompatEditText, context: Context?): Boolean {
        if (phoneEt.text?.length != 18) {
            phoneEt.error = context?.getString(R.string.phone_error)
            return false
        }
//        TODO unique
        return true
    }

}