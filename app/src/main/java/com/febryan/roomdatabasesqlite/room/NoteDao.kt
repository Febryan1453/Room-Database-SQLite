package com.febryan.roomdatabasesqlite.room

import androidx.room.*

@Dao
interface NoteDao {  //DAO = Data Akses Objek

    // Kita pake suspend karena kita menggunakan corutines

        @Insert
        suspend fun addNote(note: Note)

        @Query("SELECT * FROM note ORDER BY id DESC")
        suspend fun getNotes() : List<Note>

        @Query("SELECT * FROM note WHERE id=:note_id")
        suspend fun getNote(note_id: Int) : List<Note>

        @Update
        suspend fun updateNote(note: Note)

        @Delete
        suspend fun deleteNote(note: Note)
}