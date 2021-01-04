package com.example.foodonline.Chef;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodonline.Adpter.ChefAdapter;
import com.example.foodonline.Adpter.ListTableAdapter;
import com.example.foodonline.DataModel.ChefModel;
import com.example.foodonline.R;

import java.util.ArrayList;

public class ChefActivity extends AppCompatActivity {
    TextView name_food, amount, table;
    Button btn_cancel, btn_done_cook;
    ListView lv_DS_Chef;

    ArrayList<ChefModel> data_chef = new ArrayList<>();
    ChefAdapter chefAdapter_chef;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);
        setControl();
        setEvent();
    }

    private void setEvent() {
        data_chef.add(new ChefModel("Bún đậu mắm tôm","Số lượng: 1","Bàn: 9"));
        data_chef.add(new ChefModel("Phở bò","Số lượng: 5","Bàn: 4"));
        data_chef.add(new ChefModel("Bánh cuốn tây sơn","Số lượng: 1","Bàn: 1"));
        data_chef.add(new ChefModel("Sườn quay","Số lượng: 1","Bàn: 12"));
        chefAdapter_chef = new ChefAdapter(ChefActivity.this, R.layout.item_chef, data_chef);
        lv_DS_Chef.setAdapter(chefAdapter_chef);


    }


    private void setControl() {
        name_food = findViewById(R.id.name_food);
        amount = findViewById(R.id.amount);
        table = findViewById(R.id.table);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_done_cook = findViewById(R.id.btn_done_cook);
        lv_DS_Chef = findViewById(R.id.lv_chef);


    }
}
