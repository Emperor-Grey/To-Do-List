package com.firstcode.todolist.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = NotesEntity.class ,version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    //Linking With the Dao To the DataBase
    public abstract NoteDao noteDao();

}
