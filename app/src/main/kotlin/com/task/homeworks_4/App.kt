package com.task.homeworks_4

import android.app.Application
import androidx.room.Room
import com.task.homeworks_4.data.local.room.AppDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
         db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "database-task"
        ).allowMainThreadQueries().build()
    }

    companion object{
        lateinit var  db: AppDataBase
    }
}
