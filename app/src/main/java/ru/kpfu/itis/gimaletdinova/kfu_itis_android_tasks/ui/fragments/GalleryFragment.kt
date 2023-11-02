package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.Fade
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.Card
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter.GalleryAdapter
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter.decorations.SimpleHorizontalMarginDecorator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter.decorations.SimpleVerticalMarginDecorator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentGalleryBinding
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.holder.CardViewHolder
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.DataRepository
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.util.getValueInPx

class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private var binding: FragmentGalleryBinding? = null
    private var galleryAdapter: GalleryAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val number = requireArguments().getInt(NUMBER_KEY)

        initRecyclerView(number)

        if (number <= 12) {
            addSwipeToDelete()
        }


        postponeEnterTransition()
        (view.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }

        binding?.run {
            addBtn.setOnClickListener {
                BottomSheetFragment(galleryAdapter).show(
                    childFragmentManager,
                    BottomSheetFragment.FRAGMENT_TAG
                )
                manageWarning()
            }
        }
    }

    private fun addSwipeToDelete() {
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.Callback() {

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return when (viewHolder) {
                    is CardViewHolder -> makeMovementFlags(0, ItemTouchHelper.LEFT)
                    else -> 0
                }
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onDelete(viewHolder.adapterPosition)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding?.galleryRv)
    }

    private fun initRecyclerView(number: Int) {
        binding?.galleryRv?.apply {
            val currentLayoutManager = if (number <= 12) {
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            } else {
                StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            }

            val marginValue = 8.getValueInPx(resources.displayMetrics)
            addItemDecoration(SimpleHorizontalMarginDecorator(marginValue))
            addItemDecoration(SimpleVerticalMarginDecorator(marginValue))

            layoutManager = currentLayoutManager

            galleryAdapter = GalleryAdapter(
                currentLayoutManager,
                ::onLikeClicked,
                ::onRootClicked,
                ::onDeleteClicked,
                number > 12
            )
            adapter = galleryAdapter

            if (DataRepository.getItemsList().isEmpty()) {
                DataRepository.initDataList(number)
            }
            galleryAdapter?.setItems(DataRepository.getItemsList())
            manageWarning()
        }
    }

    private fun manageWarning() {
        binding?.emptyScreenTv?.visibility = if (DataRepository.getItemsList().isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun onLikeClicked(position: Int, card: Card) {
        DataRepository.setItem(position, card)
        galleryAdapter?.updateItem(position, card)
    }

    private fun onRootClicked(card: Card, view: View) {

        val fragment = CardViewFragment.newInstance(card.title, card.image, card.description, "card-${card.id}")
        fragment.sharedElementEnterTransition = prepareTransition()
        fragment.sharedElementReturnTransition = prepareTransition()
        fragment.enterTransition = Slide()
        parentFragmentManager.beginTransaction()
            .addSharedElement(view, view.transitionName)
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun prepareTransition(): Transition {
        val set = TransitionSet()
        set.ordering = TransitionSet.ORDERING_TOGETHER
        set.addTransition(ChangeBounds())
        set.addTransition(ChangeTransform())
        return set
    }

    private fun onDeleteClicked(position: Int, card: Card) {
        onDelete(position)
        binding?.run {
            Snackbar
                .make(root, getString(R.string.cancel), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.yes)) {
                    DataRepository.setItem(position, card)
                    galleryAdapter?.insertItem(position, card)
                }
                .show()
        }
    }

    private fun onDelete(position: Int) {
        galleryAdapter?.deleteItem(position)
        DataRepository.deleteItem(position)
        manageWarning()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {

        private const val NUMBER_KEY = "NUMBER_KEY"
        fun newInstance(number: Int) = GalleryFragment().apply {
            arguments = bundleOf(NUMBER_KEY to number)
        }

    }
}