package com.task.homework_4

import android.app.Application
import android.content.Context

class App : Application() {
    fun generateSharedPreferences() =
        this.getSharedPreferences("homework4.preferences", Context.MODE_PRIVATE)
}