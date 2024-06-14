package com.pira.safeguardpro.service.repository.remote

import com.pira.safeguardpro.service.model.Emprestimo
import com.pira.safeguardpro.service.model.Epi
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface EpiService {
    @GET("get_epi")
    suspend fun getEpis(): List<Epi>

    @GET("get_epi/{epi_id}")
    suspend fun getEpiById(@Path("epi_id") id: Int): Response<List<Epi>>

    @GET("get_epi/{epi_ca}")
    suspend fun getEpiByCa(@Path("epi_ca") ca: Int): Response<List<Epi>>

    @Multipart
    @POST("add_epi")
    suspend fun createEpi(
        @Part("nome_equipamento") nome_equipamento: RequestBody,
        @Part("numero_ca") numero_ca: RequestBody,
        @Part("tempo_uso") tempo_uso: RequestBody,
        @Part("data_vencimento") data_vencimento: RequestBody,
    ): Response<Epi>

    @Multipart
    @PUT("update_epi/{epi_id}")
    suspend fun updateEpi(
        @Path("epi_id") id: Int,
        @Part("nome_equipamento") nome_equipamento: RequestBody,
        @Part("numero_ca") numero_ca: RequestBody,
        @Part("tempo_uso") tempo_uso: RequestBody,
        @Part("data_vencimento") data_vencimento: RequestBody,
    ): Response<Epi>

    @DELETE("delete_epi/{epi_id}")
    suspend fun deleteEpiById(@Path("epi_id")id: Int): Response<Epi>
}