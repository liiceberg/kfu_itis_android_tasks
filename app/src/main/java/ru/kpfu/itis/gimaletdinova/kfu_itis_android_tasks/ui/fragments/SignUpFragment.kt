package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.MainActivity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentSignUpBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.UserEntity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.di.ServiceLocator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.PhoneInputTextWatcher
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.ValidationUtil

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding: FragmentSignUpBinding by viewBinding(FragmentSignUpBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {

            phoneEt.apply {
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus && phoneEt.text.isNullOrEmpty()) {
                        phoneEt.setText("+7 (9")
                    }
                }
                addTextChangedListener(PhoneInputTextWatcher(this))
            }

            signUpBtn.setOnClickListener {
                lifecycleScope.launch {
                    if (validate()) {
                        val user = UserEntity(
                            username = usernameEt.text.toString(),
                            email = emailEt.text.toString(),
                            phone = phoneEt.text.toString(),
                            password = passwordEt.text.toString()
                        )
                        if (saveUser(user)) {
                            Toast.makeText(context, R.string.account_created, Toast.LENGTH_SHORT)
                                .show()

                            parentFragmentManager.beginTransaction()
                                .replace(
                                    (requireActivity() as MainActivity).fragmentContainerId,
                                    SignInFragment(),
                                    SignInFragment.SIGN_IN_FRAGMENT_TAG
                                )
                                .commit()
                        }
                    }
                }
            }
        }
    }

    private suspend fun validate(): Boolean {
        with(binding) {
            usernameEt.text?.let { username ->
                if (username.length < 3 || username.length > 16) {
                    usernameEt.error = context?.getString(R.string.username_error)
                    return false
                }
            }
            return ValidationUtil.validatePhone(phoneEt, context, lifecycleScope) &&
                    ValidationUtil.validatePassword(passwordEt, passwordRepeatEt, context)
                    && validateEmail(emailEt)
        }
    }
    private suspend fun validateEmail(emailEt: AppCompatEditText) : Boolean {
        emailEt.text?.let { email ->
            if (!email.matches(Regex("\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*\\.\\w{2,4}"))) {
                emailEt.error = context?.getString(R.string.email_error)
                return false
            }
            val exist = lifecycleScope.async {
                withContext(Dispatchers.IO) {
                    ServiceLocator.getDbInstance().userDao.isEmailExist(email.toString())
                }
            }.await()
            if (exist) {
                emailEt.error = getString(R.string.email_already_exist)
                return false
            }
        }
        return true
    }

    private fun saveUser(user: UserEntity): Boolean {
        kotlin.runCatching {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    ServiceLocator.getDbInstance().userDao.save(user)
                }
            }
        }.onSuccess {
            return true
        }
        return false
    }

    companion object {
        const val SIGN_UP_FRAGMENT_TAG = "SIGN_UP_FRAGMENT_TAG"
    }
}