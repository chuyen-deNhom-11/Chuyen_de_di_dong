package com.example.foodonline.User.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.Adpter.ListTableAdapter;
import com.example.foodonline.DataModel.TableModel;
import com.example.foodonline.R;

import java.util.ArrayList;

public class SetTableFragment extends Fragment {

    Context context;
    ArrayList<TableModel> tableModels;
    RecyclerView list_table;

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
        tableModels.add(new TableModel("1","Bàn 1","1","1",1));
        tableModels.add(new TableModel("1","Bàn 2","2","2",2));
        tableModels.add(new TableModel("1","Bàn 3","3","3",0));
        tableModels.add(new TableModel("1","Bàn 3","3","3",0));
        tableModels.add(new TableModel("1","Bàn 3","3","3",0));
        tableModels.add(new TableModel("1","Bàn 3","3","3",0));
        LinearLayoutManager layoutManager = new GridLayoutManager(context,3);
        list_table.setLayoutManager(layoutManager);
        ListTableAdapter listTableAdapter = new ListTableAdapter(tableModels);
        list_table.setAdapter(listTableAdapter);
    }
}
