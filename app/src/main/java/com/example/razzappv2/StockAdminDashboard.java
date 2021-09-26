package com.example.razzappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class StockAdminDashboard extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    ImageView popup_img;
    RelativeLayout electronicbtn,stationarybtn,foodbtn,cothbtn;

    SliderView sliderView;
    int[] images = {R.drawable.imgc,
            R.drawable.imgo,
            R.drawable.imgr,
            R.drawable.imgt,
            R.drawable.imgt
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_stock_admin_dashboard);



        popup_img = findViewById(R.id.popup_img);

        PopupMenu popupMenu = new PopupMenu(this,popup_img);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        TextView name = findViewById(R.id.username);
        TextView email = findViewById(R.id.useremail);
        ImageView prof = findViewById(R.id.user_photo);

        name.setText(currentUser.getDisplayName());
        email.setText(currentUser.getEmail());

        Glide.with(this)  // this
                .load(currentUser.getPhotoUrl())
                .override(100, 200)
                .into(prof);

        electronicbtn = (RelativeLayout) findViewById(R.id.electronicbtn);
        stationarybtn = (RelativeLayout) findViewById(R.id.stationarybtn);
        foodbtn = (RelativeLayout) findViewById(R.id.foodbtn);
        cothbtn = (RelativeLayout) findViewById(R.id.cothbtn);

        electronicbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openElectronic();
            }
        });

        stationarybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStaionary();
            }
        });


        foodbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openfood();
            }
        });

        cothbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencloth();
            }
        });

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


        sliderView = findViewById(R.id.image_slider);
        SliderAdapter sliderAdapter = new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();
    }

    private void openElectronic() {
        Intent intent = new Intent(this, ElectronicDasboard.class);
        startActivity(intent);
    }

    private void openStaionary() {
        Intent intent = new Intent(this, StationeryDasboard.class);
        startActivity(intent);
    }


    private void openfood() {
        Intent intent = new Intent(this, FoodDashboard.class);
        startActivity(intent);
    }


    private void opencloth() {
        Intent intent = new Intent(this, ClothDashboard.class);
        startActivity(intent);
    }


}