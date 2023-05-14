package com.firstcode.todolist;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText text;
    Button Add;
    ListView listView;
    ArrayList<String> itemList = new ArrayList<>(); //to hold the item's
    ArrayAdapter<String> arrayAdapter;  //To connect the arraylist and this view
    //Now v don't want the data to be deleted when v close the application soo v store/Save them in a different file/Memory


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing
        text = findViewById(R.id.addEdittext);
        Add = findViewById(R.id.Add);
        listView = findViewById(R.id.itemList);

        //using things from the fileConnector
        itemList = FileConnector.readData(this);

        //Sending the data through the adapter
        arrayAdapter = new ArrayAdapter<>(this
                ,android.R.layout.simple_list_item_1,android.R.id.text1
                ,itemList);
        //setting the adapter
        listView.setAdapter(arrayAdapter);

        //adding functionalities to the button
        Add.setOnClickListener(v -> {
            String TextName = text.getText().toString();
            itemList.add(TextName);
            text.setText("");

            //now writing the data
            FileConnector.writeData(itemList,getApplicationContext());
            //gotta notify that its changed dunno y
            arrayAdapter.notifyDataSetChanged();

        });

        //now adding click activities to the each item
        listView.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
            alert.setTitle("Delete");
            alert.setMessage("Do You Want To Delete This Item From The List ?");
            alert.setCancelable(false);
            alert.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            alert.setPositiveButton("Yes", (dialog, which) -> {
                itemList.remove(position);
                //notify as the data is changed again
                arrayAdapter.notifyDataSetChanged();
                FileConnector.writeData(itemList,getApplicationContext());
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
        });
    }
    // Adding Menus

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu , menu);
        return true;
    }
}