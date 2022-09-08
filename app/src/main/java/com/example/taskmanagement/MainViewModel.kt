package com.example.taskmanagement

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class Task(
    var name: String,
    var desc: String,
    var id: Int,
) {
    var complete by mutableStateOf<Boolean>(false)

    fun completeTask() {
        complete = true;
    }
}

class MainViewModel: ViewModel() {

    var popup by mutableStateOf<Boolean>(false)

    fun openPopup() {
        popup = true
    }
    fun closePopup() {
        popup = false
    }

    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> = _tasks

    var numberOfTasks by mutableStateOf<Float>(0f)
    var numberOfCompleteTasks by mutableStateOf<Float>(0f)
    var progress by mutableStateOf<Float>(0f)
    var progressPercent by mutableStateOf<Int>(0)

    fun updateProgress() {
        progress = numberOfCompleteTasks / numberOfTasks
        progressPercent = (progress * 100).toInt()
    }

    fun createTask(name: String, desc: String) {
        _tasks.add(Task(name, desc, _tasks.lastIndex + 1))
        popup = false
        numberOfTasks++;
        updateProgress()
    }

    fun completeTask(id: Int) {
        _tasks.forEach {
            if (it.id == id) {
                it.completeTask()
                numberOfCompleteTasks++
                updateProgress()
                return
            }
        }
    }
}