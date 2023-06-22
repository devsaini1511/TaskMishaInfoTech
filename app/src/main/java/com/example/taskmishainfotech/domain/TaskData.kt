package com.example.taskmishainfotech.domain

data class TaskData(
    var taskId: String,
    var title: String,
    var description: String,
    var completed: String,
    var imageUrl: String,
    var timestamp: Long

){
    constructor() : this("","","","","",0)
}