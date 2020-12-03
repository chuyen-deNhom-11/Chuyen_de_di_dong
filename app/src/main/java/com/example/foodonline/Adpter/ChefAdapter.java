package com.example.foodonline.Adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foodonline.DataModel.ChefModel;
import com.example.foodonline.R;

import java.util.ArrayList;

public class ChefAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<ChefModel> data;

    public ChefAdapter(Context context, int layout, ArrayList<ChefModel> data){
        this.context=context;
        this.layout=layout;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        // Ánh xạ
        TextView txt_name_food = (TextView) convertView.findViewById(R.id.name_food);
        TextView txt_amount = (TextView) convertView.findViewById(R.id.amount);
        TextView txt_table = (TextView) convertView.findViewById(R.id.table);
        //Gán giá trị
        ChefModel chefModel = data.get(position);
        txt_name_food.setText(chefModel.getNameFood());
        txt_amount.setText(chefModel.getAmount());
        txt_table.setText(chefModel.getTable());

        return convertView;
    }
}
