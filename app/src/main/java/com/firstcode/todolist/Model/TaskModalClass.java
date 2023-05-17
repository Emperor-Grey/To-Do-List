package com.firstcode.todolist.Model;

public class TaskModalClass {

    private String Title;
    private  String Description;


    public TaskModalClass(String title, String description) {
        Title = title;
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
