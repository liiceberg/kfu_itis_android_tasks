package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util

import android.content.Context
import android.provider.Settings
import androidx.appcompat.widget.AppCompatButton

object AirplaneModeHandler {

    private var isAirplaneModeOn: Boolean = false
    fun changeButtonEnable(button: AppCompatButton?) {
        button?.isEnabled = !isAirplaneModeOn
    }
    fun onAirplaneModeChanged() {
        isAirplaneModeOn = !isAirplaneModeOn
    }
    fun init(ctx: Context) {
        isAirplaneModeOn = Settings.System.getInt(ctx.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
    }
    fun isAirplaneModeOn() = isAirplaneModeOn

}