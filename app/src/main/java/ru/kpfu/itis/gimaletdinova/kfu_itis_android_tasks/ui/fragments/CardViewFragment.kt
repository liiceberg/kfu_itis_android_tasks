package ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.R
import ru.kpfu.itis.gimaletdinova.kfu_itis_android_tasks.databinding.FragmentCardViewBinding

class CardViewFragment : Fragment(R.layout.fragment_card_view) {
    private var binding: FragmentCardViewBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardViewBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        (view.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
        val title = arguments?.getString(TITLE_KEY)
        val description = arguments?.getString(DESCRIPTION_KEY)
        val image = requireArguments().getInt(IMAGE_KEY)
        val transition = arguments?.getString(TRANSITION_KEY)

        binding?.run {
            titleTv.text = title
            descriptionTv.text = description
            imageIv.setImageResource(image)
            imageIv.transitionName = transition
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {

        private const val TITLE_KEY = "TITLE_KEY"
        private const val IMAGE_KEY = "IMAGE_KEY"
        private const val DESCRIPTION_KEY = "DESCRIPTION_KEY"
        private const val TRANSITION_KEY = "TRANSITION_KEY"
        fun newInstance(title: String, image: Int, description: String, transition: String) =
            CardViewFragment().apply {
                arguments = bundleOf(
                    TITLE_KEY to title,
                    IMAGE_KEY to image,
                    DESCRIPTION_KEY to description,
                    TRANSITION_KEY to transition
                )
            }
    }

}