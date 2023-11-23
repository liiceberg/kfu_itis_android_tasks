package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util

import androidx.core.app.NotificationCompat

object NotificationsSettingsObject {
    var importance = NotificationsUtil.NOTIFICATION_CHANNEL_LOW
    var visibility = NotificationCompat.VISIBILITY_PUBLIC
    var isTextLong = false
    var isButtonsDisplay = false
}