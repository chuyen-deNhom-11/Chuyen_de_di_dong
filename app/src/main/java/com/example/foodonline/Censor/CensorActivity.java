package com.example.foodonline.Censor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.Adpter.CensorAdapter;
import com.example.foodonline.Adpter.ChefAdapter;
import com.example.foodonline.DataModel.CensorModel;
import com.example.foodonline.R;

import java.util.ArrayList;

public class CensorActivity extends AppCompatActivity {
    TextView tv_type_booking, tv_number_dish, tv_oderer, tv_price;
    ListView list_invoice;

    ArrayList<CensorModel> data_censor = new ArrayList<>();
    ArrayList<CensorModel> data_censor_price = new ArrayList<>();
    CensorAdapter censorAdapter_censor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_censor);
         setControl();
         setEvent();
    }

    private void setEvent() {
        data_censor.add(new CensorModel("Bàn 1 (online)","Số lượng: 2 món","KH: Bùi Văn Huy","100.000đ"));
        data_censor.add(new CensorModel("Bàn 2","Số lượng: 10 món","KH: Nguyễn Hoàng Duy","1.500.000đ"));
        data_censor.add(new CensorModel("Online","Số lượng: 4 món","KH: Lê Minh Thành","525.000đ"));

        censorAdapter_censor = new CensorAdapter(CensorActivity.this, R.layout.item_invoice, data_censor);

        list_invoice.setAdapter(censorAdapter_censor);

        list_invoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CensorActivity.this,InvoiceActivity.class);
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
