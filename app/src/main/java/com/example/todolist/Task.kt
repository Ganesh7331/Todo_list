package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task (
    val taskName:String,
    val time:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int=0
)