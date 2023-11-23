package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.MainActivity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import kotlin.random.Random

object CoroutinesUtil {
    private var coroutineJob: Job? = null
    private var stopOnBackground = false
    private var coroutinesLeft = 0

    fun cancelJob(ctx: Context) {
        if (stopOnBackground) {
            coroutineJob?.cancel(ctx.getString(R.string.stop_coroutines))
        }
    }

    fun runCoroutines(lifecycleOwner: LifecycleOwner, ctx: Context) {
        var count = 0
        stopOnBackground = CoroutinesSettingsObject.isStopOnBackground
        coroutinesLeft = CoroutinesSettingsObject.number
        coroutineJob = lifecycleOwner.lifecycleScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    if (CoroutinesSettingsObject.isAsync) {
                        repeat(CoroutinesSettingsObject.number) {
                            launch { run(++count, ctx) }
                        }
                    } else {
                        repeat(CoroutinesSettingsObject.number) {
                            run(++count, ctx)
                        }
                    }
                }
            }.onSuccess {
                NotificationsUtil.createCoroutinesNotification(ctx)
            }.onFailure {
                Log.e(MainActivity::class.java.name, ctx.getString(R.string.coroutines_left_message, coroutinesLeft))
            }
            coroutineJob = null
        }
    }

    private suspend fun run(num: Int, ctx: Context) {
        println(ctx.getString(R.string.coroutines_run_message, num))
        delay(Random.nextInt(1, 5) * 1000L)
        coroutinesLeft--
    }
}