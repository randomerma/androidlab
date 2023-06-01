package com.example.androidlab

import android.app.Application
import com.example.androidlab.ui.notes.NoteDatabase
import com.example.androidlab.ui.notes.NoteRepository

class LabApplication : Application() {

    private val database by lazy { NoteDatabase.getDatabase(this) }

    val noteRepository by lazy { NoteRepository(database.noteDao()) }
}