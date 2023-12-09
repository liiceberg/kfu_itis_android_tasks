package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.base.BaseFragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentDatabaseBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.CardEntity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.di.ServiceLocator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.CurrentUser

class DatabaseFragment: BaseFragment(R.layout.fragment_database) {

    private val binding: FragmentDatabaseBinding by viewBinding(FragmentDatabaseBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarTitle(R.string.create_new_card)
        with(binding) {
            createBtn.setOnClickListener {
                if (validate()) {
                    val card = CardEntity(title = titleEt.text.toString(),
                        description = descriptionEt.text.toString(),
                        instruments = instrumentsEt.text.toString(),
                        image = imageEt.text.toString(),
                        productionTime = durationEt.text.toString().toInt(),
                        authorId = CurrentUser.userId)
                    saveCard(card)
                    Toast.makeText(context, R.string.card_created, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validate(): Boolean {
//        TODO
        return true
    }

    private fun saveCard(card: CardEntity): Boolean {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                ServiceLocator.getDbInstance().cardDao.save(card)
            }
        }
        return true
    }
    companion object {
        const val DATABASE_FRAGMENT_TAG = "DATABASE_FRAGMENT_TAG"
    }

}