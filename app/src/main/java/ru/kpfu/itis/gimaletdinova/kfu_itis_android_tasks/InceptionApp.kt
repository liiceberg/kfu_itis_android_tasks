package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks

import android.app.Application
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.di.ServiceLocator

class InceptionApp : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.initData(this)
    }
}