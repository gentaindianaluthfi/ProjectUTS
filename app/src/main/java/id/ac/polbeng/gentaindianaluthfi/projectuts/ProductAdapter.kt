package id.ac.polbeng.gentaindianaluthfi.projectuts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.polbeng.gentaindianaluthfi.projectuts.databinding.ItemProductBinding
import java.util.Locale

class ProductAdapter(
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.tvTitle.text = product.title
            binding.tvCategory.text = "Kategori: ${product.category}"
            binding.tvPrice.text = String.format(Locale.US, "$%.2f", product.price)
            binding.tvStock.text = "Stok: ${product.stock}"
            binding.tvRating.text = String.format(Locale.US, "%.2f", product.rating)

            // Load image using Glide from thumbnail
            Glide.with(binding.root.context)
                .load(product.thumbnail)
                .centerCrop()
                .into(binding.ivThumbnail)

            binding.root.setOnClickListener {
                onItemClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int = products.size
}
