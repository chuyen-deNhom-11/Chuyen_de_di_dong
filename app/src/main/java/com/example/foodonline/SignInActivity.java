package com.example.foodonline;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class SignInActivity extends AppCompatActivity {
    Button btn_Cancel, btn_Register;
    EditText txt_Name, txt_Username, txt_Pass, txt_ConfirmPass, txt_adress, txt_email, txt_PhoneNumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_in);

        create();
        eventClick();
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
                        Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Không", null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputText();

            }
        });

    }

    private void checkInputText() {
        if (txt_Name.getText().toString().equals("") || txt_Username.getText().toString().equals("") || txt_Pass.getText().toString().equals("") || txt_ConfirmPass.getText().toString().equals("") || txt_adress.getText().toString().equals("") || txt_email.getText().toString().equals("") || txt_PhoneNumber.getText().toString().equals("")) {
            Toast.makeText(this, "Nhập thiếu thông tin", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
        }
    }

    private void create() {
        btn_Cancel = findViewById(R.id.btn_Cancel);
        btn_Register = findViewById(R.id.btn_Register);
        txt_Name = findViewById(R.id.txt_Name);
        txt_Username = findViewById(R.id.txt_Username);
        txt_Pass = findViewById(R.id.txt_Pass);
        txt_ConfirmPass = findViewById(R.id.txt_ConfirmPass);
        txt_adress = findViewById(R.id.txt_adress);
        txt_email = findViewById(R.id.txt_email);
        txt_PhoneNumber = findViewById(R.id.txt_PhoneNumber);
    }
}
