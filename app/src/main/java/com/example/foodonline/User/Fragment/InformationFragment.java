package com.example.foodonline.User.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodonline.DataModel.UserModel;
import com.example.foodonline.LoginActivity;
import com.example.foodonline.R;
import com.example.foodonline.User.NoficationActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static com.example.foodonline.utils.Constant.USER_REFERENCES;
import static com.example.foodonline.utils.Constant.USER_ID;
public class InformationFragment extends Fragment {
    Context context;
    LinearLayout btn_history, btn_nofication;
    Intent intent;
    String userID;
    TextView tv_Name, tv_PhoneNumber, tv_Adress,tv_Email,tv_LogOut;
    private DatabaseReference database;


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
    }

    private void setClickButton() {
        btn_nofication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(context, NoficationActivity.class);
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


}
