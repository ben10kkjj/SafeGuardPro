package com.pira.safeguardpro.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pira.safeguardpro.service.model.Emprestimo
import com.pira.safeguardpro.service.repository.EmprestimoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmprestimoViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = EmprestimoRepository(application)

    private val mEmprestimoList = MutableLiveData<List<Emprestimo>>()
    val emprestimoList: LiveData<List<Emprestimo>> = mEmprestimoList

    private val mEmprestimo = MutableLiveData<Emprestimo>()
    val emprestimo: LiveData<Emprestimo> = mEmprestimo

    private val mErro = MutableLiveData<String>()
    val erro: LiveData<String> = mErro

    private val mCreatedEmprestimo = MutableLiveData<Emprestimo>()
    val createdemprestimo: LiveData<Emprestimo> = mCreatedEmprestimo

    private val mUpdatedEmprestimo = MutableLiveData<Emprestimo>()
    val updatedEmprestimo: LiveData<Emprestimo> = mUpdatedEmprestimo

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mEmprestimoList.postValue(repository.getEmprestimos())
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun loadEmprestimo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mEmprestimo.postValue(repository.getEmprestimo(id))
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun insert(emprestimo: Emprestimo) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val createdEmprestimo = repository.insertEmprestimo(emprestimo)
                mCreatedEmprestimo.postValue(createdEmprestimo)
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun update(emprestimo: Emprestimo) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedEmprestimo = repository.updateEmprestimo(emprestimo)
                mUpdatedEmprestimo.postValue(updatedEmprestimo)
            } catch (e: Exception){
                mErro.postValue(e.message)
            }
        }
    }
}