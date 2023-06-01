package com.example.androidlab.ui.notes

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: Flow<List<NoteEntity>> = noteDao.getAllNotes()

    @WorkerThread
    suspend fun insert(note: NoteEntity) = noteDao.insertNote(note)

    @WorkerThread
    suspend fun update(note: NoteEntity) = noteDao.updateNote(note)

    @WorkerThread
    suspend fun delete(id: Long) = noteDao.deleteNoteById(id)
}