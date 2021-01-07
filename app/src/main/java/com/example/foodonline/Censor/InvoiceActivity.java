package com.example.foodonline.Censor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.Adpter.ListDishBillAdapter;
import com.example.foodonline.DataModel.BillModel;
import com.example.foodonline.DataModel.CartModel;
import com.example.foodonline.LoginActivity;
import com.example.foodonline.R;
import com.example.foodonline.SignInActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InvoiceActivity extends AppCompatActivity {
    Button btn_HuyDon, btn_XNDon;
    Intent intent;
    String sIdBill;
    TextView tv_price,tv_address,tv_PhoneNumber,tv_total_people,tv_name,tv_type_booking;
    ListView lv_detail_invoice;
    FirebaseDatabase fData= FirebaseDatabase.getInstance();
    ArrayList<CartModel> cartModels = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_invoice);
        intent = getIntent();
        sIdBill = intent.getStringExtra("BillId");
        create();
        setOnClick();
        setDataListener();
    }

    private void setOnClick() {
        btn_HuyDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InvoiceActivity.this);
                builder.setMessage("Bạn có muốn hủy đơn");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       finish();
                    }
                });
                builder.setNegativeButton("Không", null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
        btn_XNDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InvoiceActivity.this);
                builder.setMessage("Xác nhận đơn hàng");
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("Không", null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    int iType;
    private void setDataListener(){
        fData.getReference().child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (sIdBill.equals(snapshot.getKey())){
                    tv_name.setText(snapshot.child("name").getValue(String.class));
                    tv_address.setText(snapshot.child("adress").getValue(String.class));
                    iType = snapshot.child("type").getValue(Integer.class);
                    tv_price.setText(snapshot.child("price").getValue(String.class));
                    if (iType ==0){
                        tv_type_booking.setText("Online");
                    }else if (iType == 1){
                        tv_type_booking.setText(snapshot.child("nameTable").getValue(String.class) +"(online)");
                    }else {
                        tv_type_booking.setText(snapshot.child("nameTable").getValue(String.class));
                    }
                    for (DataSnapshot postSnapshot : snapshot.child("Dish").getChildren()){
                        cartModels.add(postSnapshot.getValue(CartModel.class));
                        ListDishBillAdapter listDishBillAdapter = new ListDishBillAdapter(InvoiceActivity.this,R.layout.fragment_bill,cartModels,"userID","HistoryActivity");
                        lv_detail_invoice.setAdapter(listDishBillAdapter);
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
    private void create() {
        btn_HuyDon = findViewById(R.id.btn_HuyDon);
        btn_XNDon = findViewById(R.id.btn_XNDon);
        tv_type_booking = findViewById(R.id.tv_type_booking);
        tv_address = findViewById(R.id.tv_address);
        tv_name = findViewById(R.id.tv_name);
        tv_price = findViewById(R.id.tv_price);
        tv_total_people = findViewById(R.id.tv_total_people);
        tv_PhoneNumber = findViewById(R.id.tv_PhoneNumber);
        lv_detail_invoice = findViewById(R.id.lv_detail_invoice);
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
