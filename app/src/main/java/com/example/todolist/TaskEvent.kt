package com.example.todolist

sealed interface TaskEvent{
    object SaveTask: TaskEvent
    data class settaskName(val taskName:String): TaskEvent
    data class settime(val time:String): TaskEvent
    data class DeleteTask(val task: Task): TaskEvent
    object ShowDialog: TaskEvent
    object HideDialog: TaskEvent
    data class SortTask(val sortType: SortType): TaskEvent
}