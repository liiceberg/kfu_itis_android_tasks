package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util

import android.util.DisplayMetrics

fun Int.getValueInPx(dm: DisplayMetrics): Int {
    return (this * dm.density).toInt()
}