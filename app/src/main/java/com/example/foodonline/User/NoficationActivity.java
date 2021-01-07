package com.example.foodonline.User;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.Adpter.ListNofictionAdapter;
import com.example.foodonline.DataModel.NoficationModel;
import com.example.foodonline.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class NoficationActivity extends AppCompatActivity {
    ArrayList<NoficationModel> data;
    ListView list_item_nofication;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nofication);
        setTitle("Thông báo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list_item_nofication = findViewById(R.id.list_item_nofication);

        setItemNofication();
    }
    private void setItemNofication(){
        data = new ArrayList<>();


        data.add(new NoficationModel("a","b","c","1/1/2020",1));
        data.add(new NoficationModel("a","b","c","1/11/2020",0));
        ListNofictionAdapter nofictionAdapter = new ListNofictionAdapter(this,R.layout.activity_nofication,data);
        list_item_nofication.setAdapter(nofictionAdapter);
        list_item_nofication.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemNofication(position);
            }
        });
    }
    private void selectItemNofication(int position){
        if (data.get(position).getStatus() == 1){

        }
    }
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }
}
