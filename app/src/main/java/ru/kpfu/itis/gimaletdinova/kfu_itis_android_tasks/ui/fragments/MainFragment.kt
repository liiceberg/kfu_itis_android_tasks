package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.MainActivity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.base.BaseFragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentMainBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.CardEntity
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.db.entity.UserCardCrossReference
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.di.ServiceLocator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.CardModel
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.Favourite
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.comparator.CardProductionTimeComparator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.comparator.CardRatingComparator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.adapter.CardAdapter
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.adapter.diffutil.CardDiffUtilItemCallback
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.CurrentUser

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private var cardAdapter: CardAdapter? = null
    private var favouritesAdapter: CardAdapter? = null
    private val spanCount = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarTitle(R.string.main)
        initRecyclerView()
        initFavouritesRecyclerView()
        initFilter()
        with(binding) {
            createBtn.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(
                        (requireActivity() as MainActivity).fragmentContainerId,
                        CardCreateFragment(),
                        CardCreateFragment.CARD_CREATE_FRAGMENT_TAG,
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun initFilter() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.filter,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.filterSpinner.adapter = adapter
        }

        binding.filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
//                TODO correct
                val list = cardAdapter?.currentList?.map{it.copy()}
                when (position) {
                    0 -> {
                        list?.sortedWith(CardProductionTimeComparator())
                        list?.let {
                            cardAdapter?.setItems(it)
                        }
                    }

                    1 -> {
                        list?.sortedWith(CardProductionTimeComparator())
                        list?.let {
                            cardAdapter?.setItems(it.reversed())
                        }
                    }

                    2 -> {
                        list?.sortedWith(CardRatingComparator())
                        list?.let {
                            cardAdapter?.setItems(it)
                        }
                    }

                    3 -> {
                        list?.sortedWith(CardRatingComparator())
                        list?.let {
                            cardAdapter?.setItems(it.reversed())
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initRecyclerView() {
        cardAdapter = CardAdapter(
            diffCallback = CardDiffUtilItemCallback(),
            glide = Glide.with(this),
            onLikeClicked = ::onLikeClicked,
            onCardClicked = ::onCardClicked,
            onDeleteClicked = ::onDeleteClicked
        )
        binding.contentRv.apply {
            layoutManager = GridLayoutManager(
                requireContext(), spanCount, RecyclerView.VERTICAL, false
            )
            adapter = cardAdapter
        }
        lifecycleScope.launch {
            val cardEntities = getCards()
            if (cardEntities == null) {
                binding.noCardsTv.visibility = View.VISIBLE
            } else {
                binding.noCardsTv.visibility = View.GONE
                val cards = mutableListOf<CardModel>()
                val favourite = getFavourites(CurrentUser.userId)
                for (c in cardEntities) {
                    val card = CardModel(
                        c.id,
                        c.title,
                        c.image,
                        c.description,
                        c.instruments,
                        c.rating,
                        c.authorId,
                        c.productionTime
                    )
                    if (c in favourite.cards) {
                        card.isLiked = true
                    }
                    cards.add(card)
                }
                cardAdapter?.setItems(cards)
            }

        }
    }

    private fun initFavouritesRecyclerView() {
        favouritesAdapter = CardAdapter(
            diffCallback = CardDiffUtilItemCallback(),
            glide = Glide.with(this),
            onLikeClicked = ::onLikeClicked,
            onCardClicked = ::onCardClicked,
            onDeleteClicked = ::onDeleteClicked
        )
        binding.favouritesRv.apply {
            layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.HORIZONTAL, false
            )
            adapter = favouritesAdapter
        }
        lifecycleScope.launch {
            val fav = getFavourites(CurrentUser.userId)
            val cards = mutableListOf<CardModel>()
            for (c in fav.cards) {
                val card = CardModel(
                    c.id,
                    c.title,
                    c.image,
                    c.description,
                    c.instruments,
                    c.rating,
                    c.authorId,
                    c.productionTime,
                    true
                )
                cards.add(card)
            }
            if (cards.isEmpty()) {
                binding.favouritesRv.visibility = View.GONE
            } else {
                favouritesAdapter?.setItems(cards)
            }
        }
    }

    private fun onDeleteClicked(index: Int, cardModel: CardModel) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                ServiceLocator.getDbInstance().cardDao.delete(cardModel.id)
            }
        }
        cardAdapter?.removeItem(index)
    }

    private fun onLikeClicked(index: Int, cardModel: CardModel) {
        cardAdapter?.updateItem(index, cardModel)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                CurrentUser.userId?.let { userId ->
                    ServiceLocator.getDbInstance().favouritesDao.apply {
                        val favourite = UserCardCrossReference(
                            userId,
                            cardModel.id!!
                        )
                        if (cardModel.isLiked) {
                            save(favourite)
                        } else {
                            delete(favourite)
                        }
                    }
                }

                println(ServiceLocator.getDbInstance().favouritesDao.get(CurrentUser.userId))
            }
        }
    }

    private fun onCardClicked(cardModel: CardModel) {
        parentFragmentManager.beginTransaction()
            .replace(
                (requireActivity() as MainActivity).fragmentContainerId,
                DetailFragment.newInstance(cardModel.id),
                DetailFragment.DETAIL_FRAGMENT_TAG,
            )
            .addToBackStack(null)
            .commit()
    }

    private suspend fun getCards(): List<CardEntity>? {
        return lifecycleScope.async {
            withContext(Dispatchers.IO) {
                ServiceLocator.getDbInstance().cardDao.getAll()
            }
        }.await()
    }

    private suspend fun getFavourites(userId: Int?): Favourite {
        return lifecycleScope.async {
            withContext(Dispatchers.IO) {
                ServiceLocator.getDbInstance().favouritesDao.get(userId)
            }
        }.await()
    }

    companion object {
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
    }
}
