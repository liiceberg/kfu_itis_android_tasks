package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.MainActivity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentSignInBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.UserEntity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.di.ServiceLocator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.CurrentUser

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding: FragmentSignInBinding by viewBinding(FragmentSignInBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(binding) {

            signUpBtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        (requireActivity() as MainActivity).fragmentContainerId,
                        SignUpFragment(),
                        SignUpFragment.SIGN_UP_FRAGMENT_TAG
                    )
                    .addToBackStack(null)
                    .commit()
            }

            signInBtn.setOnClickListener {
                lifecycleScope.launch {
                    if (validate()) {
                        parentFragmentManager.popBackStack()
                        parentFragmentManager.beginTransaction()
                            .replace(
                                (requireActivity() as MainActivity).fragmentContainerId,
                                MainFragment(),
                                MainFragment.MAIN_FRAGMENT_TAG
                            )
                            .commit()
                    }
                }
            }

        }
    }

    private suspend fun validate(): Boolean {
        with(binding) {
            val email = emailEt.text.toString()
            val password = passwordEt.text.toString()
            val user = lifecycleScope.async {
                withContext(Dispatchers.IO) {
                    ServiceLocator.getDbInstance().userDao.get(email)
                }
            }.await()

            if (user == null) {
                emailEt.error = context?.getString(R.string.user_not_found_error)
                return false
            }
            if (user.password != password) {
                passwordEt.error = context?.getString(R.string.incorrect_password_error)
                return false
            }
            updateCurrentUser(user)
            return true
        }
    }

    private fun updateCurrentUser(user: UserEntity) {
        CurrentUser.userId = user.id
        CurrentUser.username = user.username
        CurrentUser.email = user.email
        CurrentUser.phone = user.phone
    }

    companion object {
        const val SIGN_IN_FRAGMENT_TAG = "SIGN_IN_FRAGMENT_TAG"
    }

}