package com.pm.runnerz.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pm.runnerz.data.entities.Produto

@Dao
interface RunningDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addProduto(produto: Produto)

    @Update
    fun updateProduto(produto: Produto)

    @Query("SELECT * FROM produto ORDER BY id DESC")
    fun readAllProdutos(): LiveData<List<Produto>>

    @Delete
    fun deleteProduto(produto: Produto)
}