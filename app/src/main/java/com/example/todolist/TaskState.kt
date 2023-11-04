package com.example.todolist

import com.example.todolist.SortType
import com.example.todolist.Task

data class TaskState (
    val taskName:String="",
    val time:String="",
    val tasks: List<Task> = emptyList(),
    val isAddingTask:Boolean=false,
    val sortType: SortType = SortType.NAME

)