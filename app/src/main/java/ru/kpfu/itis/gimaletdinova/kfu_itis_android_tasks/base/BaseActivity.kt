package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract class BaseActivity : AppCompatActivity() {

    abstract val fragmentContainerId: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {

            override fun onFragmentViewCreated(
                fm: FragmentManager,
                fragment: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                super.onFragmentViewCreated(fm, fragment, v, savedInstanceState)
                (fragment as? BaseFragment)?.getToolbar().let { safeToolbar ->
                    setSupportActionBar(safeToolbar)
                    supportActionBar?.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
                }
            }
        }, false)
    }

}