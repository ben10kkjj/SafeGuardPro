package com.pira.safeguardpro.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pira.safeguardpro.service.model.Funcionario
import com.pira.safeguardpro.service.repository.FuncionarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FuncionarioViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FuncionarioRepository(application)

    private val mFuncionarioList = MutableLiveData<List<Funcionario>>()
    val funcionarioList: LiveData<List<Funcionario>> = mFuncionarioList

    private val mFuncionario = MutableLiveData<Funcionario>()
    val funcionario: LiveData<Funcionario> = mFuncionario

    private val mErro = MutableLiveData<String>()
    val erro: LiveData<String> = mErro

    private val mCreatedFuncionario = MutableLiveData<Funcionario>()
    val createdFuncionario: LiveData<Funcionario> = mCreatedFuncionario

    private val mUpdatedFuncionario = MutableLiveData<Funcionario>()
    val updatedFuncionario: LiveData<Funcionario> = mUpdatedFuncionario

    private val mDeletedFuncionario = MutableLiveData<Boolean>()
    val deletedFuncionario: LiveData<Boolean> = mDeletedFuncionario

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mFuncionarioList.postValue(repository.getFuncionarios())
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun loadFuncionario(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mFuncionario.postValue(repository.getFuncionario(id))
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun insert(funcionario: Funcionario) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val createdFuncionario = repository.insertFuncionario(funcionario)
                mCreatedFuncionario.postValue(createdFuncionario)
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mDeletedFuncionario.postValue(repository.deleteFuncionario(id))
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }

    fun update(funcionario: Funcionario) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedFuncionario = repository.updateFuncionario(funcionario)
                mUpdatedFuncionario.postValue(updatedFuncionario)
            } catch (e: Exception) {
                mErro.postValue(e.message)
            }
        }
    }
}