package com.example.razzappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SuperAdminDashboard extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mDatabaseReference2;
    private DatabaseReference mDatabaseReference3;
    private DatabaseReference mDatabaseReference4;
    RelativeLayout rel4,rel3;
    ImageView popup_img;
    TextView elecsum,foodsum,stationsum,clothsum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_super_admin_dashboard);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabaseReference2= FirebaseDatabase.getInstance().getReference().child("clothproduct");
        mDatabaseReference= FirebaseDatabase.getInstance().getReference().child("eproduct");
        mDatabaseReference3= FirebaseDatabase.getInstance().getReference().child("foodproduct");
        mDatabaseReference4= FirebaseDatabase.getInstance().getReference().child("statproduct");


        mDatabaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int child=0;
                if(snapshot.exists()) {
                    child = (int) snapshot.getChildrenCount();
                    clothsum.setText(Integer.toString(child));
                }else{
                    clothsum.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int child=0;
                if(snapshot.exists()) {
                    child = (int) snapshot.getChildrenCount();
                    elecsum.setText(Integer.toString(child));
                }else{
                    elecsum.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int child=0;
                if(snapshot.exists()) {
                    child = (int) snapshot.getChildrenCount();
                    foodsum.setText(Integer.toString(child));
                }else{
                    foodsum.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabaseReference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int child=0;
                if(snapshot.exists()) {
                    child = (int) snapshot.getChildrenCount();
                    stationsum.setText(Integer.toString(child));
                }else{
                    stationsum.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        elecsum=(TextView)findViewById(R.id.elecsum);
        foodsum=(TextView)findViewById(R.id.foodsum);
        stationsum=(TextView)findViewById(R.id.stationsum);
        clothsum=(TextView)findViewById(R.id.clothsum);
        TextView name = findViewById(R.id.username);
        TextView email = findViewById(R.id.useremail);
        ImageView prof = findViewById(R.id.user_photo);

        name.setText(currentUser.getDisplayName());
        email.setText(currentUser.getEmail());

        Glide.with(this)  // this
                .load(currentUser.getPhotoUrl())
                .override(100, 200)
                .into(prof);

        popup_img = findViewById(R.id.popup_img);

        PopupMenu popupMenu = new PopupMenu(this,popup_img);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id==R.id.logout){



                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();



                }
                return false;
            }
        });
        popup_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });

        rel4 = findViewById(R.id.rel4);
        rel3 = findViewById(R.id.rel3);

        rel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    openadminM();

            }
        });

        rel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegDas();
            }
        });
    }

    private void openRegDas() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void openadminM() {
        Intent intent = new Intent(this, ShowStockAdmins.class);
        startActivity(intent);
        finish();
    }
}