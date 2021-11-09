package com.pm.runnerz.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.pm.runnerz.data.database.RunningDatabase
import com.pm.runnerz.data.entities.Produto
import com.pm.runnerz.data.repository.RunningRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RunningViewModel(application: Application) : AndroidViewModel(application) {
    val readAllProdutos: LiveData<List<Produto>>
    private val repository: RunningRepository

    init {
        val runningDao = RunningDatabase.getDatabase(application).runningDao()
        repository = RunningRepository(runningDao)
        readAllProdutos = repository.readAllProdutos

    }

    fun addProduto(produto: Produto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProduto(produto)
        }
    }

    fun updateProduto(produto: Produto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProduto(produto)
        }
    }

    fun deleteProduto(produto: Produto) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduto(produto)
        }
    }
}