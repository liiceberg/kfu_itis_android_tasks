package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.ActivityMainBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.fragments.FirstFragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.fragments.FourthFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        goToScreen(
            R.id.main_activity_container,
            ActionType.ADD,
            FirstFragment(),
            FirstFragment.FIRST_FRAGMENT_TAG,
            true
        )

        binding?.run {
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {

                mainActivityContainer2.visibility = View.VISIBLE

                goToScreen(
                    R.id.main_activity_container_2,
                    ActionType.ADD,
                    FourthFragment(),
                    FourthFragment.FOURTH_FRAGMENT_TAG,
                    true
                )
            } else {
                mainActivityContainer2.visibility = View.GONE
            }
        }
    }

    fun goToScreen(
        fragmentContainerId: Int,
        actionType: ActionType,
        destination: Fragment,
        tag: String? = null,
        isAddToBackStack: Boolean = true
    ) {
        supportFragmentManager.beginTransaction().apply {
            when (actionType) {
                ActionType.ADD -> {
                    this.add(fragmentContainerId, destination, tag)
                }

                ActionType.REPLACE -> {
                    this.replace(fragmentContainerId, destination, tag)
                }

                ActionType.REMOVE -> {
                    this.remove(destination)
                }

                else -> Unit
            }
            if (isAddToBackStack) {
                this.addToBackStack(null)
            }
        }.commit()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}
