package com.pm.runnerz.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pm.runnerz.data.entities.Running

@Dao
interface RunningDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addRunning(running: Running)

    @Update
    fun updateRunning(running: Running)

    @Delete
    fun deleteRunning(running: Running)

    @Query("SELECT * FROM corrida ORDER BY id ASC")
    fun readAllRunnings(): LiveData<List<Running>>
}