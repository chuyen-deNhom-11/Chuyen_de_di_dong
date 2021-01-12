package com.example.foodonline.Chef;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.DataModel.UserModel;
import com.example.foodonline.LoginActivity;
import com.example.foodonline.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.foodonline.utils.Constant.USER_ID;
import static com.example.foodonline.utils.Constant.USER_REFERENCES;

public class InfomationActivity extends AppCompatActivity {
    String userID,sPassword,sEmail,sAdress,sName,sPhone;
    TextView tv_Name, tv_PhoneNumber, tv_Adress,tv_Email,tv_LogOut,tv_ChangePass,tvUpdate;
    EditText edt_old_password,et_Name,et_PhoneNumber,et_Adress;
    Button btn_cancel,btn_reset_password,btn_reset_infonation;
    LinearLayout ln_history;
    private DatabaseReference database;
    Intent intent;
    FirebaseDatabase fData = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.fragment_information);
        intent = getIntent();
        userID = intent.getStringExtra(USER_ID);
        creat();
        readData();
        setClickButton();
    }
    private void readData(){
        database = FirebaseDatabase.getInstance().getReference();
        database.child(USER_REFERENCES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().equals(userID)){
                    UserModel user = snapshot.getValue(UserModel.class);
                    tv_Name.setText(user.getName());
                    tv_PhoneNumber.setText(user.getNumberPhone());
                    tv_Email.setText(user.getEmail());
                    tv_Adress.setText(user.getAdress());
                    sPassword = user.getPassword();
                    sEmail = user.getEmail();
                    sAdress= user.getAdress();
                    sName = user.getName();
                    sPhone = user.getNumberPhone();
                }
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
    private void setClickButton() {
        tv_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new  AlertDialog.Builder(InfomationActivity.this);
                builder.setMessage(R.string.alert_logout)
                        .setPositiveButton("có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                intent = new Intent(InfomationActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Không",null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
        tv_ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogResetPassword();
            }
        });
        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogResetInfomation();
            }
        });
    }
    private void dialogResetPassword(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_reset_password, null);

        edt_old_password = alertLayout.findViewById(R.id.edt_old_password);
        btn_cancel = alertLayout.findViewById(R.id.btn_cancel);
        btn_reset_password = alertLayout.findViewById(R.id.btn_reset_password);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(alertLayout);
        final AlertDialog dialog = builder.create();
        dialog.show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_old_password.getText().toString().equals(sPassword)) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(sEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(InfomationActivity.this);
                            builder.setMessage("Vui lòng kiểm tra tin nhắn trong email !");
                            builder.setPositiveButton("ok",null);
                            Dialog dialogNotification = builder.create();
                            dialogNotification.show();
                        }
                    });
                }else{
                    Toast.makeText(InfomationActivity.this,"Sai mật khẩu",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void dialogResetInfomation(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_reset_infomation, null);

        et_Name = alertLayout.findViewById(R.id.et_Name);
        et_PhoneNumber= alertLayout.findViewById(R.id.et_PhoneNumber);
        et_Adress = alertLayout.findViewById(R.id.et_Adress);
        btn_cancel = alertLayout.findViewById(R.id.btn_cancel);
        btn_reset_infonation = alertLayout.findViewById(R.id.btn_reset_infonation);

        et_Name.setText(sName);
        et_Adress.setText(sAdress);
        et_PhoneNumber.setText(sPhone);

        AlertDialog.Builder builder = new AlertDialog.Builder(InfomationActivity.this);
        builder.setView(alertLayout);
        final AlertDialog dialog = builder.create();
        dialog.show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_reset_infonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName = et_Name.getText().toString();
                sPhone = et_PhoneNumber.getText().toString();
                sAdress = et_Adress.getText().toString();
                if (sEmail.equals("")||sPhone.equals("")||sAdress.equals("")){
                    Toast.makeText(InfomationActivity.this,"Vui Lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }else {
                    fData.getReference().child("Users").child(userID).child("name").setValue(sName);
                    fData.getReference().child("Users").child(userID).child("numberPhone").setValue(sPhone);
                    fData.getReference().child("Users").child(userID).child("adress").setValue(sAdress);
                    dialog.dismiss();
                }
            }
        });
    }
    private void creat() {
        tv_Name = findViewById(R.id.tv_name);
        tv_PhoneNumber = findViewById(R.id.tv_phone_no);
        tv_Adress = findViewById(R.id.tv_adress);
        tv_Email = findViewById(R.id.tv_email);
        tv_LogOut = findViewById(R.id.tv_LogOut);
        tv_ChangePass = findViewById(R.id.tv_ChangePass);
        tvUpdate = findViewById(R.id.tvUpdate);
        ln_history = findViewById(R.id.ln_history);
        ln_history.setVisibility(View.GONE);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
