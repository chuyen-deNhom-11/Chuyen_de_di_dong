package com.example.foodonline;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.DataModel.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class SignInActivity extends AppCompatActivity {
    Intent intent;
    Button btn_Cancel, btn_Register;
    TextView txt_Name, txt_Pass, txt_ConfirmPass, txt_adress, txt_PhoneNumber, txt_email;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
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
                        intent = new Intent(SignInActivity.this, LoginActivity.class);
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
        if (txt_Name.getText().toString().equals("") ||  txt_Pass.getText().toString().equals("") || txt_ConfirmPass.getText().toString().equals("") || txt_adress.getText().toString().equals("") || txt_email.getText().toString().equals("") || txt_PhoneNumber.getText().toString().equals("")) {
            Toast.makeText(this, R.string.check_input_text, Toast.LENGTH_LONG).show();
        }
        else if (!txt_Pass.getText().toString().equals(txt_ConfirmPass.getText().toString())) {
            Toast.makeText(this, R.string.check_password, Toast.LENGTH_LONG).show();
        } else {
            fAuth.createUserWithEmailAndPassword(txt_email.getText().toString(),txt_Pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        UserModel userModel = new UserModel();
                        userModel.setUid(fAuth.getUid());
                        userModel.setEmail(txt_email.getText().toString());
                        userModel.setName(txt_Name.getText().toString());
                        userModel.setAdress(txt_adress.getText().toString());
                        userModel.setNumberPhone(txt_PhoneNumber.getText().toString());
                        userModel.setPassword(txt_Pass.getText().toString());
                        userModel. setType("0");
                        FirebaseDatabase.getInstance().getReference("Users").child(userModel.getUid()).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                intent = new Intent(SignInActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(SignInActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private void create() {
        btn_Cancel = findViewById(R.id.btn_Cancel);
        btn_Register = findViewById(R.id.btn_Register);
        txt_Name = findViewById(R.id.txt_Name);
        txt_Pass = findViewById(R.id.txt_Pass);
        txt_ConfirmPass = findViewById(R.id.txt_ConfirmPass);
        txt_adress = findViewById(R.id.txt_adress);
        txt_email = findViewById(R.id.txt_email);
        txt_PhoneNumber = findViewById(R.id.txt_PhoneNumber);
        fAuth = FirebaseAuth.getInstance();
    }
}