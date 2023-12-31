package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room

class MainActivity : ComponentActivity() {


    private val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            TaskDatabase::class.java,
            "task.db"
        ).build()
    }

    private val viewModel by viewModels<TaskViewStateModel>(
        factoryProducer = {
            object: ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TaskViewStateModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.state.collectAsState()
            TaskScreen(state =state , onEvent = viewModel::onEvent)
        }
    }
}

