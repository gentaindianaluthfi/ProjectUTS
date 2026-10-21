package id.ac.polbeng.gentaindianaluthfi.projectuts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.ac.polbeng.gentaindianaluthfi.projectuts.databinding.DialogReviewsBinding

class ReviewsDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogReviewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogReviewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("DEPRECATION")
        val reviews = arguments?.getSerializable(ARG_REVIEWS) as? ArrayList<Review> ?: arrayListOf()

        binding.rvDialogReviews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDialogReviews.adapter = ReviewAdapter(reviews)

        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_REVIEWS = "ARG_REVIEWS"

        fun newInstance(reviews: List<Review>): ReviewsDialogFragment {
            val fragment = ReviewsDialogFragment()
            val args = Bundle()
            args.putSerializable(ARG_REVIEWS, ArrayList(reviews))
            fragment.arguments = args
            return fragment
        }
    }
}
