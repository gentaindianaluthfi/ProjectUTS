package id.ac.polbeng.gentaindianaluthfi.projectuts

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    fun getProducts(
        @Query("limit") limit: Int = 25
    ): Call<ProductResponse>
}
