package com.pm.runnerz.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pm.runnerz.data.dao.RunningDao
import com.pm.runnerz.data.entities.Produto

@Database(entities = [Produto::class], version = 1, exportSchema = false)
abstract class RunningDatabase : RoomDatabase() {

    abstract fun runningDao(): RunningDao

    companion object {
        @Volatile
        private var INSTANCE: RunningDatabase? = null

        fun getDatabase(context: Context): RunningDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RunningDatabase::class.java,
                    "running_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}