package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.MainActivity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R

object NotificationsUtil {

    const val NOTIFICATION_CHANNEL_DEFAULT = "NOTIFICATION_CHANNEL_DEFAULT"
    const val NOTIFICATION_CHANNEL_LOW = "NOTIFICATION_CHANNEL_LOW"
    const val NOTIFICATION_CHANNEL_HIGH = "NOTIFICATION_CHANNEL_HIGH"

    const val ACTION_NAME = "ACTION_NAME"

    const val FIRST_BTN_REQUEST_CODE = 1001
    const val SECOND_BTN_REQUEST_CODE = 1002
    const val REQUEST_CODE = 111

    private var notificationId = 0

    fun initChannels(ctx: Context) {

        (ctx.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.let { manager ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val channelDefault = NotificationChannel(
                    NOTIFICATION_CHANNEL_DEFAULT,
                    ctx.getString(R.string.default_notification_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                )

                val channelLow = NotificationChannel(
                    NOTIFICATION_CHANNEL_LOW,
                    ctx.getString(R.string.low_notification_channel_name),
                    NotificationManager.IMPORTANCE_LOW
                )

                val channelHigh = NotificationChannel(
                    NOTIFICATION_CHANNEL_HIGH,
                    ctx.getString(R.string.high_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH
                )

                manager.createNotificationChannel(channelDefault)
                manager.createNotificationChannel(channelLow)
                manager.createNotificationChannel(channelHigh)
            }
        }
    }

    fun createNotification(ctx: Context, title: String, text: String) {

        (ctx.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.let { manager ->

            val notification: NotificationCompat.Builder =
                NotificationCompat.Builder(ctx, NotificationsSettingsObject.importance)
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setVisibility(NotificationsSettingsObject.visibility)
                    .setAutoCancel(true)

            if (NotificationsSettingsObject.isTextLong) {
                notification.setStyle(NotificationCompat.BigTextStyle().bigText(text))
            }

            if (NotificationsSettingsObject.isButtonsDisplay) {

                val firstIntent = Intent(ctx, MainActivity::class.java)

                firstIntent.putExtra(ACTION_NAME, FIRST_BTN_REQUEST_CODE)

                val firstPendingIntent = PendingIntent.getActivity(
                    ctx,
                    FIRST_BTN_REQUEST_CODE,
                    firstIntent,
                    PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
                )

                notification.addAction(
                    R.drawable.baseline_looks_one_24,
                    ctx.getString(R.string.first_action),
                    firstPendingIntent
                )

                val secondIntent = Intent(ctx, MainActivity::class.java)

                secondIntent.putExtra(ACTION_NAME, SECOND_BTN_REQUEST_CODE)

                val secondPendingIntent = PendingIntent.getActivity(
                    ctx,
                    SECOND_BTN_REQUEST_CODE,
                    secondIntent,
                    PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
                )

                notification.addAction(
                    R.drawable.baseline_looks_two_24,
                    ctx.getString(R.string.second_action),
                    secondPendingIntent
                )
            }

            val intent = Intent(ctx, MainActivity::class.java)

            val pendingIntent = PendingIntent.getActivity(
                ctx,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
            )

            notification.setContentIntent(pendingIntent)

            manager.notify(++notificationId, notification.build())
        }
    }

    fun createCoroutinesNotification(ctx: Context) {

        (ctx.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.let { manager ->

            val notification: NotificationCompat.Builder =
                NotificationCompat.Builder(ctx, NOTIFICATION_CHANNEL_LOW)
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle(ctx.getString(R.string.coroutines_notifications_title))
                    .setContentText(ctx.getString(R.string.coroutines_notifications_text))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setAutoCancel(true)

            manager.notify(++notificationId, notification.build())
        }
    }

}