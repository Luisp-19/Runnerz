package com.pm.runnerz.data.repository

import androidx.lifecycle.LiveData
import com.pm.runnerz.data.dao.RunningDao
import com.pm.runnerz.data.entities.Produto

class RunningRepository(private val runningDao: RunningDao) {
    val readAllProdutos: LiveData<List<Produto>> = runningDao.readAllProdutos()

    suspend fun addProduto(produto: Produto) {
        runningDao.addProduto(produto)
    }

    suspend fun updateProduto(produto: Produto) {
        runningDao.updateProduto(produto)
    }

    suspend fun deleteProduto(produto: Produto) {
        runningDao.deleteProduto(produto)
    }
}