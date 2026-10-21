package id.ac.polbeng.gentaindianaluthfi.projectuts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.polbeng.gentaindianaluthfi.projectuts.databinding.FragmentProductListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())

        binding.swipeRefresh.setOnRefreshListener {
            loadProducts()
        }

        loadProducts()
    }

    private fun loadProducts() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE

        ApiClient.instance.getProducts(limit = 25).enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (!isAdded) return
                binding.progressBar.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false

                if (response.isSuccessful) {
                    val products = response.body()?.products.orEmpty()
                    if (products.isNotEmpty()) {
                        val adapter = ProductAdapter(products) { product ->
                            val intent = Intent(requireContext(), DetailActivity::class.java)
                            intent.putExtra(DetailActivity.EXTRA_PRODUCT, product)
                            startActivity(intent)
                        }
                        binding.rvProducts.adapter = adapter
                    } else {
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = "Tidak ada produk ditemukan"
                    }
                } else {
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = "Gagal memuat produk (${response.code()})"
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                if (!isAdded) return
                binding.progressBar.visibility = View.GONE
                binding.swipeRefresh.isRefreshing = false
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = "Error koneksi: ${t.localizedMessage}"
                Toast.makeText(requireContext(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
