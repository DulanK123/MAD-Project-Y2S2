package com.example.razzappv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {




    ImageView ImgUserPhoto;
    static int PReqCode = 1;
    static int REQUESCODE=1;
    Uri pickedImgUri;


    private EditText userEmail,userPassword,userNic,userAddress,userName;
    private ProgressBar loadingProgress;
    private Button regBtn;
    private RadioButton regSAdmin;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;

    ImageView regbk;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        regbk = findViewById(R.id.regbk);
        userEmail =findViewById(R.id.regMail);
        userPassword=findViewById(R.id.regPassword);
        userNic=findViewById(R.id.regNic);
        userName=findViewById(R.id.regName);
        userAddress=findViewById(R.id.regAddress);
        loadingProgress=findViewById(R.id.regProgressBar);
        regSAdmin=findViewById(R.id.regSAdmin);
        regBtn = findViewById(R.id.regBtn);
        loadingProgress.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        regbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSupDas();
            }
        });


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regBtn.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);
                final String email =userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                final String nic = userNic.getText().toString();
                final String name = userName.getText().toString();
                final String address = userAddress.getText().toString();
                final String isSAdmin=regSAdmin.getText().toString();


                if(email.isEmpty()||name.isEmpty()||password.isEmpty()||nic.isEmpty()||address.isEmpty()||isSAdmin.isEmpty()){

                    //display error message something goes wrong
                    showMessage("Please verify all field");
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);

                }
                else{
                    //if there is not error start create a user account
                    //CreateAccount method wii try to create the user if the email is valid
                    CreateUserAccount(email,name,password,nic,address,isSAdmin);
                }

            }
        });

        ImgUserPhoto=findViewById(R.id.regUserPhoto);
        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT>=22){
                    checkAndRequestForPermission();
                }
                else {
                    openGallery();
                }


            }
        });

    }

    private void openSupDas() {
        Intent intent = new Intent(this, SuperAdminDashboard.class);
        startActivity(intent);
        finish();
    }


    private void CreateUserAccount(String email, String name, String password, String nic,String address,String isSAdmin) {

        //this method create user account with specific email and password
        if (pickedImgUri == null) {
            showMessage("Please verify all field");

            regBtn.setVisibility(View.VISIBLE);
            loadingProgress.setVisibility(View.INVISIBLE);
        }else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userid= user.getUid();
                                DocumentReference df = fStore.collection("Users").document(user.getUid());
                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("id",userid);
                                userInfo.put("Name", name);
                                userInfo.put("email", email);
                                userInfo.put("password", password);
                                userInfo.put("nic", nic);
                                userInfo.put("address", address);
                                userInfo.put("isSAdmin", isSAdmin);


                                df.set(userInfo);

                                //user account create successfully
                                showMessage("Account created");
                                //after we created user account we need update his profile and name
                                updateUserInfo(name, pickedImgUri, mAuth.getCurrentUser());


                            } else {
                                //account craete faild
                                showMessage("account create faild" + task.getException().getMessage());
                                regBtn.setVisibility(View.VISIBLE);
                                loadingProgress.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
        }
    }

    //update user photo and name
    private void updateUserInfo(String name, Uri pickedImgUri, FirebaseUser currentUser) {

        //first upload user photo to firebase storage and get URI

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());

        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //image upload successfullly now we cant get our imageuri
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //uri contain user image uri

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();


                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            mAuth.signOut();
                                            mAuth.signInWithEmailAndPassword("super@gmail.com", "123456").addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    updateUI();

                                                }
                                            });
                                            //user info uploaded successfully
                                            showMessage("Register complete");

                                        }
                                    }
                                });

                    }
                });

            }
        });

    }




    private void updateUI() {
        Intent SuperDashboard = new Intent(getApplicationContext(),SuperAdminDashboard.class);
        startActivity(SuperDashboard);
        finish();


    }

    //show message
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();



    }

    private void openGallery() {
        //TODO: open gallery intent  and wait for user to pick an image

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESCODE);
    }

    private void checkAndRequestForPermission() {
        if(ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(RegisterActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();

            }
            else{
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }
        else {
            openGallery();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==REQUESCODE && data != null){
            //save user picked up photo uri

            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);

        }
    }
}