package com.firstcode.todolist;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.firstcode.todolist.Adapter.NotesAdapter;
import com.firstcode.todolist.Room.NoteDataBase;
import com.firstcode.todolist.Room.NotesEntity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextView taskHeading;
    private RecyclerView recyclerView;
    FloatingActionButton fab;
    BottomSheetDialog dialog;
    private NotesAdapter notesAdapter;
    private NoteDataBase nbd;
    private ArrayList<NotesEntity> TaskLists;
    private View decorView; //for full Screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for full Screen
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if(visibility == 0)
                decorView.setSystemUiVisibility(HideSystemBar());
        });

        taskHeading = findViewById(R.id.Heading);
        recyclerView = findViewById(R.id.RecyclerView);
        fab = findViewById(R.id.fab);

        dialog = new BottomSheetDialog(this);
        //Inflating the View in this method
        CreateDialog();
        fab.setOnClickListener(v -> dialog.show());
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        //Recycler View
        recyclerView = findViewById(R.id.RecyclerView);
        //Database
        nbd = Room.databaseBuilder(getApplicationContext(), NoteDataBase.class, "My DataBase")
                .allowMainThreadQueries()
                .build();

        //TaskLists
        TaskLists = new ArrayList<>();
        TaskLists.addAll(nbd.noteDao().getAllNotes());

        //Adapter
        notesAdapter = new NotesAdapter(this , TaskLists , MainActivity.this , nbd);

        // Layout Manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notesAdapter);
        recyclerView.refreshDrawableState();

    }

    //for fullScreen
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decorView.setSystemUiVisibility(HideSystemBar());
        }
    }
    //since i can't write everytime let me just put that shit in a method
    private int HideSystemBar(){

        return    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
    }
    private void CreateDialog() {

        View view = getLayoutInflater().inflate(R.layout.newtask_layout, null,false);

        AppCompatEditText titleName = view.findViewById(R.id.newTaskTitle);
        AppCompatEditText TaskDescription = view.findViewById(R.id.newDescription);
        AppCompatButton AddNewTask = view.findViewById(R.id.AddButton);

        AddNewTask.setOnClickListener(v -> {

            String title = null, description = null;

            // ADDING NEW TASKS
            if(!Objects.requireNonNull(titleName.getText()).toString().equals("")) {
                title = titleName.getText().toString();
            }
            if(!Objects.requireNonNull(TaskDescription.getText()).toString().equals("")){
                description = TaskDescription.getText().toString();
            }

            TaskLists.add(new NotesEntity(title , description));
            nbd.noteDao().Insert(new NotesEntity(title , description));

            notesAdapter.notifyItemInserted(TaskLists.size()-1);

            recyclerView.scrollToPosition(TaskLists.size()-1);

            dialog.dismiss();
        });

        dialog.setContentView(view);
    }
}