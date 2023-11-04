package com.example.todolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.todolist.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Upsert
   suspend fun upsertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM Task ORDER BY time")
    fun sortTaskbyTime(): Flow<List<Task>>

    @Query("SELECT * FROM Task ORDER BY taskName")
    fun sortTaskbyName(): Flow<List<Task>>

}