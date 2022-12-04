package com.task.homework_4.data.local.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Dao
import com.task.homework_4.ui.models.Task

@Dao
interface Dao {

@Insert
fun insert(task: Task)

@Delete
fun delete(task: Task)

@Query("SELECT * FROM TASK ORDER BY id DESC")
fun query():List<Task>

@Update
fun update(task: Task)

}