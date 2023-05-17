package com.firstcode.todolist.Model;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import com.firstcode.todolist.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> { // Passing my View Holder in <>
    // its Located Down

    private Context context;
    private ArrayList<TaskModalClass> TaskList;

    public TaskAdapter(Context context, ArrayList<TaskModalClass> taskList) {
        this.context = context;
        TaskList = taskList;
    }

    //Implemented Method
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout , parent , false);

        return new MyViewHolder(view);
    }

    //Implemented Method
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TaskModalClass model = TaskList.get(position);
        holder.CardViewTitle.setText(model.getTitle());
        holder.CardViewDescription.setText(model.getDescription());
        holder.MyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creating new Dialogue to Update shit
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.update_task);

                // Finding the Id of the update shit
                AppCompatEditText Title = v.findViewById(R.id.UpdateTitle);
                AppCompatEditText description = v.findViewById(R.id.UpdateDescription);
                AppCompatButton Update = v.findViewById(R.id.UpdateButton);
                AppCompatTextView heading = v.findViewById(R.id.UpdateHeading);

                Title.setText((TaskList.get(holder.getAdapterPosition())).getTitle());
                description.setText(TaskList.get(holder.getAdapterPosition()).getDescription());

                Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String title = null, SampleDescription = null;

                        // UPDATING NEW TASKS
                        if(!Title.getText().toString().equals("")) {
                            title = Title.getText().toString();
                        }
                        if(!description.getText().toString().equals("")){
                            SampleDescription = description.getText().toString();
                        }

                        // What Do i need to Update
                        TaskList.set(holder.getAdapterPosition(), new TaskModalClass(title , SampleDescription));
                        // we need to let the recycler View that v Changed Something
                        notifyItemChanged(holder.getAdapterPosition());

                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
    }

    //Implemented Method
    @Override
    public int getItemCount() {
        return TaskList.size();
    }

    // View Holder

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView CardViewTitle;
        private TextView CardViewDescription;

        private RelativeLayout MyCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            CardViewTitle = itemView.findViewById(R.id.cardViewTitle);
            CardViewDescription = itemView.findViewById(R.id.CardViewDescription);
            MyCard = itemView.findViewById(R.id.MyCard);
        }

    }

}
