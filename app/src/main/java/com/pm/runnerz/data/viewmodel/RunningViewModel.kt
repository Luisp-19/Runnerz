package com.pm.runnerz.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.pm.runnerz.data.database.RunningDatabase
import com.pm.runnerz.data.entities.Running
import com.pm.runnerz.data.repository.RunningRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RunningViewModel(application: Application) : AndroidViewModel(application) {
    val readAllRunnings: LiveData<List<Running>>
    private val repository: RunningRepository

    init {
        val runningDao = RunningDatabase.getDatabase(application).runningDao()
        repository = RunningRepository(runningDao)
        readAllRunnings = repository.readAllRunnings
    }

    fun addRunning(running: Running) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRunning(running)
        }
    }

    fun updateRunning(running: Running) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRunning(running)
        }
    }

    fun deleteRunning(running: Running) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRunning(running)
        }
    }
}