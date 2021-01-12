package com.example.foodonline.Censor.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodonline.Adpter.ChefAdapter;
import com.example.foodonline.Chef.ChefActivity;
import com.example.foodonline.DataModel.ChefModel;
import com.example.foodonline.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DishFragment extends Fragment {
    Button btn_cancel, btn_done_cook;
    ListView list_invoice;
    Context context;
    ArrayList<ChefModel> data_chef = new ArrayList<>();
    ChefAdapter chefAdapter_chef;
    FirebaseDatabase fData = FirebaseDatabase.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

    int i = 0;

    private void setEvent() {
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
                        chefAdapter_chef.notifyDataSetChanged();
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
        chefAdapter_chef = new ChefAdapter(context, R.layout.item_chef, data_chef, "DishFragment");
        list_invoice.setAdapter(chefAdapter_chef);


    }

    private void setControl(View view) {
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_done_cook = view.findViewById(R.id.btn_done_cook);
        list_invoice = view.findViewById(R.id.list_invoice);
    }
}
