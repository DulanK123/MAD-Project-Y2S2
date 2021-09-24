package com.example.razzappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddStationeryitems extends AppCompatActivity {
    EditText name,des,qty,eurl;
    Button btnAdd;
    ImageView btnBack;
    boolean valid=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_stationeryitems);
        name =(EditText) findViewById(R.id.txtName);
        des =(EditText) findViewById(R.id.txtDes);
        qty = (EditText) findViewById(R.id.txtQty);
        eurl =(EditText) findViewById(R.id.txtImageUrl);

        btnAdd=(Button) findViewById(R.id.btnAdd);
        btnBack=(ImageView) findViewById(R.id.regbk);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(name);
                checkField(des);
                checkField(qty);

                if(valid) {

                    insertData();
                    clearAll();
                }else{
                    Toast.makeText(AddStationeryitems.this, "Please fill required filed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddStationeryitems.this, StationeryDasboard.class));
                finish();
            }
        });

    }

    private void insertData(){
        Map<String,Object> map=new HashMap<>();
        map.put("name",name.getText().toString());
        map.put("des",des.getText().toString());
        map.put("qty",qty.getText().toString());
        map.put("eurl",eurl.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("statproduct").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddStationeryitems.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddStationeryitems.this, "error while inserting", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else{
            valid =true;
        }
        return valid;
    }
    private void clearAll(){
        name.setText("");
        des.setText("");
        qty.setText("");
        eurl.setText("");
    }
}