package com.firstcode.todolist;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileConnector {

    private static final String FILENAME = "listInfo.dat";

    public static void writeData(ArrayList<String> item, Context context){

        try {

            FileOutputStream fos = context.openFileOutput(FILENAME,Context.MODE_PRIVATE);
            ObjectOutputStream oas = new ObjectOutputStream(fos);
            oas.writeObject(item);
            oas.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();

        }catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static ArrayList<String> readData(Context context){

        ArrayList<String> itemList = null;
        try {

            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            itemList = (ArrayList<String>) ois.readObject();

        } catch (FileNotFoundException e) {

            itemList = new ArrayList<>(); // don't forget this cuz it has to create if there's nothing to read
            e.printStackTrace();

        } catch (IOException i) {
            i.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return itemList;
    }
}
