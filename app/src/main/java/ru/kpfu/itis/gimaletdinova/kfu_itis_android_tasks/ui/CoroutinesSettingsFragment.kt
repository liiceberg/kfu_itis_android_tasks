package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentCoroutinesSettingsBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.AirplaneModeHandler
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.CoroutinesSettingsObject
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.CoroutinesUtil

class CoroutinesSettingsFragment : Fragment(R.layout.fragment_coroutines_settings) {

    private val binding: FragmentCoroutinesSettingsBinding by viewBinding(
        FragmentCoroutinesSettingsBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            AirplaneModeHandler.changeButtonEnable(runBtn)

            numberSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    CoroutinesSettingsObject.number = progress
                    numberValueTv.text = progress.toString()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            })

            asyncCb.setOnCheckedChangeListener { _, isChecked ->
                CoroutinesSettingsObject.isAsync = isChecked
            }

            backgroundCb.setOnCheckedChangeListener { _, isChecked ->
                CoroutinesSettingsObject.isStopOnBackground = isChecked
            }

            runBtn.setOnClickListener {
                CoroutinesUtil.runCoroutines(viewLifecycleOwner, requireContext())
            }
        }

    }

}