package com.example.razzappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShowStockAdmins extends AppCompatActivity {
    private RecyclerView recyclerview;
    ImageView regbk;
    private FirebaseFirestore db;
    private StockadminAdapter adapter;
    private List<StockadminModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_stock_admins);

        recyclerview=findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        regbk = findViewById(R.id.regbk);
        regbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSupDas();
            }
        });
        db=FirebaseFirestore.getInstance();
        list=new ArrayList<>();
        adapter=new StockadminAdapter(this,list);
        recyclerview.setAdapter(adapter);
        ItemTouchHelper touchHelper =new ItemTouchHelper(new TouchHelper(adapter));
        touchHelper.attachToRecyclerView(recyclerview);

        showData();

    }

    private void openSupDas() {
        Intent intent = new Intent(this, SuperAdminDashboard.class);
        startActivity(intent);
        finish();
    }
    public void showData(){
        db.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        for(DocumentSnapshot snapshot:task.getResult()){
                            StockadminModel stockadminModel =new StockadminModel(snapshot.getString("id"),snapshot.getString("Name"),snapshot.getString("email"));
                            list.add(stockadminModel);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ShowStockAdmins.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}