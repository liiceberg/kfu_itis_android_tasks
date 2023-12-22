package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.base.BaseActivity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.UserEntity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.di.ServiceLocator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments.MainFragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments.SignInFragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.CurrentUser
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.ParamKeys

class MainActivity : BaseActivity() {

    override val fragmentContainerId: Int = R.id.main_activity_container
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isRemembered()
    }

    private fun isRemembered() {
        lifecycleScope.launch {
            val userId = ServiceLocator.getSharedPreferences().getInt(ParamKeys.USER_ID_KEY, -1)
            if (userId != -1) {
                updateCurrentUser(getUser(userId))
            }
            if (CurrentUser.userId != null) {
                supportFragmentManager.beginTransaction()
                    .add(
                        fragmentContainerId,
                        MainFragment(),
                        MainFragment.MAIN_FRAGMENT_TAG,
                    )
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .add(
                        fragmentContainerId,
                        SignInFragment(),
                        SignInFragment.SIGN_IN_FRAGMENT_TAG,
                    )
                    .commit()
            }
        }
    }

    private suspend fun getUser(id: Int): UserEntity? {
        return lifecycleScope.async {
            withContext(Dispatchers.IO) {
                ServiceLocator.getDbInstance().userDao.get(id)
            }
        }.await()
    }

    fun updateCurrentUser(user: UserEntity?) {
        user?.apply {
            CurrentUser.userId = id
            CurrentUser.username = username
            CurrentUser.email = email
            CurrentUser.phone = phone
        }
    }

}