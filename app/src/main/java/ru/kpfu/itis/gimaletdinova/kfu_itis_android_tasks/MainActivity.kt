package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }
    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}