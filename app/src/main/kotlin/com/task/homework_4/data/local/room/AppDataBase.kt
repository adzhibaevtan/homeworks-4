package com.task.homework_4.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.homework_4.ui.models.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun dao(): Dao
}