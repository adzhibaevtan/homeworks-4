package com.task.homework_4

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.task.homework_4.data.room.AppDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
         db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "database-task"
        ).allowMainThreadQueries().build()
    }

    companion object{
        lateinit var  db:AppDataBase
    }
}
