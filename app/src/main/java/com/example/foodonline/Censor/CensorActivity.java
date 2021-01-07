package com.example.foodonline.Censor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.Adpter.CensorAdapter;
import com.example.foodonline.Adpter.ChefAdapter;
import com.example.foodonline.DataModel.BillModel;
import com.example.foodonline.DataModel.CensorModel;
import com.example.foodonline.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CensorActivity extends AppCompatActivity {
    TextView tv_type_booking, tv_number_dish, tv_oderer, tv_price;
    ListView list_invoice;

    ArrayList<BillModel> arrayListBill = new ArrayList<>();
    CensorAdapter censorAdapter_censor;
    FirebaseDatabase fData= FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_censor);
         setControl();
         setEvent();
    }

    int i=0;
    private void setEvent() {
         fData.getReference().child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                arrayListBill.add(snapshot.getValue(BillModel.class));
                arrayListBill.get(i).setId(snapshot.getKey());
                i++;
                censorAdapter_censor.notifyDataSetChanged();
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

        censorAdapter_censor = new CensorAdapter(CensorActivity.this, R.layout.item_invoice, arrayListBill);

        list_invoice.setAdapter(censorAdapter_censor);

        list_invoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CensorActivity.this,InvoiceActivity.class);
                intent.putExtra("BillId",arrayListBill.get(i).getId());
                startActivity(intent);
            }
        });


    }

    private void setControl() {
        tv_type_booking = findViewById(R.id.tv_type_booking);
        tv_number_dish = findViewById(R.id.tv_number_dish);
        tv_oderer = findViewById(R.id.tv_oderer);
        tv_price = findViewById(R.id.tv_price);
        list_invoice = findViewById(R.id.list_invoice);
    }

}
