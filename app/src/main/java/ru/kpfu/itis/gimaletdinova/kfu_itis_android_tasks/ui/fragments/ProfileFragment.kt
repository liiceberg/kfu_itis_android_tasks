package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.MainActivity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.base.BaseFragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentProfileBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.PhoneInputTextWatcher

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(binding) {
            usernameTv.text = requireContext().getString(R.string.profile_username, "")
            emailTv.text = requireContext().getString(R.string.profile_email, "")
            phoneTv.text = requireContext().getString(R.string.profile_phone, "")

            phoneEt.apply {
                setText("")
                addTextChangedListener(PhoneInputTextWatcher(this))
            }
            phoneEditBtn.setOnClickListener {

            }
            passwordEditBtn.setOnClickListener {

            }
            exitBtn.setOnClickListener {
                showExitDialog()
            }
            deleteBtn.setOnClickListener {
                showDeleteDialog()
            }
        }
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
//                TODO
                logout()
            }
            .show()
    }

    private fun logout() {
        parentFragmentManager.popBackStack()
        parentFragmentManager.beginTransaction()
            .add(
                (requireActivity() as MainActivity).fragmentContainerId,
                SignInFragment(),
                SignInFragment.SIGN_IN_FRAGMENT_TAG,
            )
            .commit()
    }
}