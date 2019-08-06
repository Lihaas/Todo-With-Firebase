package com.stwalkerster.android.apps.todoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stwalkerster.android.apps.todoapp.DetailActivity;
import com.stwalkerster.android.apps.todoapp.R;
import com.stwalkerster.android.apps.todoapp.model.NotesPojo;

import java.util.List;

public class  TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

List<NotesPojo> notesList;
Context context;

public  TodoAdapter(List<NotesPojo> notesList ,Context context){

        this.context = context;
        this.notesList = notesList;

}

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.items,parent,false);

        return new TodoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
            NotesPojo nota = notesList.get(position);
            holder.tile.setText(nota.getmTitle());
            holder.disc.setText(nota.getmDescription());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }


    public class TodoViewHolder extends RecyclerView.ViewHolder {

           TextView tile;
           TextView disc;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            tile = itemView.findViewById(R.id.Item_title);
            disc = itemView.findViewById(R.id.Item_disc);
            Button closing = itemView.findViewById(R.id.bClose);

            closing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NotesPojo pojoData = notesList.get(getAdapterPosition());
                    String id = pojoData.getmId();
                    DatabaseReference mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    try {
                        mDatabase.child("Notes").child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                            }
                        });
                    }catch (Exception e){}



                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NotesPojo pojoData = notesList.get(getAdapterPosition());
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("id",pojoData.getmId());
                    i.putExtra("title",pojoData.getmTitle());
                    i.putExtra("discrip",pojoData.getmDescription());

                    context.startActivity(i);

                }
            });
        }
    }
}
