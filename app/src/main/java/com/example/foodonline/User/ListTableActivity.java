package com.example.foodonline.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.Adpter.ListTableAdapter;
import com.example.foodonline.DataModel.TableModel;
import com.example.foodonline.R;
import com.example.foodonline.User.Fragment.HomUserFragment;
import com.example.foodonline.User.Fragment.SetTableFragment;

import java.util.ArrayList;

public class ListTableActivity extends AppCompatActivity implements ListTableAdapter.ClickLisener{
    ArrayList<TableModel> tableModels;
    RecyclerView list_table;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Danh sách bàn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_list_table);
        list_table = findViewById(R.id.list_table);
        setItemTable();
    }
    private void setItemTable(){
        tableModels = new ArrayList<>();
//        tableModels.add(new TableModel("1","Bàn 1","1","1",1));
//        tableModels.add(new TableModel("1","Bàn 2","2","2",2));
//        tableModels.add(new TableModel("1","Bàn 3","3","3",0));
//        tableModels.add(new TableModel("1","Bàn 4","3","3",0));
//        tableModels.add(new TableModel("1","Bàn 5","3","3",0));
//        tableModels.add(new TableModel("1","Bàn 6","3","3",0));
        LinearLayoutManager layoutManager = new GridLayoutManager(this,3);
        list_table.setLayoutManager(layoutManager);
        ListTableAdapter listTableAdapter = new ListTableAdapter(tableModels,this);
        list_table.setAdapter(listTableAdapter);
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

    @Override
    public void onClick(int posotion) {

    }
}
