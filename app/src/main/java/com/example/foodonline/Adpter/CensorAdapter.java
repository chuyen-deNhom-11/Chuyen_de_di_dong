package com.example.foodonline.Adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.foodonline.DataModel.CensorModel;
import com.example.foodonline.R;

import java.util.ArrayList;

public class CensorAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<CensorModel> data;

    public CensorAdapter(Context context, int layout, ArrayList<CensorModel> data){
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
        TextView tv_type_booking = (TextView) convertView.findViewById(R.id.tv_type_booking);
        TextView tv_number_dish = (TextView) convertView.findViewById(R.id.tv_number_dish);
        TextView tv_oderer = (TextView) convertView.findViewById(R.id.tv_oderer);
        TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price);
        //Gán giá trị
        CensorModel censorModel = data.get(position);
        tv_type_booking.setText(censorModel.getTv_type_booking());
        tv_number_dish.setText(censorModel.getTv_number_dish());
        tv_oderer.setText(censorModel.getTv_oderer());
        tv_price.setText(censorModel.getTv_price());

        return convertView;
    }
}
