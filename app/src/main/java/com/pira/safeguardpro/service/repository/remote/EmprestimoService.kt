package com.pira.safeguardpro.service.repository.remote

import com.pira.safeguardpro.service.model.Emprestimo
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface EmprestimoService {
    @GET("get_emprestimo")
    suspend fun getEmprestimos(): List<Emprestimo>

    @GET("get_emprestimo/{emprestimo_id}")
    suspend fun getEmprestimoById(@Path("emprestimo_id") id: Int): Response<List<Emprestimo>>

    @POST("add_emprestimo")
    suspend fun createEmprestimo(
        @Part("codigo_funcionario") codigo_funcionario: RequestBody,
        @Part("numero_ca") numero_ca: RequestBody,
        @Part("nome_epi") nome_epi: RequestBody,
    ): Response<Emprestimo>

    @Multipart
    @PUT("update_emprestimo/{emprestimo_id}")
    suspend fun updateEmprestimo(
        @Path("emprestimo_id")id: Int,
        @Part("codigo_funcionario") codigo_funcionario: RequestBody,
        @Part("numero_ca") numero_ca: RequestBody,
        @Part("nome_epi") nome_epi: RequestBody,
    ): Response<Emprestimo>
}