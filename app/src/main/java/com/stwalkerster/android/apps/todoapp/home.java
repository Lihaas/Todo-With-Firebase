package com.stwalkerster.android.apps.todoapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ProgressBar;
//import com.github.ybq.android.spinkit.SpinKitView;
import java.util.ArrayList;
import java.util.List;
import android.content.DialogInterface;

public class home extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    List<NotesPojo> notesLists = new ArrayList<>();;
    RecyclerView.Adapter adapters;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        progressBar.setVisibility(View.VISIBLE);
        /*Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);*/



      //  new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
         adapters = new TodoAdapter(notesLists,this);
        database = FirebaseDatabase.getInstance();
        databaseReference =database.getReference("Notes");

        recyclerView = findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notesLists.removeAll(notesLists);
                //All data change in database notify here
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                    {
                        //Add data to pojo class
                        NotesPojo noteDatas = dataSnapshot1.getValue(NotesPojo.class);
                        notesLists.add(noteDatas);

                    }
                    //Saying to adapter on chnage on data
                   adapters.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(home.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(home.this,AddNotes.class));
            }
        });

        adapters = new TodoAdapter(notesLists, getApplicationContext());
        recyclerView.setAdapter(adapters);






    }

/*
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    notesLists.remove(viewHolder.getAdapterPosition());
                    adapters.notifyDataSetChanged();
        }
    };*/


}
