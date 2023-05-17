package com.firstcode.todolist;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firstcode.todolist.Model.TaskAdapter;
import com.firstcode.todolist.Model.TaskModalClass;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView taskHeading;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    BottomSheetDialog dialog;
    ArrayList<TaskModalClass> TaskList;
    TaskAdapter adapter;
    private View decorView; // For Full Screen


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskHeading = findViewById(R.id.Heading);
        recyclerView = findViewById(R.id.RecyclerView);
        fab = findViewById(R.id.fab);
        // Dialogue Section
        dialog = new BottomSheetDialog(this);
        //Inflating the View in this method
        CreateDialog();
        fab.setOnClickListener(v -> dialog.show());
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        //arraylist
        TaskList = new ArrayList<>();
        TaskList.add(new TaskModalClass("Title","This is a dummy text or the task maybe description"));

        // Adapter
        adapter = new TaskAdapter(MainActivity.this ,TaskList);
        recyclerView.setAdapter(adapter);

        //Now Managing the Layout
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        recyclerView.setLayoutManager(layoutManager);



        // for full Screen
        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if(visibility == 0)
                decorView.setSystemUiVisibility(HideSystemBar());
        });

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



    //Creating a Dialogue
    private void CreateDialog() {
        View view = getLayoutInflater().inflate(R.layout.newtask_layout, null,false);

        AppCompatEditText titleName = view.findViewById(R.id.newTaskTitle);
        AppCompatEditText TaskDescription = view.findViewById(R.id.newDescription);
        AppCompatButton AddNewTask = view.findViewById(R.id.AddButton);

        AddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = null, description = null;

                dialog.dismiss();


                // ADDING NEW TASKS
                if(!titleName.getText().toString().equals("")) {
                    title = titleName.getText().toString();
                }
                if(!TaskDescription.getText().toString().equals("")){
                    description = TaskDescription.getText().toString();
                }

                //Accessing from the constructor from the model class to add
                TaskList.add(new TaskModalClass(title , description)); // v need to make the scope available so
                //making them null to Access


                // Now Adapting this
                adapter.notifyItemInserted(TaskList.size()-1);

                //if v have More data then i want the the app to scroll to that position for the user Experience
                recyclerView.scrollToPosition(TaskList.size()-1);   // passing same position see
            }
        });

        //Setting the content View to Dialogue
        dialog.setContentView(view);


    }
}