package com.example.todolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.TaskEvent
import com.example.todolist.TaskState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactDialog(
    state: TaskState,
    onEvent: (TaskEvent)->Unit,
    modifier: Modifier=Modifier
){
    AlertDialog(
        modifier=modifier,
        onDismissRequest = {
                           onEvent(TaskEvent.HideDialog)
        }, title = {
        Text(text = "Add Contact")
    },
        text={
            Column(verticalArrangement = Arrangement.spacedBy(8.dp))
            {
               TextField(
                   value=state.taskName,
                   onValueChange = {
                       onEvent(TaskEvent.settaskName(it))
                   },
                   placeholder = {
                       Text(text = "First Name")
                   }
               )
                TextField(
                    value=state.time,
                    onValueChange = {
                        onEvent(TaskEvent.settime(it))
                    },
                    placeholder = {
                        Text(text = "Last Name")
                    }
                )

            }
        },
        confirmButton = {

                        Button(onClick = { onEvent(TaskEvent.SaveTask) }) {
                            Text("Save")
                        }


        },





    )
}