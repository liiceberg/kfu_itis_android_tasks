package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.MainActivity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.base.BaseFragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentMainBinding

class MainFragment: BaseFragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with (binding) {
            createBtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        (requireActivity() as MainActivity).fragmentContainerId,
                        DatabaseFragment(),
                        DatabaseFragment.DATABASE_FRAGMENT_TAG,
                    )
                    .addToBackStack(null)
                    .commit()
            }

        }
    }

    companion object {
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
    }
}