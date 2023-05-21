package com.firstcode.todolist.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void Insert(NotesEntity notes);

    @Update
    void Update(NotesEntity notes);

    @Delete
    void Delete(NotesEntity notes);

    @Query("Select * From Notes")
    List<NotesEntity> getAllNotes();

    @Query("Select * From Notes where Id ==:NoteId")
    List<NotesEntity> getNote(int NoteId);
}
