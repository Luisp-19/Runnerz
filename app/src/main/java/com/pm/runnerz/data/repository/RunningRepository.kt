package com.pm.runnerz.data.repository

import androidx.lifecycle.LiveData
import com.pm.runnerz.data.dao.RunningDao
import com.pm.runnerz.data.entities.Running

class RunningRepository(private val runningDao: RunningDao) {
    val readAllRunnings: LiveData<List<Running>> = runningDao.readAllRunnings()

    suspend fun addRunning(running: Running) {
        runningDao.addRunning(running)
    }

    suspend fun updateRunning(running: Running) {
        runningDao.updateRunning(running)
    }

    suspend fun deleteRunning(running: Running) {
        runningDao.deleteRunning(running)
    }
}