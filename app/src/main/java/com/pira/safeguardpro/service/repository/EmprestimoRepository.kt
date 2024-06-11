package com.pira.safeguardpro.service.repository
import android.content.Context
import com.pira.safeguardpro.service.model.Emprestimo
import com.pira.safeguardpro.service.repository.remote.EmprestimoService
import com.pira.safeguardpro.service.repository.remote.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class EmprestimoRepository(context: Context) {

    private val mRemote = RetrofitClient.createService(EmprestimoService::class.java)

    private val emprestimoEmpty = Emprestimo(0, 0, 0, "", "")

    suspend fun getEmprestimos(): List<Emprestimo> {
        return mRemote.getEmprestimos()
    }

    suspend fun insertEmprestimo(emprestimo: Emprestimo): Emprestimo {
        return mRemote.createEmprestimo(
            nome_epi = emprestimo.nome_epi.toRequestBody("text/plain".toMediaTypeOrNull()),
            codigo_funcionario = emprestimo.codigo_funcionario.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull()),
            numero_ca = emprestimo.numero_ca.toString()
                .toRequestBody("text/plain".toMediaTypeOrNull()),
        ).body() ?: emprestimoEmpty
    }

    suspend fun getEmprestimo(id: Int): Emprestimo {
        val response = mRemote.getEmprestimoById(id)
        return if (response.isSuccessful) {
            response.body()?.first() ?: emprestimoEmpty
        } else {
            emprestimoEmpty
        }
    }

    suspend fun updateEmprestimo(emprestimo: Emprestimo): Emprestimo {
        return mRemote.updateEmprestimo(
            nome_epi = emprestimo.nome_epi.toRequestBody("text/plain".toMediaTypeOrNull()),
            codigo_funcionario = emprestimo.codigo_funcionario.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            numero_ca = emprestimo.numero_ca.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
            id = emprestimo.id
        ).body() ?: emprestimoEmpty
    }
}