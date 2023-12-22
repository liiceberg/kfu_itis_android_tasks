package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.base.BaseFragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentDetailBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.CardEntity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.RatingEntity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.di.ServiceLocator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.CurrentUser
import java.text.DecimalFormat
import java.text.Format

class DetailFragment : BaseFragment(R.layout.fragment_detail) {
    private val binding: FragmentDetailBinding by viewBinding(FragmentDetailBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            val id = arguments?.getInt(CARD_ID_KEY)
            var card: CardEntity? = null
            val numberFormat = DecimalFormat("#.##")
            lifecycleScope.launch {
                card = getCard(id)
                titleTv.text = card?.title


                ratingTv.text = getString(R.string.rating, numberFormat.format(card?.rating))

                val userRating = getUserRating(card?.id)
                userRating?.let {
                    ratingRb.rating = userRating.toFloat()
                }

                difficultyTv.text = getString(R.string.difficulty, card?.difficulty)
                timeTv.text = getString(R.string.time, card?.productionTime)
                instrumentsTv.text = getString(R.string.instruments, card?.instruments)
                description.text = getString(R.string.description, card?.description)

                Glide.with(requireContext())
                    .load(card?.image)
                    .error(R.drawable.card_img)
                    .into(imageIv)
            }
            ratingRb.onRatingBarChangeListener =
                RatingBar.OnRatingBarChangeListener { _, rating, fromUser ->
                    if (fromUser) {
                        changeRating(rating.toDouble(), card, numberFormat)
                    }
                }
        }
    }

    private fun changeRating(userRating: Double, card: CardEntity?, numberFormat: Format) {
        lifecycleScope.launch {
            val newRating: Double
            withContext(Dispatchers.IO) {
                ServiceLocator.getDbInstance().ratingDao.apply {
                    delete(CurrentUser.userId, card?.id)
                    val allRatings = get(card?.id)
                    newRating = (allRatings.sum() + userRating) / (allRatings.size + 1)
                    card?.rating = newRating

                    save(RatingEntity(
                            userId = CurrentUser.userId,
                            cardId = card?.id,
                            rating = userRating
                    ))
                    card?.let { ServiceLocator.getDbInstance().cardDao.update(it) }
                }
            }
            binding.ratingTv.text = getString(R.string.rating, numberFormat.format(newRating))
        }
    }

    private suspend fun getCard(id: Int?): CardEntity? {
        val card = lifecycleScope.async {
            withContext(Dispatchers.IO) {
                ServiceLocator.getDbInstance().cardDao.get(id)
            }
        }.await()
        return card
    }

    private suspend fun getUserRating(cardId: Int?): Double? {
        val rating = lifecycleScope.async {
            withContext(Dispatchers.IO) {
                ServiceLocator.getDbInstance().ratingDao.get(CurrentUser.userId, cardId)
            }
        }.await()
        return rating
    }


    companion object {
        const val DETAIL_FRAGMENT_TAG = "DETAIL_FRAGMENT_TAG"
        private const val CARD_ID_KEY = "CARD_ID_KEY"
        fun newInstance(id: Int?) =
            DetailFragment().apply {
                arguments = bundleOf(CARD_ID_KEY to id)
            }
    }
}