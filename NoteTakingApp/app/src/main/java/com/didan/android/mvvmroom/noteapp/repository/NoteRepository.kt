package com.didan.android.mvvmroom.noteapp.repository

import com.didan.android.mvvmroom.noteapp.database.NoteDatabase
import com.didan.android.mvvmroom.noteapp.model.Note

class NoteRepository(private val noteDb: NoteDatabase) {

    suspend fun insertNote(note: Note) = noteDb.getNoteDao().insertNote(note);
    suspend fun updateNote(note: Note) = noteDb.getNoteDao().updateNote(note);
    suspend fun deleteNote(note: Note) = noteDb.getNoteDao().deleteNote(note);
    fun getAllNotes() = noteDb.getNoteDao().getAllNotes();
    fun searchNote(query: String?) = noteDb.getNoteDao().searchNote(query);
}