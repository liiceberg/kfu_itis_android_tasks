package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks

import android.os.Bundle
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.base.BaseActivity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments.ProfileFragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments.SignInFragment

class MainActivity : BaseActivity() {

    override val fragmentContainerId: Int = R.id.main_activity_container
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(
                fragmentContainerId,
                ProfileFragment(),
                SignInFragment.SIGN_IN_FRAGMENT_TAG,
            )
            .commit()
    }

}