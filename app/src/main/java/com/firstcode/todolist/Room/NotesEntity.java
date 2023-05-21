package com.firstcode.todolist.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")
public class NotesEntity {

    @ColumnInfo(name = "Id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "TitleName")
    private final String titleName;

    @ColumnInfo(name = "Description")
    private final String description;

    public NotesEntity(String titleName, String description) {
        this.titleName = titleName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleName() {
        return titleName;
    }

    public String getDescription() {
        return description;
    }

}