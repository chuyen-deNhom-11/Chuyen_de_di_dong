package com.example.foodonline.Censor.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodonline.Adpter.DepositMoneyAdapter;
import com.example.foodonline.DataModel.BookingTableModel;
import com.example.foodonline.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BrowseFragment extends Fragment {
    ListView list_invoice;
    FirebaseDatabase fData = FirebaseDatabase.getInstance();
    ArrayList<BookingTableModel> bookingTableModels = new ArrayList<>();
    DepositMoneyAdapter depositMoneyAdapter;
    Context context;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_censor, container, false);
        list_invoice = root.findViewById(R.id.list_invoice);
        setAdpater();
        return root;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    int i=0;
    private void setAdpater(){
        fData.getReference().child("BookingTable").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.child("status").getValue(Integer.class)!=null&&snapshot.child("status").getValue(Integer.class) ==1){
                    bookingTableModels.add(snapshot.getValue(BookingTableModel.class));
                    bookingTableModels.get(i).setId(snapshot.getKey());
                    i++;
                    depositMoneyAdapter.notifyDataSetChanged();
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
        depositMoneyAdapter= new DepositMoneyAdapter(context,R.layout.fragment_censor,bookingTableModels,"BrowseFragment");
        list_invoice.setAdapter(depositMoneyAdapter);
    }
}
