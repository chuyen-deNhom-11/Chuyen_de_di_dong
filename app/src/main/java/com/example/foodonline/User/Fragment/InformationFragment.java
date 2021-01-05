package com.example.foodonline.User.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodonline.Adpter.HistoryAdapter;
import com.example.foodonline.DataModel.UserModel;
import com.example.foodonline.LoginActivity;
import com.example.foodonline.R;
import com.example.foodonline.SignInActivity;
import com.example.foodonline.User.HistoryActivity;
import com.example.foodonline.User.NoficationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import static com.example.foodonline.utils.Constant.USER_REFERENCES;
import static com.example.foodonline.utils.Constant.USER_ID;
public class InformationFragment extends Fragment {
    Context context;
    LinearLayout btn_history, btn_nofication;
    Intent intent;
    String userID,sPassword,sEmail,sAdress,sName,sPhone;
    TextView tv_Name, tv_PhoneNumber, tv_Adress,tv_Email,tv_LogOut,tv_ChangePass,tvUpdate;
    EditText edt_old_password,edt_new_password,edt_again_password,et_Name,et_PhoneNumber,et_Adress;
    Button btn_cancel,btn_reset_password,btn_reset_infonation;
    private DatabaseReference database;
    FirebaseDatabase fData = FirebaseDatabase.getInstance();
    public static Fragment newInstance(String userId) {
        Bundle args = new Bundle();
        InformationFragment fragment = new InformationFragment();
        args.putString(USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        creat(view);
        setClickButton();

        Bundle bundle = getArguments();
        if (bundle != null) {
            userID = bundle.getString(USER_ID);
        }
        readData();
        return view;
    }

    private void creat(View view) {
        btn_history = view.findViewById(R.id.history);
        btn_nofication = view.findViewById(R.id.notification);
        tv_Name = view.findViewById(R.id.tv_Name);
        tv_PhoneNumber = view.findViewById(R.id.tv_PhoneNumber);
        tv_Adress = view.findViewById(R.id.tv_Adress);
        tv_Email = view.findViewById(R.id.tv_Email);
        tv_LogOut = view.findViewById(R.id.tv_LogOut);
        tv_ChangePass = view.findViewById(R.id.tv_ChangePass);
        tvUpdate = view.findViewById(R.id.tvUpdate);
    }

    private void setClickButton() {
        btn_nofication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context, NoficationActivity.class);
                startActivity(intent);
            }
        });
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, HistoryActivity.class);
                intent.putExtra(USER_ID,userID);
                startActivity(intent);
            }
        });
        tv_LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new  AlertDialog.Builder(context);
                builder.setMessage(R.string.alert_logout)
                        .setPositiveButton("có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                intent = new Intent(context, LoginActivity.class);
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
    private void readData(){
        database = FirebaseDatabase.getInstance().getReference();
        database.child(USER_REFERENCES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().equals(userID)){
                    UserModel user = snapshot.getValue(UserModel.class);
                    tv_Name.setText(user.getName());
                    tv_PhoneNumber.setText(user.getPhoneNumber());
                    tv_Email.setText(user.getEmail());
                    tv_Adress.setText(user.getAdress());
                    sPassword = user.getPassword();
                    sEmail = user.getEmail();
                    sAdress= user.getAdress();
                    sName = user.getName();
                    sPhone = user.getPhoneNumber();
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

    private void dialogResetPassword(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_reset_password, null);

        edt_old_password = alertLayout.findViewById(R.id.edt_old_password);
        btn_cancel = alertLayout.findViewById(R.id.btn_cancel);
        btn_reset_password = alertLayout.findViewById(R.id.btn_reset_password);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setMessage("Vui lòng kiểm tra tin nhắn trong email !");
                            builder.setPositiveButton("ok",null);
                            Dialog dialogNotification = builder.create();
                            dialogNotification.show();
                        }
                    });
                }else{
                    Toast.makeText(context,"Sai mật khẩu",Toast.LENGTH_SHORT).show();
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

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                    Toast.makeText(context,"Vui Lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show();
                }else {
                    fData.getReference().child("Users").child(userID).child("name").setValue(sName);
                    fData.getReference().child("Users").child(userID).child("phoneNumber").setValue(sPhone);
                    fData.getReference().child("Users").child(userID).child("adress").setValue(sAdress);
                    dialog.dismiss();
                }
            }
        });
    }
}
