package com.example.todolist

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolist.Task
import com.example.todolist.TaskDao

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase:RoomDatabase() {


    abstract val dao: TaskDao
}