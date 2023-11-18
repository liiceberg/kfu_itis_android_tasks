package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentCoroutinesSettingsBinding

class CoroutinesSettingsFragment: Fragment(R.layout.fragment_coroutines_settings) {

    private val binding: FragmentCoroutinesSettingsBinding by viewBinding(
        FragmentCoroutinesSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}