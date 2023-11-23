package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentMainScreenBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.AirplaneModeHandler
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.NotificationsUtil

class MainScreenFragment : Fragment(R.layout.fragment_main_screen) {

    private val binding: FragmentMainScreenBinding by viewBinding(FragmentMainScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(binding) {
            AirplaneModeHandler.changeButtonEnable(sendBtn)

            sendBtn.setOnClickListener {
                if (titleEt.text.isNullOrEmpty()) {
                    titleEt.error = getString(R.string.empty_filed_error)
                }
                if (textEt.text.isNullOrEmpty()) {
                    textEt.error = getString(R.string.empty_filed_error)
                }
                else {

                    NotificationsUtil.createNotification(
                        requireContext(),
                        titleEt.text.toString(),
                        textEt.text.toString()
                    )
                }
            }
        }
    }

}