package com.example.razzappv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class ElectronicDasboard extends AppCompatActivity {

    RecyclerView recyclerView;
    ElectronicAdapter mainAdapter;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_electronic_dasboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ElectronicModel> options =
                new FirebaseRecyclerOptions.Builder<ElectronicModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("eproduct"), ElectronicModel.class)
                        .build();
        mainAdapter = new ElectronicAdapter(options);
        recyclerView.setAdapter(mainAdapter);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddElectronicproduct.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView =(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private void txtSearch(String str){
        FirebaseRecyclerOptions<ElectronicModel> options =
                new FirebaseRecyclerOptions.Builder<ElectronicModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("eproduct").orderByChild("name").startAt(str).endAt(str+"~"), ElectronicModel.class)
                        .build();
        mainAdapter = new ElectronicAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);


    }
}