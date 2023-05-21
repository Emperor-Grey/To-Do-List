package com.firstcode.todolist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.firstcode.todolist.MainActivity;
import com.firstcode.todolist.R;
import com.firstcode.todolist.Room.NoteDataBase;
import com.firstcode.todolist.Room.NotesEntity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Objects;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<NotesEntity> TaskLists;
    private final MainActivity mainActivity;
    private final NoteDataBase nbd;

    public NotesAdapter(Context context, ArrayList<NotesEntity> TaskLists, MainActivity mainActivity, NoteDataBase nbd) {
        this.context = context;
        this.TaskLists = TaskLists;
        this.mainActivity = mainActivity;
        this.nbd = nbd;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout , parent , false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final NotesEntity notes = TaskLists.get(position);
        holder.CardViewTitle.setText(notes.getTitleName());
        holder.CardViewDescription.setText(notes.getDescription());

        holder.itemView.setOnClickListener(v -> {


            // Creating new Dialogue to Update shit
            BottomSheetDialog dialog = new BottomSheetDialog(context);
            dialog.setContentView(R.layout.update_task);

            // Finding the Id of the update shit
            AppCompatEditText Title = dialog.findViewById(R.id.UpdateTitle);
            AppCompatEditText description = dialog.findViewById(R.id.UpdateDescription);
            AppCompatButton Update = dialog.findViewById(R.id.UpdateButton);

            assert Title != null;
            Title.setText(TaskLists.get(holder.getAdapterPosition()).getTitleName());
            assert description != null;
            description.setText(TaskLists.get(holder.getAdapterPosition()).getDescription());
            assert Update != null;
            Update.setOnClickListener(v1 -> {
                String title = Objects.requireNonNull(Title.getText()).toString();
                String SampleDescription = Objects.requireNonNull(description.getText()).toString();
                int position1 = holder.getAdapterPosition();

                // Update the database entry
                NotesEntity updatedNotes = new NotesEntity(title, SampleDescription);
                updatedNotes.setId(TaskLists.get(position1).getId());
                nbd.noteDao().Update((updatedNotes));

                TaskLists.set(position1, updatedNotes);
                notifyItemChanged(position1);
                dialog.dismiss();
            });
            dialog.show();
        });

        holder.itemView.setOnLongClickListener(v -> {

            int position12 = holder.getAdapterPosition();

            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Do You Want To Delete This ?")
                    .setIcon(R.drawable.baseline_delete_24)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Delete the database entry
                        NotesEntity deletedNotes = TaskLists.get(position12);
                        nbd.noteDao().Delete(deletedNotes);

                        TaskLists.remove(position12);
                        notifyItemRemoved(position12);
                    })
                    .setNegativeButton("No", (dialog, which) -> {

                    });
            builder.show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return TaskLists.size();
    }


    //Making My View Holder
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView CardViewTitle;

        private final TextView CardViewDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.CardViewTitle = itemView.findViewById(R.id.cardViewTitle);
            this.CardViewDescription = itemView.findViewById(R.id.CardViewDescription);
        }
    }

}
