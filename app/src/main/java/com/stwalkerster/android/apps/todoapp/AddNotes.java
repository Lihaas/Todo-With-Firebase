package com.stwalkerster.android.apps.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stwalkerster.android.apps.todoapp.model.NotesPojo;

public class AddNotes extends AppCompatActivity {
Button addnotes;
EditText title,disc;
    String id;
    FirebaseDatabase database ;
    DatabaseReference mDataBase;
    String titlesend,disSend;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        title = findViewById(R.id.setTitle);
        disc = findViewById(R.id.setDesc);
        addnotes = findViewById(R.id.addsnotes);
        database = FirebaseDatabase.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();
addnotes.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      //  progressBar.setVisibility(View.VISIBLE);
        Addnotes();
    }
}); }


private  void  Addnotes(){
        titlesend = title.getText().toString();
        disSend = disc.getText().toString();
        try {
            id = mDataBase.push().getKey();
        }catch (Exception e){}
        NotesPojo noteData = new NotesPojo(id,titlesend,disSend);
        mDataBase.child("Notes").child(id).setValue(noteData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddNotes.this, "Done", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddNotes.this,home.class));
                    //    progressBar.setVisibility(View.INVISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddNotes.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}
