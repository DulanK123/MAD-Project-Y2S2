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

public class AddFooditems extends AppCompatActivity {

    EditText name,des,qty,eurl,date;
    Button btnAdd;
    ImageView btnBack;
    boolean valid=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_fooditems);
        name =(EditText) findViewById(R.id.txtName);
        des =(EditText) findViewById(R.id.txtDes);
        qty = (EditText) findViewById(R.id.txtQty);
        eurl =(EditText) findViewById(R.id.txtImageUrl);
        date=(EditText) findViewById(R.id.txtdate);
        btnAdd=(Button) findViewById(R.id.btnAdd);
        btnBack=(ImageView) findViewById(R.id.regbk);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(name);
                checkField(des);
                checkField(qty);
                checkField(date);

                if(valid) {

                    insertData();
                    clearAll();
                }else{
                    Toast.makeText(AddFooditems.this, "Please fill required filed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddFooditems.this, FoodDashboard.class));
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
        map.put("date",date.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("foodproduct").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddFooditems.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddFooditems.this, "error while inserting", Toast.LENGTH_SHORT).show();
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
        date.setText("");
    }
}