package com.example.foodonline;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.DataModel.UserModel;
import com.example.foodonline.User.HomeUserActivity;
import com.example.foodonline.utils.SharePreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.regex.Pattern;


import static com.example.foodonline.utils.Constant.USER_REFERENCES;



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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                edtName = findViewById(R.id.txt_Name);
//                edtTaiKhoan = findViewById(R.id.txt_takhoan);
//                edtPass = findViewById(R.id.txt_Pass);
//                edtConfirmPass = findViewById(R.id.txt_ConfirmPass);
//                edtAddress = findViewById(R.id.txt_adress);
//                edtEmail = findViewById(R.id.txt_email);
//                edtPhoneNumber = findViewById(R.id.txt_PhoneNumber);
                if (edtName.getText().toString().trim().isEmpty() || edtTaiKhoan.getText().toString().trim().isEmpty() ||
                        edtPass.getText().toString().trim().isEmpty() || edtConfirmPass.getText().toString().trim().isEmpty() || edtAddress.getText().toString().isEmpty()
                ||   edtEmail.getText().toString().trim().isEmpty() ||   edtPhoneNumber.getText().toString().trim().isEmpty()

                ) {
                    Toast.makeText(SignInActivity.this, "Vui lòng  nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!EMAIL_ADDRESS.matcher(edtEmail.getText().toString().trim().trim()).matches()) {
                    Toast.makeText(SignInActivity.this, "Địa chỉ email không đúng", Toast.LENGTH_SHORT).show();
                } else {
                  //  Util.showDialog(RegisterActivity.this);
                    if (btnRadioAdmin.isChecked()) {
                        type = "0";
                    } else {
                        type = "1";
                    }

                    firebaseAuth.createUserWithEmailAndPassword(edtEmail.getText().toString().trim(), edtPass.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        // createTableUser(email, password, name, idServer);
                                        final UserModel userModel = new UserModel();
                                        // uid,name,email,phone, dateOfBirth,password
                                        userModel.setUid(firebaseAuth.getCurrentUser().getUid());
                                        userModel.setName(edtName.getText().toString());
                                        userModel.setEmail(edtEmail.getText().toString());
                                        userModel.setPhoneNumber(edtPhoneNumber.getText().toString());
                                        userModel.setAddress(edtAddress.getText().toString());
                                        userModel.setPassword(edtPass.getText().toString());
                                        userModel.setType(type);
                                        FirebaseDatabase.getInstance().getReference(USER_REFERENCES).child(userModel.getUid()).setValue(userModel)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(SignInActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                            String uid = firebaseAuth.getCurrentUser().getUid();
                                                            firebaseDatabase.getReference("tokens").child(uid).child(FirebaseInstanceId.getInstance().getToken()).push().setValue(true);
                                                            new Handler().postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    Intent intent = new Intent(SignInActivity.this, HomeUserActivity.class);
                                                                    SharePreferenceUtils.saveUserInfor(SignInActivity.this, userModel);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                            }, 1000);

                                                        }
                                                    }
                                                });
                                    } else {
//                                        Util.dismissDialog(RegisterActivity.this);
                                        Log.d("TAG", "onComplete: " + task);
                                        Toast.makeText(SignInActivity.this, "" + (task).getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        // loginFirebase(email, password, name, idServer);
                                    }
                                }
                            });

                }
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
