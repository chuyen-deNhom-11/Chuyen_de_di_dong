package com.example.foodonline.Chef;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodonline.Adpter.ChefAdapter;
import com.example.foodonline.Adpter.ListTableAdapter;
import com.example.foodonline.Censor.HomeCensor;
import com.example.foodonline.DataModel.ChefModel;
import com.example.foodonline.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.foodonline.utils.Constant.USER_ID;

public class ChefActivity extends AppCompatActivity {
    Button btn_cancel, btn_done_cook;
    ListView lv_DS_Chef;
    Spinner spn_select_type;
    ArrayList<String> dataSpinner = new ArrayList<>();
    ArrayList<ChefModel> data_chef = new ArrayList<>();
    ChefAdapter chefAdapter_chef;
    FirebaseDatabase fData = FirebaseDatabase.getInstance();
    TextView tv_acount;
    Intent intent;
    String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        getSupportActionBar().hide();
        intent = getIntent();
        userId = intent.getStringExtra("userID");
        setControl();
        setEvent();
        selectSpinner();
    }
    private void selectSpinner(){
        dataSpinner.add("Danh sách món đang nấu");
        dataSpinner.add("Danh sách món hủy");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_select_type.setAdapter(arrayAdapter);
        spn_select_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0){
                    setEvent();
                }else {
                    readDataCanCelCooking();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tv_acount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ChefActivity.this, InfomationActivity.class);
                intent.putExtra(USER_ID, userId);
                startActivity(intent);
            }
        });
    }
    private void readDataCanCelCooking(){
        i=0;
        data_chef = new ArrayList<>();
        fData.getReference().child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.child("status").getValue(Integer.class) == 2) {
                    for (DataSnapshot postSnapshot : snapshot.child("Dish").getChildren()) {
                        if (postSnapshot.child("status").getValue(Integer.class) != null && postSnapshot.child("status").getValue(Integer.class) == 1){
                            data_chef.add(postSnapshot.getValue(ChefModel.class));
                            data_chef.get(i).setIdBill(snapshot.getKey());
                            data_chef.get(i).setType(snapshot.child("type").getValue(Integer.class));
                            data_chef.get(i).setTable(snapshot.child("nameTable").getValue(String.class));
                            data_chef.get(i).setTotalPrice(snapshot.child("price").getValue(String.class));
                            data_chef.get(i).setKeyDish(postSnapshot.getKey());
                            i++;
                           chefAdapter_chef = new ChefAdapter(ChefActivity.this, R.layout.item_chef, data_chef,"DishCancel");
                           lv_DS_Chef.setAdapter(chefAdapter_chef);
                        }
                    }
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
    int i=0;
    private void setEvent() {
        i=0;
        data_chef = new ArrayList<>();
        fData.getReference().child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.child("status").getValue(Integer.class) == 2){
                    for (DataSnapshot postSnapshot : snapshot.child("Dish").getChildren()) {
                        if (postSnapshot.child("status").getValue(Integer.class) == null||postSnapshot.child("status").getValue(Integer.class) ==2) {
                            data_chef.add(postSnapshot.getValue(ChefModel.class));
                            data_chef.get(i).setIdBill(snapshot.getKey());
                            data_chef.get(i).setType(snapshot.child("type").getValue(Integer.class));
                            data_chef.get(i).setTable(snapshot.child("nameTable").getValue(String.class));
                            data_chef.get(i).setKeyDish(postSnapshot.getKey());
                            i++;
                            chefAdapter_chef = new ChefAdapter(ChefActivity.this, R.layout.item_chef, data_chef,"ChefActivity");
                            lv_DS_Chef.setAdapter(chefAdapter_chef);
                        }
                    }
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


    private void setControl() {
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_done_cook = findViewById(R.id.btn_done_cook);
        lv_DS_Chef = findViewById(R.id.lv_chef);
        spn_select_type = findViewById(R.id.spn_select_type);
        tv_acount = findViewById(R.id.tv_acount);
    }
}
