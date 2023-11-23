package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui

import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentNotificationsSettingsBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.NotificationsUtil
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.NotificationsSettingsObject

class NotificationsSettingsFragment : Fragment(R.layout.fragment_notifications_settings) {

    private val binding: FragmentNotificationsSettingsBinding by viewBinding(
        FragmentNotificationsSettingsBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            importanceGroup.setOnCheckedChangeListener { _, checkedId ->
                NotificationsSettingsObject.importance = when (checkedId) {
                    R.id.importance_high_rb -> NotificationsUtil.NOTIFICATION_CHANNEL_DEFAULT
                    R.id.importance_medium_rb -> NotificationsUtil.NOTIFICATION_CHANNEL_LOW
                    R.id.importance_urgent_rb -> NotificationsUtil.NOTIFICATION_CHANNEL_HIGH
                    else -> ""
                }
            }
            visibilityGroup.setOnCheckedChangeListener { _, checkedId ->
                NotificationsSettingsObject.visibility = when (checkedId) {
                    R.id.visibility_private_rb -> NotificationCompat.VISIBILITY_PRIVATE
                    R.id.visibility_secret_rb -> NotificationCompat.VISIBILITY_SECRET
                    R.id.visibility_public_rb -> NotificationCompat.VISIBILITY_PUBLIC
                    else -> 10
                }
            }
            longTextCb.setOnCheckedChangeListener { _, isChecked ->
                NotificationsSettingsObject.isTextLong = isChecked
            }
            displayButtonsCb.setOnCheckedChangeListener { _, isChecked ->
                NotificationsSettingsObject.isButtonsDisplay = isChecked
            }
        }
    }
}