package com.example.foodonline.Censor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.Adpter.ListDishBillAdapter;
import com.example.foodonline.DataModel.BillModel;
import com.example.foodonline.DataModel.CartModel;
import com.example.foodonline.DataModel.NoficationModel;
import com.example.foodonline.LoginActivity;
import com.example.foodonline.R;
import com.example.foodonline.SignInActivity;
import com.example.foodonline.User.NoficationActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class InvoiceActivity extends AppCompatActivity {
    Button btn_HuyDon, btn_XNDon;
    Intent intent;
    String sIdBill,sLayout,sUserID,sDepositMoney,sTableID;
    int iType,iStatus;
    TextView tv_price,tv_address,tv_PhoneNumber,tv_total_people,tv_name,tv_type_booking,tv_deposit;
    ListView lv_detail_invoice;
    LinearLayout ln_deposit_money;
    FirebaseDatabase fData= FirebaseDatabase.getInstance();
    ArrayList<CartModel> cartModels = new ArrayList<>();
    public static final String COC = "Cọc tiền";
    public static final String THANHTOAN = "Thanh toán";
    public static final String NAU = "Nấu";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_invoice);
        intent = getIntent();
        sIdBill = intent.getStringExtra("BillId");
        sLayout = intent.getStringExtra("sLayout");
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
                String textButton = btn_XNDon.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(InvoiceActivity.this);
                if (textButton.equals(COC)){
                    builder.setMessage("Yều cầu cọc tiền đơn hàng");
                    builder.setPositiveButton("Yêu cầu", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NoficationModel noficationModel = new NoficationModel(System.currentTimeMillis(),0);
                            fData.getReference().child("Bill").child(sIdBill).child("status").setValue(5).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    fData.getReference().child("Users").child(sUserID).child("Notification").child(sIdBill).setValue(noficationModel);
                                    finish();
                                }
                            });
                        }
                    });
                }else if(textButton.equals(NAU)){
                    builder.setMessage("Bắt đầu nấu");
                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fData.getReference().child("Bill").child(sIdBill).child("status").setValue(2);
                            if (sLayout.equals("ApprovedFragment")){
                                if (iType==1){
                                    fData.getReference().child("Table").child(sTableID).child("status").setValue(2);
                                    fData.getReference().child("Table").child(sTableID).child("id_nvoice").setValue(sIdBill);
                                }
                            }
                            finish();
                        }
                    });
                }else if(textButton.equals(THANHTOAN)){
                    builder.setMessage("Thanh toán hóa đơn");
                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fData.getReference().child("Bill").child(sIdBill).child("status").setValue(4);
                            finish();
                        }
                    });
                } else {
                    builder.setMessage("Xác nhận đơn hàng");
                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(iType ==2){
                                fData.getReference().child("Bill").child(sIdBill).child("status").setValue(2);
                            }else {
                                fData.getReference().child("Bill").child(sIdBill).child("status").setValue(1);
                            }
                            finish();
                        }
                    });
                }
                builder.setNegativeButton("Không", null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    int Total;
    private void setDataListener(){
        fData.getReference().child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (sIdBill.equals(snapshot.getKey())){
                    tv_name.setText(snapshot.child("name").getValue(String.class));
                    tv_address.setText(snapshot.child("adress").getValue(String.class));
                    iType = snapshot.child("type").getValue(Integer.class);
                    sUserID = snapshot.child("user").getValue(String.class);
                    iStatus = snapshot.child("status").getValue(Integer.class);
                    tv_PhoneNumber.setText(snapshot.child("phone").getValue(String.class));
                    sDepositMoney = snapshot.child("depositMoney").getValue(String.class);
                    if (snapshot.child("tableID").getValue(String.class)!=null){
                        sTableID= snapshot.child("tableID").getValue(String.class);
                    }
                    if (sDepositMoney != null){
                        ln_deposit_money.setVisibility(View.VISIBLE);
                        tv_deposit.setText(sDepositMoney);
                        Total = Integer.parseInt(snapshot.child("price").getValue(String.class)) - Integer.parseInt(sDepositMoney);
                        tv_price.setText(Total+"");
                    }else {
                        tv_price.setText(snapshot.child("price").getValue(String.class));
                    }
                    if (iStatus == 3){
                        btn_XNDon.setText(THANHTOAN);
                    }
                    if (iType ==0){
                        tv_type_booking.setText("Online");
                        if (iStatus == 0){
                            btn_XNDon.setText(COC);
                        }

                    }else if (iType == 1){
                        tv_type_booking.setText(snapshot.child("nameTable").getValue(String.class) +"(online)");
                        if (iStatus == 0){
                            btn_XNDon.setText(COC);
                        }
                    }else {
                        tv_type_booking.setText(snapshot.child("nameTable").getValue(String.class));
                    }
                    if (iStatus ==1){
                        btn_XNDon.setText(NAU);
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
                if (sIdBill.equals(snapshot.getKey())){
                    tv_name.setText(snapshot.child("name").getValue(String.class));
                    tv_address.setText(snapshot.child("adress").getValue(String.class));
                    iType = snapshot.child("type").getValue(Integer.class);
                    sUserID = snapshot.child("user").getValue(String.class);
                    iStatus = snapshot.child("status").getValue(Integer.class);
                    tv_PhoneNumber.setText(snapshot.child("phone").getValue(String.class));
                    sDepositMoney = snapshot.child("depositMoney").getValue(String.class);
                    if (sDepositMoney != null){
                        ln_deposit_money.setVisibility(View.VISIBLE);
                        tv_deposit.setText(sDepositMoney);
                        Total = Integer.parseInt(snapshot.child("price").getValue(String.class)) - Integer.parseInt(sDepositMoney);
                        tv_price.setText(Total+"");
                    }else {
                        tv_price.setText(snapshot.child("price").getValue(String.class));
                    }
                    if (iStatus == 3){
                        btn_XNDon.setText("Thanh toán");
                    }
                    if (iType ==0){
                        tv_type_booking.setText("Online");
                        if (iStatus == 0){
                            btn_XNDon.setText("Cọc tiền");
                        }

                    }else if (iType == 1){
                        tv_type_booking.setText(snapshot.child("nameTable").getValue(String.class) +"(online)");
                        if (iStatus == 0){
                            btn_XNDon.setText("Cọc tiền");
                        }
                    }else {
                        tv_type_booking.setText(snapshot.child("nameTable").getValue(String.class));
                    }
                    if (iStatus ==1){
                        btn_XNDon.setText("Nấu");
                    }
                    for (DataSnapshot postSnapshot : snapshot.child("Dish").getChildren()){
                        cartModels.add(postSnapshot.getValue(CartModel.class));
                        ListDishBillAdapter listDishBillAdapter = new ListDishBillAdapter(InvoiceActivity.this,R.layout.fragment_bill,cartModels,"userID","HistoryActivity");
                        lv_detail_invoice.setAdapter(listDishBillAdapter);
                    }
                }
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
        ln_deposit_money = findViewById(R.id.ln_deposit_money);
        tv_deposit = findViewById(R.id.tv_deposit);
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
