package id.ac.polbeng.gentaindianaluthfi.projectuts

import java.io.Serializable

data class ProductResponse(
    val products: List<Product>
) : Serializable

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val availabilityStatus: String?,
    val tags: List<String>?,
    val dimensions: Dimensions?,
    val reviews: List<Review>?,
    val rating: Double,
    val images: List<String>?,
    val thumbnail: String?
) : Serializable

data class Dimensions(
    val width: Double,
    val height: Double,
    val depth: Double
) : Serializable

data class Review(
    val rating: Int,
    val comment: String,
    val date: String,
    val reviewerName: String,
    val reviewerEmail: String
) : Serializable
