package id.ac.polbeng.gentaindianaluthfi.projectuts

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.ac.polbeng.gentaindianaluthfi.projectuts.databinding.ActivityDetailBinding
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Produk"

        @Suppress("DEPRECATION")
        val product = intent.getSerializableExtra(EXTRA_PRODUCT) as? Product

        if (product != null) {
            displayProductDetail(product)
        } else {
            Toast.makeText(this, "Data produk tidak ditemukan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun displayProductDetail(product: Product) {
        binding.tvDetailTitle.text = product.title
        binding.tvDetailId.text = "ID Produk: ${product.id}"
        binding.tvDetailPrice.text = String.format(Locale.US, "$%.2f", product.price)
        binding.tvDetailAvailability.text = product.availabilityStatus ?: "In Stock"
        binding.tvDetailCategory.text = "Kategori: ${product.category}"
        binding.tvDetailStock.text = "Stok: ${product.stock}"
        binding.tvDetailRating.text = String.format(Locale.US, "%.2f", product.rating)
        binding.tvDetailDescription.text = product.description

        // Display tags
        val tagsText = product.tags?.joinToString(separator = " ") { "#$it" } ?: "-"
        binding.tvDetailTags.text = tagsText

        // Display dimensions
        val dim = product.dimensions
        if (dim != null) {
            binding.tvDetailDimensions.text =
                String.format(Locale.US, "%.2f x %.2f x %.2f cm", dim.width, dim.height, dim.depth)
        } else {
            binding.tvDetailDimensions.text = "-"
        }

        // Load image from images list (or fallback to thumbnail) as per requirement
        val imageUrl = product.images?.firstOrNull() ?: product.thumbnail
        Glide.with(this)
            .load(imageUrl)
            .fitCenter()
            .into(binding.ivDetailImage)

        // Display top 1 review
        val reviews = product.reviews.orEmpty()
        if (reviews.isNotEmpty()) {
            val topReview = reviews.first()
            binding.cardTopReview.visibility = View.VISIBLE
            binding.tvReviewerName.text = topReview.reviewerName
            binding.tvReviewerEmail.text = topReview.reviewerEmail
            binding.tvReviewRating.text = topReview.rating.toString()
            binding.tvReviewComment.text = topReview.comment
            binding.tvReviewDate.text = topReview.date.take(10)
        } else {
            binding.cardTopReview.visibility = View.GONE
        }

        // Click listener for "Lihat Semua Review"
        binding.tvLihatSemuaReview.setOnClickListener {
            if (reviews.isNotEmpty()) {
                val dialog = ReviewsDialogFragment.newInstance(reviews)
                dialog.show(supportFragmentManager, "ReviewsDialogFragment")
            } else {
                Toast.makeText(this, "Tidak ada review untuk produk ini", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_PRODUCT = "EXTRA_PRODUCT"
    }
}
