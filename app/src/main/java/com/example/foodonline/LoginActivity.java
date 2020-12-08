package com.example.foodonline;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.DataModel.AcountModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    Button sign_in;
    Intent intent;
    TextView sign_up;

    EditText editTextEmail;
    EditText editTextPass;
    public static FirebaseAuth firebaseAuth;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;

    EditText et_acount;
    String userName, passWord;
    TextInputEditText etPassword;
    private DatabaseReference database;
    final ArrayList<AcountModel> listAcount = new ArrayList<AcountModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        sign_up = findViewById(R.id.sign_up);
        editTextEmail = findViewById(R.id.edt_email);
        editTextPass = findViewById(R.id.edt_pass);
        sign_in = findViewById(R.id.sign_in);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void creat() {
        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);
    }


    //    TODO: read data acount
    private void readData() {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("acount").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                AcountModel acount = snapshot.getValue(AcountModel.class);
                listAcount.add(new AcountModel(acount.getTaikhoan(), acount.getMatkhau(), acount.getRole()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z][a-zA-Z\\-]{1,25}" +
                    ")+"
    );


    //    TODO: set click back
    private long backPressTime;

    @Override
    public void onBackPressed() {

        if (backPressTime + 2000 > System.currentTimeMillis()) {
            finishAffinity();
            System.exit(0);
        } else {
            Toast.makeText(LoginActivity.this, R.string.toatsExit, Toast.LENGTH_SHORT).show();
        }
        backPressTime = System.currentTimeMillis();
    }

}
