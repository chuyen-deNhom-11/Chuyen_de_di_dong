package com.example.foodonline.Censor.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.foodonline.Adpter.CensorAdapter;
import com.example.foodonline.Censor.InvoiceActivity;
import com.example.foodonline.DataModel.BillModel;
import com.example.foodonline.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//đã thanh toán
public class PaidFragment extends Fragment {

    TextView tv_type_booking, tv_number_dish, tv_oderer, tv_price;
    ListView list_invoice;
    Context context;
    ArrayList<BillModel> arrayListBill = new ArrayList<>();
    CensorAdapter censorAdapter_censor;
    FirebaseDatabase fData= FirebaseDatabase.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_censor, container, false);
        setControl(root);
        setEvent();
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    int i=0;
    private void setEvent() {
        fData.getReference().child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.child("status").getValue(Integer.class) == 4) {
                    arrayListBill.add(snapshot.getValue(BillModel.class));
                    arrayListBill.get(i).setId(snapshot.getKey());
                    i++;
                    censorAdapter_censor.notifyDataSetChanged();
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

        censorAdapter_censor = new CensorAdapter(context, R.layout.item_invoice, arrayListBill,"PaidFragment");

        list_invoice.setAdapter(censorAdapter_censor);

        list_invoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, InvoiceActivity.class);
                intent.putExtra("BillId",arrayListBill.get(i).getId());
                intent.putExtra("sLayout","PaidFragment");
                startActivity(intent);
            }
        });


    }
    private void setControl(View view) {
        tv_type_booking = view.findViewById(R.id.tv_type_booking);
        tv_number_dish = view.findViewById(R.id.tv_number_dish);
        tv_oderer = view.findViewById(R.id.tv_oderer);
        tv_price = view.findViewById(R.id.tv_price);
        list_invoice = view.findViewById(R.id.list_invoice);
    }
}