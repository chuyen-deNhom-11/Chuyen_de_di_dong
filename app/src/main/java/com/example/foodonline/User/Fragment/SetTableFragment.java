package com.example.foodonline.User.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.Adpter.ListTableAdapter;
import com.example.foodonline.DataModel.TableModel;
import com.example.foodonline.R;
import com.example.foodonline.User.ListTableActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SetTableFragment extends Fragment implements ListTableAdapter.ClickLisener {

    Context context;
    ArrayList<TableModel> tableModels;
    RecyclerView list_table;
    TextView name_table ,total_people;
    EditText edt_Name,edt_PhoneNumber,edt_adress,edt_bokking_day,edt_time_booking;
    FirebaseDatabase fData = FirebaseDatabase.getInstance();
    ListTableAdapter listTableAdapter;

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        SetTableFragment fragment = new SetTableFragment();
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
        View view = inflater.inflate(R.layout.fragment_set_table, container, false);
        list_table = view.findViewById(R.id.list_table);
        setItemTable();
        return view;
    }
    private void setItemTable(){
        tableModels = new ArrayList<>();
//        tableModels.add(new TableModel("1","Bàn 1","1","1",1));
//        tableModels.add(new TableModel("1","Bàn 2","2","2",2));
//        tableModels.add(new TableModel("1","Bàn 3","3","3",0));
//        tableModels.add(new TableModel("1","Bàn 4","3","3",0));
//        tableModels.add(new TableModel("1","Bàn 5","3","3",0));
//        tableModels.add(new TableModel("1","Bàn 6","3","3",0));
        fData.getReference().child("Table").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               TableModel table = snapshot.getValue(TableModel.class);
               tableModels.add(new TableModel(snapshot.getKey(),table.getNameTable(),table.getNumberPeople(),table.getStatus()));
               listTableAdapter.notifyDataSetChanged();
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
        LinearLayoutManager layoutManager = new GridLayoutManager(context,3);
        list_table.setLayoutManager(layoutManager);
        listTableAdapter = new ListTableAdapter(tableModels,this);
        list_table.setAdapter(listTableAdapter);
    }

    private void showDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_set_the_table, null);

        name_table = alertLayout.findViewById(R.id.name_table);
        total_people = alertLayout.findViewById(R.id.total_people);
        edt_Name = alertLayout.findViewById(R.id.edt_Name);
        edt_adress = alertLayout.findViewById(R.id.edt_adress);
        edt_bokking_day = alertLayout.findViewById(R.id.edt_bokking_day);
        edt_PhoneNumber = alertLayout.findViewById(R.id.edt_PhoneNumber);
        edt_time_booking = alertLayout.findViewById(R.id.edt_time_booking);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(alertLayout);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(int posotion) {
        Toast.makeText(context,tableModels.get(posotion).getNameTable(),Toast.LENGTH_SHORT).show();
        showDialog();
    }
}
