package com.pira.safeguardpro.service.repository

import android.content.Context
import com.pira.safeguardpro.service.model.Epi
import com.pira.safeguardpro.service.repository.remote.EpiService
import com.pira.safeguardpro.service.repository.remote.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class EpiRepository(context: Context) {

    private val mRemote = RetrofitClient.createService(EpiService::class.java)

    private val epiEmpty = Epi(0, "", 0, 0, "")

    suspend fun getEpis(): List<Epi> {
        return mRemote.getEpis()
    }

    suspend fun insertEpi(epi: Epi): Epi {
        return mRemote.createEpi(
            nome_equipamento = epi.nome_equipamento.toRequestBody("text/plain".toMediaTypeOrNull()),
            data_vencimento = epi.data_vencimento.toRequestBody("text/plain".toMediaTypeOrNull()),
            tempo_uso = epi.tempo_uso.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            numero_ca = epi.numero_ca.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
        ).body() ?: epiEmpty
    }

    suspend fun getEpi(id: Int): Epi {
        val response = mRemote.getEpiById(id)
        return if (response.isSuccessful) {
            response.body()?.first() ?: epiEmpty
        } else {
            epiEmpty
        }
    }

    suspend fun getEpiByCa(ca: Int): Epi {
        val response = mRemote.getEpiByCa(ca)
        return if (response.isSuccessful) {
            response.body()?.first() ?: epiEmpty
        } else {
            epiEmpty
        }
    }

    suspend fun updateEpi(epi: Epi): Epi {
        return mRemote.updateEpi(
            nome_equipamento = epi.nome_equipamento.toRequestBody("text/plain".toMediaTypeOrNull()),
            data_vencimento = epi.data_vencimento.toRequestBody("text/plain".toMediaTypeOrNull()),
            tempo_uso = epi.tempo_uso.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            numero_ca = epi.numero_ca.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            id = epi.id
        ).body() ?: epiEmpty
    }

    suspend fun deleteEpi(id: Int): Boolean {
        return mRemote.deleteEpiById(id).isSuccessful
    }
}