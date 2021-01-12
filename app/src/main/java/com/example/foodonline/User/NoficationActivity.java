package com.example.foodonline.User;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.Adpter.ListDishBillAdapter;
import com.example.foodonline.Adpter.ListNofictionAdapter;
import com.example.foodonline.DataModel.CartModel;
import com.example.foodonline.DataModel.NoficationModel;
import com.example.foodonline.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.foodonline.utils.Constant.USER_ID;

public class NoficationActivity extends AppCompatActivity {
    ArrayList<NoficationModel> data;
    ListView list_item_nofication;
    FirebaseDatabase fData = FirebaseDatabase.getInstance();
    Intent intent;
    String sUserId;
    Button btn_Oder,btn_Cancel;
    TextView total_price, address,tv_name_phone,tv_name_date_booking,tv_time;
    LinearLayout bookingTrue,layout_ship,layout_close;
    String name, phoneNumber, adress, sTime, sDate, sNameTable, sTableID = "";
    ArrayList<CartModel> cartModels;
    ListView list_dish;
    ImageView img_close;
    ListNofictionAdapter nofictionAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nofication);
        setTitle("Thông báo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list_item_nofication = findViewById(R.id.list_item_nofication);
        intent = getIntent();
        sUserId = intent.getStringExtra(USER_ID);
        setItemNofication();
    }
    int i =0;
    private void setItemNofication(){
        data = new ArrayList<>();

        fData.getReference().child("Users").child(sUserId).child("Notification").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                data.add(snapshot.getValue(NoficationModel.class));
                data.get(i).setId(snapshot.getKey());
                i++;
                 nofictionAdapter = new ListNofictionAdapter(NoficationActivity.this,R.layout.activity_nofication,data);
                list_item_nofication.setAdapter(nofictionAdapter);
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
        list_item_nofication.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemNofication(position);
            }
        });
    }
    private void selectItemNofication(int position){
        if (data.get(position).getStatus() == 0){
            readData(data.get(position).getId());
            final Dialog dialog = new Dialog(this, R.style.MyAlertDialogTheme);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.fragment_bill);
            layout_close = dialog.findViewById(R.id.layout_close);
            total_price = dialog.findViewById(R.id.total_price);
            btn_Oder = dialog.findViewById(R.id.btn_Oder);
            btn_Cancel = dialog.findViewById(R.id.btn_Cancel);
            img_close = dialog.findViewById(R.id.img_close);
            layout_ship = dialog.findViewById(R.id.layout_ship);
            list_dish = dialog.findViewById(R.id.list_dish);
            bookingTrue = dialog.findViewById(R.id.bookingTrue);
            tv_name_phone = dialog.findViewById(R.id.tv_name_phone);
            tv_name_date_booking = dialog.findViewById(R.id.tv_name_date_booking);
            address = dialog.findViewById(R.id.address);
            tv_time = dialog.findViewById(R.id.tv_time);
            layout_close.setVisibility(View.VISIBLE);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btn_Oder.setText("Đặt cọc");
            btn_Oder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.dialog_deposit_money, null);
                    EditText edt_deposit_money = alertLayout.findViewById(R.id.edt_deposit_money);
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoficationActivity.this);
                    builder.setView(alertLayout);
                    builder.setTitle("Nhập tiền cọc");
                    builder.setPositiveButton("Đặt cọc", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!edt_deposit_money.getText().toString().equals("")){
                                fData.getReference().child("Bill").child(data.get(position).getId()).child("depositMoney").setValue(edt_deposit_money.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        fData.getReference().child("Bill").child(data.get(position).getId()).child("status").setValue(6);
                                        fData.getReference().child("Users").child(sUserId).child("Notification").child(data.get(position).getId())
                                                .child("status").setValue(1);
                                    }
                                });
                            }else {
                                Toast.makeText(NoficationActivity.this, "Vui lòng nhập tiền cọc !",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Hủy", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            btn_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoficationActivity.this);
                    builder.setMessage("Bạn có muốn hủy hóa đơn này?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            fData.getReference().child("Bill").child(data.get(position).getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(NoficationActivity.this,"Hủy thành công",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Không", null);
                    Dialog dialog = builder.create();
                    dialog.show();
                }
            });
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
            dialog.show();
        }
    }

    private void readData(String sID){
        cartModels = new ArrayList<>();
        fData.getReference().child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().equals(sID)){
                    total_price.setText(snapshot.child("price").getValue(String.class));
                    name = snapshot.child("name").getValue(String.class);
                    phoneNumber = snapshot.child("phone").getValue(String.class);
                    adress = snapshot.child("adress").getValue(String.class);
                    if (snapshot.child("tableID").getValue(String.class)!=null){
                        sTableID = snapshot.child("tableID").getValue(String.class);
                        bookingTrue.setVisibility(View.VISIBLE);
                        sTime=snapshot.child("timeBooking").getValue(String.class);
                        sDate = snapshot.child("dateBooking").getValue(String.class);
                        sNameTable = snapshot.child("nameTable").getValue(String.class);
                    }
                    tv_name_phone.setText(name +" - "+phoneNumber);
                    address.setText(adress);
                    tv_name_date_booking.setText(sNameTable +"-"+sTime);
                    tv_time.setText(sDate);
                    for (DataSnapshot postSnapshot : snapshot.child("Dish").getChildren()){
                        cartModels.add(postSnapshot.getValue(CartModel.class));
                        ListDishBillAdapter listDishBillAdapter = new ListDishBillAdapter(NoficationActivity.this,R.layout.fragment_bill,cartModels,sUserId,"HistoryActivity");
                        list_dish.setAdapter(listDishBillAdapter);
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
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
