package com.example.foodonline;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class SignInActivity extends AppCompatActivity {
    Intent intent;
    Button btn_Cancel;

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
                       intent = new Intent(SignInActivity.this, LoginActivity.class);
                       startActivity(intent);
                   }
               });
               builder.setNegativeButton("Không",null);
               Dialog dialog =builder.create();
               dialog.show();
           }
       });

    }
    private void checkInputText(){

    }
    private void create(){
        btn_Cancel = findViewById(R.id.btn_Cancel);
    }
}
