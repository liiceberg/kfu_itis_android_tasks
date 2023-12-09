package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.MainActivity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.base.BaseFragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentProfileBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.di.ServiceLocator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.CurrentUser
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.PhoneInputTextWatcher
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.ValidationUtil

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        TODO hide icon in toolbar
        setToolbarTitle(R.string.profile)
        with(binding) {
            usernameTv.text =
                requireContext().getString(R.string.profile_username, CurrentUser.username)
            emailTv.text = requireContext().getString(R.string.profile_email, CurrentUser.email)
            phoneTv.text = requireContext().getString(R.string.profile_phone, CurrentUser.phone)

            phoneEt.apply {
                setText(CurrentUser.phone)
                addTextChangedListener(PhoneInputTextWatcher(this))
            }
            phoneEditBtn.setOnClickListener {
                if (ValidationUtil.validatePhone(phoneEt, context)) {
                    val phone = phoneEt.text.toString()
                    CurrentUser.phone = phone
                    phoneTv.text = requireContext().getString(R.string.profile_phone, CurrentUser.phone)
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            ServiceLocator.getDbInstance().userDao.updatePhone(
                                CurrentUser.userId,
                                phone
                            )
                        }
                    }
                }
            }
            passwordEditBtn.setOnClickListener {
                lifecycleScope.launch {
                    if (oldPasswordEt.text.toString() != getUserPassword()) {
                        oldPasswordEt.error = context?.getString(R.string.old_password_error)
                    } else {
                        if (ValidationUtil.validatePassword(
                                newPasswordEt,
                                newPasswordRepeatEt,
                                context
                            )
                        ) {
                            lifecycleScope.launch {
                                withContext(Dispatchers.IO) {
                                    ServiceLocator.getDbInstance().userDao.updatePassword(
                                        CurrentUser.userId,
                                        newPasswordEt.text.toString()
                                    )
                                }
                            }
                        }
                    }
                }
            }
            exitBtn.setOnClickListener {
                showExitDialog()
            }
            deleteBtn.setOnClickListener {
                showDeleteDialog()
            }
        }
    }

    private suspend fun getUserPassword(): String? {
        return lifecycleScope.async {
            withContext(Dispatchers.IO) {
                ServiceLocator.getDbInstance().userDao.get(CurrentUser.userId)
            }
        }.await()?.password
    }


    private fun showExitDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.warning))
            .setMessage(getString(R.string.exit_message))
            .setPositiveButton(getString(R.string.exit)) { _, _ ->
                logout()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.warning))
            .setMessage(getString(R.string.delete_message))
            .setPositiveButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.cancel()
            }
            .setNegativeButton(getString(R.string.delete_account)) { _, _ ->
                delete()
                logout()
            }
            .show()
    }

    private fun logout() {
        CurrentUser.userId = null
        CurrentUser.username = ""
        CurrentUser.phone = ""
        CurrentUser.email = ""
        parentFragmentManager.popBackStack()
        parentFragmentManager.beginTransaction()
            .replace(
                (requireActivity() as MainActivity).fragmentContainerId,
                SignInFragment(),
                SignInFragment.SIGN_IN_FRAGMENT_TAG,
            )
            .commit()
    }

    //    TODO 7 days
    private fun delete() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                ServiceLocator.getDbInstance().userDao.delete(CurrentUser.userId)
            }
        }
    }

    companion object {
        const val PROFILE_FRAGMENT_TAG = "PROFILE_FRAGMENT_TAG"
    }
}