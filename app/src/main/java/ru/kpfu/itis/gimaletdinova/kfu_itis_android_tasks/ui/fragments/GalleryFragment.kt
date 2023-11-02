package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.model.Card
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter.GalleryAdapter
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter.decorations.SimpleHorizontalMarginDecorator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.adapter.decorations.SimpleVerticalMarginDecorator
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentGalleryBinding
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
        binding?.run {
            addBtn.setOnClickListener {
                BottomSheetFragment(galleryAdapter).show(childFragmentManager, BottomSheetFragment.FRAGMENT_TAG)
                manageWarning()
            }
        }
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
                ::onRootClicked
            )

            adapter = galleryAdapter
            val dataList = DataRepository.initDataList(number)
            galleryAdapter?.setItems(dataList)
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

    private fun onRootClicked(card: Card) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                CardViewFragment.newInstance(card.title, card.image, card.description))
            .addToBackStack(null)
            .commit()
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