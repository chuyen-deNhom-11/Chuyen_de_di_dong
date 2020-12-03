package com.example.foodonline;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.foodonline.DataModel.UserModel;
import com.example.foodonline.User.HomeUserActivity;
import com.example.foodonline.utils.SharePreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.DataModel.AcountModel;
import com.example.foodonline.User.HomeUserActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ChildEventListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.regex.Pattern;

import static com.example.foodonline.utils.Constant.USER_REFERENCES;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


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

// Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");

        sign_up = findViewById(R.id.sign_up);
        editTextEmail = findViewById(R.id.edt_email);
        editTextPass = findViewById(R.id.edt_pass);
        sign_in = findViewById(R.id.sign_in);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent = new Intent(LoginActivity.this, HomeUserActivity.class);
//                startActivity(intent);
                if (editTextEmail.getText().toString().trim().isEmpty() || editTextPass.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (!EMAIL_ADDRESS.matcher(editTextEmail.getText().toString().trim().trim()).matches()) {
                    Toast.makeText(LoginActivity.this, "Địa chỉ email không đúng", Toast.LENGTH_SHORT).show();
                } else {


                    firebaseAuth.signInWithEmailAndPassword(editTextEmail.getText().toString().trim(), editTextPass.getText().toString().trim())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {


                                        FirebaseDatabase.getInstance().getReference(USER_REFERENCES).child(firebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                //  String em = String.valueOf(snapshot.getValue());
                                                if (dataSnapshot.exists()) {
                                                    // Toast.makeText(LoginActivity.this, "You already registed", Toast.LENGTH_SHORT).show();
                                                    String uid = firebaseAuth.getCurrentUser().getUid();
                                                    firebaseDatabase.getReference("tokens").child(uid).child(FirebaseInstanceId.getInstance().getToken()).push().setValue(true);

                                                    final UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Intent intent = new Intent(LoginActivity.this, HomeUserActivity.class);
                                                            SharePreferenceUtils.saveUserInfor(LoginActivity.this, userModel);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }, 1000);


                                                    // goToHomeActivity(userModel);
                                                } else {
                                                    // showRegisterDialog(user);
                                                    Toast.makeText(LoginActivity.this, "kkk", Toast.LENGTH_SHORT).show();
                                                }


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                // Util.dismissDialog(LoginActivity.this);
                                                Log.d("TAG", "onCancelled: " + databaseError.getMessage());
                                            }
                                        });
                                    } else {
                                        //  Util.dismissDialog(LoginActivity.this);
                                        Toast.makeText(LoginActivity.this, "" + (task).getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            });
                }
            }


//        creat();
//        eventClick();
        });
    }

//    private void eventClick() {
//        sign_in.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                userName = et_acount.getText().toString();
//                passWord = etPassword.getText().toString();
//                if (!checkAcount(userName, passWord)) {
//                    Toast.makeText(LoginActivity.this, R.string.wrong_login, Toast.LENGTH_SHORT).show();
//                } else {
//                    checkAcount(userName, passWord);
//
//                }
//            }
//        });
//
//        sign_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent = new Intent(LoginActivity.this, SignInActivity.class);
//                startActivity(intent);
//            }
//        });
//        etPassword.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN){
//                    switch (i){
//                        case KeyEvent.KEYCODE_DPAD_CENTER:
//                        case KeyEvent.KEYCODE_ENTER:
//                            userName = et_acount.getText().toString();
//                            passWord = etPassword.getText().toString();
//                            if (!checkAcount(userName, passWord)) {
//                                Toast.makeText(LoginActivity.this, R.string.wrong_login, Toast.LENGTH_SHORT).show();
//                            } else {
//                                checkAcount(userName, passWord);
//                            }
//                        return true;
//                        default:
//                            break;
//                    }
//                }
//                return false;
//            }
//        });
//    }

    //    TODO: Check acount login
//    private boolean checkAcount(String userName, String passWord) {
//        for (int i = 0; i < listAcount.size(); i++) {
//            if (userName.equals(listAcount.get(i).getTaikhoan()) && passWord.equals(listAcount.get(i).getMatkhau())) {
//                if (listAcount.get(i).getRole() == 0) {
//                    intent = new Intent(LoginActivity.this, HomeUserActivity.class);
//                    intent.putExtra("username", userName);
//                }
//                startActivity(intent);
//                return true;
//            }
//        }
//        return false;
//    }
    private void creat() {
        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);
    }

    //    @IgnoreExtraProperties
//    public class User {
//
//        public String username="a";
//        public String email="b";
//
//        public User() {
//            // Default constructor required for calls to DataSnapshot.getValue(User.class)
//        }
//
//        public User(String username, String email) {
//            this.username = username;
//            this.email = email;
//        }
//
//    }
//    private void writeNewUser(String userId, String name, String email) {
//        User user = new User(name, email);
//        DatabaseReference mDatabase;
//
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        mDatabase.child("users").push().setValue(user);
//    }

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
