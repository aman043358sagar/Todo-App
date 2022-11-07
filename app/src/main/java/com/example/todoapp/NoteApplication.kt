package com.example.todoapp

import android.app.Application
import com.example.todoapp.room.database.NoteDatabase
import com.example.todoapp.room.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication : Application() {

    val database by lazy { NoteDatabase.getDatabase(this) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}