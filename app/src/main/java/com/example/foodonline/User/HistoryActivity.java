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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.Adpter.DepositMoneyAdapter;
import com.example.foodonline.Adpter.HistoryAdapter;
import com.example.foodonline.Adpter.ListDishBillAdapter;
import com.example.foodonline.DataModel.BillModel;
import com.example.foodonline.DataModel.BookingTableModel;
import com.example.foodonline.DataModel.CartModel;
import com.example.foodonline.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.example.foodonline.utils.Constant.USER_ID;

public class HistoryActivity extends AppCompatActivity {
    FirebaseDatabase fData= FirebaseDatabase.getInstance();
    String sUserId;
    ArrayList<BillModel> arrayListBill;
    ArrayList<CartModel> cartModels;
    ArrayList<String> dataSpinner = new ArrayList<>();
    ArrayList<BookingTableModel> bookingTableModels = new ArrayList<>();
    DepositMoneyAdapter depositMoneyAdapter;
    HistoryAdapter historyAdapter;
    Spinner spn_select_type;
    ListView lv_list_history,list_dish;
    LinearLayout layout_close,layout_button,bookingTrue,ln_deposit_money;
    ImageView img_close;
    Intent intent;
    Button btn_Oder,btn_Cancel;
    TextView total_price,tv_name_phone,address,tv_name_date_booking,tv_time,change_tables,tv_deposit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_history);
        intent = getIntent();
        sUserId = intent.getStringExtra(USER_ID);
        dataSpinner.add("Lịch sử đặt món ăn");
        dataSpinner.add("Lịch sử đặt bàn");
        initialization();
//        setHistoryAdapter();
        selectSpinner();
        readDataBill();
    }
    private void selectSpinner(){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataSpinner);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_select_type.setAdapter(arrayAdapter);
        spn_select_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position ==0){
                    readDataBill();
                }else {
                    readDataBookingTable();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    int i=0;
    private void readDataBill(){
        arrayListBill = new ArrayList<>();
        i=0;
        fData.getReference().child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.child("user").getValue(String.class).equals(sUserId)){
                    arrayListBill.add(snapshot.getValue(BillModel.class));
                    arrayListBill.get(i).setId(snapshot.getKey());
                    i++;
                    historyAdapter = new HistoryAdapter(HistoryActivity.this,R.layout.activity_history,arrayListBill);
                    lv_list_history.setAdapter(historyAdapter);
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
    private void readDataBookingTable(){
        i=0;
        bookingTableModels = new ArrayList<>();
        fData.getReference().child("BookingTable").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.child("userId").getValue(String.class).equals(sUserId)){
                    bookingTableModels.add(snapshot.getValue(BookingTableModel.class));
                    bookingTableModels.get(i).setId(snapshot.getKey());
                    if(snapshot.child("status").getValue(Integer.class)!=null){
                        bookingTableModels.get(i).setStatus(snapshot.child("status").getValue(Integer.class));
                    }
                    i++;
                    depositMoneyAdapter= new DepositMoneyAdapter(HistoryActivity.this,R.layout.fragment_censor,bookingTableModels,"HistoryActivity");
                    lv_list_history.setAdapter(depositMoneyAdapter);
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
    private void clickItemListener(final int position){
        final Dialog dialog = new Dialog(this, R.style.MyAlertDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_bill);

        layout_close = dialog.findViewById(R.id.layout_close);
        layout_button = dialog.findViewById(R.id.layout_button);
        bookingTrue = dialog.findViewById(R.id.bookingTrue);
        layout_close.setVisibility(View.VISIBLE);
        img_close = dialog.findViewById(R.id.img_close);
        btn_Cancel = dialog.findViewById(R.id.btn_Cancel);
        btn_Oder = dialog.findViewById(R.id.btn_Oder);
        total_price = dialog.findViewById(R.id.total_price);
        tv_name_phone = dialog.findViewById(R.id.tv_name_phone);
        address= dialog.findViewById(R.id.address);
        tv_name_date_booking = dialog.findViewById(R.id.tv_name_date_booking);
        tv_time = dialog.findViewById(R.id.tv_time);
        change_tables = dialog.findViewById(R.id.change_tables);
        list_dish = dialog.findViewById(R.id.list_dish);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        dialog.show();
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        total_price.setText(arrayListBill.get(position).getPrice());
        tv_name_phone.setText(arrayListBill.get(position).getName() +"-"+arrayListBill.get(position).getPhone());
        address.setText(arrayListBill.get(position).getAdress());
        if (arrayListBill.get(position).getType()==1){
            bookingTrue.setVisibility(View.VISIBLE);
            tv_name_date_booking.setText(arrayListBill.get(position).getName() +"-"+arrayListBill.get(position).getTimeBooking());
            tv_time.setText(arrayListBill.get(position).getDateBooking());
        }
        if (arrayListBill.get(position).getStatus() == 0||arrayListBill.get(position).getStatus() == 5){
            btn_Oder.setText("Đặt cọc");
            btn_Oder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.dialog_deposit_money, null);
                    EditText edt_deposit_money = alertLayout.findViewById(R.id.edt_deposit_money);
                    AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                    builder.setView(alertLayout);
                    builder.setTitle("Nhập tiền cọc");
                    builder.setPositiveButton("Đặt cọc", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!edt_deposit_money.getText().toString().equals("")){
                                fData.getReference().child("Bill").child(arrayListBill.get(position).getId()).child("depositMoney").setValue(edt_deposit_money.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        fData.getReference().child("Bill").child(arrayListBill.get(position).getId()).child("status").setValue(6);
                                    }
                                });
                            }else {
                                Toast.makeText(HistoryActivity.this, "Vui lòng nhập tiền cọc !",Toast.LENGTH_LONG).show();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                    builder.setMessage("Bạn có muốn hủy hóa đơn này?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            fData.getReference().child("Bill").child(arrayListBill.get(position).getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(HistoryActivity.this,"Hủy thành công",Toast.LENGTH_SHORT).show();
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
        }
        else {
            layout_button.setVisibility(View.GONE);
        }
        cartModels = new ArrayList<>();
        fData.getReference().child("Bill").child(arrayListBill.get(position).getId()).child("Dish").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                cartModels.add(snapshot.getValue(CartModel.class));
                ListDishBillAdapter listDishBillAdapter = new ListDishBillAdapter(HistoryActivity.this,R.layout.fragment_bill,cartModels,sUserId,"HistoryActivity");
                list_dish.setAdapter(listDishBillAdapter);
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
    private void initialization(){
        spn_select_type = findViewById(R.id.spn_select_type);
        lv_list_history = findViewById(R.id.lv_list_history);

//        spn_select_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position ==0){
//
//                }
//            }
//        });
        lv_list_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickItemListener(position);
            }
        });
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
