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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {
EditText Btitle;
EditText Bdisc;
Button Bclose;
Button BDone;
   // ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Btitle = findViewById(R.id.oldTitle);
        Bdisc = findViewById(R.id.oldDesc);
        Bclose = findViewById(R.id.bItemClose);
        BDone = findViewById(R.id.bdone);

        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("discrip");


        Btitle.setText(title);
        Bdisc.setText(description);

        Bclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // progressBar.setVisibility(View.VISIBLE);
                String id = getIntent().getStringExtra("id");
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();

                mDatabase.child("Notes").child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetailActivity.this, "Deleted!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DetailActivity.this,home.class));
                     //   progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });


        BDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   progressBar.setVisibility(View.VISIBLE);
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();
            String    titlesend = Btitle.getText().toString();
            String    disSend = Bdisc.getText().toString();
                String id = getIntent().getStringExtra("id");
                NotesPojo noteData = new NotesPojo(id,titlesend,disSend);
                mDatabase.child("Notes").child(id).setValue(noteData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(DetailActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DetailActivity.this,home.class));
                            //    progressBar.setVisibility(View.INVISIBLE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
}
