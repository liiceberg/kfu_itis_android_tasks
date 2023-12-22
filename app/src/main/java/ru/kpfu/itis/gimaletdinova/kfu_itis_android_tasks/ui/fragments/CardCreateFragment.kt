package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.base.BaseFragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentDatabaseBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.CardEntity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.di.ServiceLocator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.CurrentUser

class CardCreateFragment: BaseFragment(R.layout.fragment_database) {

    private val binding: FragmentDatabaseBinding by viewBinding(FragmentDatabaseBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarTitle(R.string.create_new_card)
        with(binding) {
            createBtn.setOnClickListener {
                lifecycleScope.launch {
                    if (validate()) {
                        val card = CardEntity(
                            title = titleEt.text.toString(),
                            description = descriptionEt.text.toString(),
                            instruments = instrumentsEt.text.toString(),
                            image = imageEt.text.toString(),
                            productionTime = durationEt.text.toString().toInt(),
                            authorId = CurrentUser.userId,
                            difficulty = getDifficulty(difficultyRg.checkedRadioButtonId)
                        )
                        saveCard(card)
                        Toast.makeText(context, R.string.card_created, Toast.LENGTH_SHORT).show()
                        clear()
                    }
                }
            }
        }
    }
    private fun getDifficulty(checkedId: Int) : String {
        return when(checkedId) {
            R.id.beginner -> getString(R.string.difficulty_beginner)
            R.id.medium -> getString(R.string.difficulty_medium)
            R.id.master -> getString(R.string.difficulty_master)
            else -> getString(R.string.difficulty_beginner)
        }
    }

    private suspend fun validate(): Boolean {
        val emptyFieldError = getString(R.string.empty_field_error)
        with (binding) {
            if (titleEt.text.isNullOrEmpty()) {
                titleEt.error = emptyFieldError
                return false
            }
            if (descriptionEt.text.isNullOrEmpty()) {
                descriptionEt.error = emptyFieldError
                return false
            }
            if (instrumentsEt.text.isNullOrEmpty()) {
                instrumentsEt.error = emptyFieldError
                return false
            }
            if (durationEt.text.isNullOrEmpty()) {
                durationEt.error = emptyFieldError
                return false
            }
            val exist = lifecycleScope.async {
                withContext(Dispatchers.IO) {
                    ServiceLocator.getDbInstance().cardDao.isExist(titleEt.text.toString(), durationEt.text.toString().toInt())
                }
            }.await()
            if (exist) {
                titleEt.error = context?.getString(R.string.card_already_exist_error)
                return false
            }
        }
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

    private fun clear() {
        with(binding) {
            titleEt.text = null
            descriptionEt.text = null
            instrumentsEt.text = null
            imageEt.text = null
            durationEt.text = null
            difficultyRg.check(R.id.beginner)
        }
    }
    companion object {
        const val CARD_CREATE_FRAGMENT_TAG = "CARD_CREATE_FRAGMENT_TAG"
    }

}