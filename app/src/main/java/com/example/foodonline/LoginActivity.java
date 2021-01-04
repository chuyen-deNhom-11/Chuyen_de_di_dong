package com.example.foodonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.Admin.HomeAdminActivity;
import com.example.foodonline.User.HomeUserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.example.foodonline.utils.Constant.USER_ID;
import static com.example.foodonline.utils.Constant.USER_REFERENCES;

public class LoginActivity extends AppCompatActivity {

    Button sign_in;
    Intent intent;
    TextView sign_up;
    EditText et_acount;
    String userName, passWord;
    TextInputEditText etPassword;
    FirebaseAuth fAuth;
    String type;
    private DatabaseReference database;
    //    TODO: set click back
    private long backPressTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        creat();
        eventClick();
    }

    private void eventClick() {
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = et_acount.getText().toString();
                passWord = etPassword.getText().toString();
                loginUser(userName, passWord);
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
        etPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (i) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            userName = et_acount.getText().toString();
                            passWord = etPassword.getText().toString();
                            loginUser(userName, passWord);

                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    private void creat() {
        sign_in = findViewById(R.id.sign_in);
        sign_up = findViewById(R.id.sign_up);
        et_acount = findViewById(R.id.edt_email);
        etPassword = findViewById(R.id.edt_pass);
        fAuth = FirebaseAuth.getInstance();
    }


//    public final Pattern EMAIL_ADDRESS
//            = Pattern.compile(
//            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
//                    "\\@" +
//                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
//                    "(" +
//                    "\\." +
//                    "[a-zA-Z][a-zA-Z\\-]{1,25}" +
//                    ")+"
//    );

    private void loginUser(String email, String passWord) {
        fAuth.signInWithEmailAndPassword(email, passWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference().child(USER_REFERENCES).child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            HashMap hashUser = (HashMap) snapshot.getValue();
                            if (hashUser != null) {
                                type = hashUser.get("type").toString();
                                if (type.equals("0")||type.equals("4")){
                                    intent = new Intent(LoginActivity.this, HomeUserActivity.class);
                                    intent.putExtra(USER_ID, fAuth.getCurrentUser().getUid());
                                    intent.putExtra("type",type);
                                    startActivity(intent);
                                } else if (type.equals("1")) {
                                    intent = new Intent(LoginActivity.this, HomeAdminActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginActivity.this, R.string.wrong_login, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, R.string.wrong_login, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

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