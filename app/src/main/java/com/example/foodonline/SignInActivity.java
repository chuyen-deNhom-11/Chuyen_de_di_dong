package com.example.foodonline;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;



public class SignInActivity extends AppCompatActivity {
    Intent intent;
    Button btn_Cancel;


    public static FirebaseAuth firebaseAuth;
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;

    private String type = "0";
    RadioButton btnRadioAdmin;
    RadioButton btnRadioUser;
    RadioGroup btnRadioGroup;
    Button btnRegister;
    EditText edtName;
    EditText edtTaiKhoan;
    EditText edtPass;
    EditText edtConfirmPass;
    EditText edtAddress;
    EditText edtEmail;
    EditText edtPhoneNumber;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_in);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        create();
        eventClick();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");
    }


    public void eventClick() {
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
                builder.setMessage("Bạn có muốn hủy đăng ký");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent = new Intent(SignInActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Không", null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    private void checkInputText() {

    }

    private void create() {
        btn_Cancel = findViewById(R.id.btn_Cancel);
        btnRadioGroup = findViewById(R.id.btn_radio_group);
        btnRadioAdmin = findViewById(R.id.btn_radio_admin);
        btnRadioUser = findViewById(R.id.btn_radio_user);
        btnRegister = findViewById(R.id.btn_Register);

        edtName = findViewById(R.id.txt_Name);
        edtTaiKhoan = findViewById(R.id.txt_takhoan);
        edtPass = findViewById(R.id.txt_Pass);
        edtConfirmPass = findViewById(R.id.txt_ConfirmPass);
        edtAddress = findViewById(R.id.txt_adress);
        edtEmail = findViewById(R.id.txt_email);
        edtPhoneNumber = findViewById(R.id.txt_PhoneNumber);


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
}
