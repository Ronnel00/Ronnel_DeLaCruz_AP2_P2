package edu.ucne.ronnel_delacruz_ap2_p2.data.remote

import edu.ucne.ronnel_delacruz_ap2_p2.data.remote.dto.GastoRequest
import edu.ucne.ronnel_delacruz_ap2_p2.data.remote.dto.GastoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GastoApi {

    @GET("api/Gastos")
    suspend fun getGastos(): Response<List<GastoResponse>>

    @GET("api/Gastos/{id}")
    suspend fun getGastoById(
        @Path("id") id: Int
    ): Response<GastoResponse>

    @POST("api/Gastos")
    suspend fun createGasto(
        @Body gasto: GastoRequest
    ): Response<GastoResponse>

    @PUT("api/Gastos/{id}")
    suspend fun updateGasto(
        @Path("id") id: Int,
        @Body gasto: GastoRequest
    ): Response<Unit>
}