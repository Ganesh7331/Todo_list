package com.example.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewStateModel(private val dao: TaskDao):ViewModel() {

    private val _state= MutableStateFlow(TaskState())
    private val _sortType= MutableStateFlow(SortType.NAME)

    private val _tasks=_sortType.flatMapLatest {
        sortType->
        when(sortType){
              SortType.TIME->dao.sortTaskbyTime()
            SortType.NAME -> dao.sortTaskbyName()
        }

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    val state=combine(_state,_sortType,_tasks){state,sortType,Tasks->
        state.copy(
            sortType=sortType,
            tasks = Tasks

        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), TaskState())


    fun onEvent(event: TaskEvent){
        when(event){
            is TaskEvent.DeleteTask -> {

                viewModelScope.launch {
                    dao.deleteTask(event.task)
                }
            }
            TaskEvent.HideDialog -> {

                _state.update { it.copy(
                    isAddingTask = false
                ) }
            }
            TaskEvent.SaveTask -> {
                 val taskName=state.value.taskName
                val time=state.value.time

                if(taskName.isBlank()  || time.isBlank()){
                    return
                }
                val task= Task(taskName,time)
                viewModelScope.launch {
                    dao.upsertTask(task)
                }
                _state.update {
                    it.copy(
                        taskName="",
                        time="",
                        isAddingTask = false
                    )
                }



            }
            TaskEvent.ShowDialog -> {

                _state.update{
                    it.copy(
                        isAddingTask = true
                    )
                }
            }
            is TaskEvent.SortTask -> {

                _sortType.value=event.sortType
            }
            is TaskEvent.settaskName -> {
                _state.update {
                    it.copy(
                        taskName = event.taskName
                    )
                }
            }
            is TaskEvent.settime -> {

                _state.update {
                    it.copy(
                        time=event.time
                    )
                }
            }
        }
    }
}