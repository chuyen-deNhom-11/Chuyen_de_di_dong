package com.example.foodonline.Adpter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.foodonline.DataModel.BillModel;
import com.example.foodonline.R;

import java.util.ArrayList;

public class CensorAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<BillModel> data;
    String sTypeBooking,sLayout;

    public CensorAdapter(Context context, int layout, ArrayList<BillModel> data,String sLayout){
        this.context=context;
        this.layout=layout;
        this.data=data;
        this.sLayout = sLayout;
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
        LinearLayout layout_item_cencor= (LinearLayout) convertView.findViewById(R.id.layout_item_cencor);
        LinearLayout layout_cencor = convertView.findViewById(R.id.layout_cencor);
        //Gán giá trị
        BillModel billModel = data.get(position);
        if (billModel.getType() == 0){
            sTypeBooking = "Online";
        }else if (billModel.getType() == 1){
            sTypeBooking = billModel.getNameTable() +"(online)";
        }else {
            sTypeBooking = billModel.getNameTable();
        }
       if (sLayout.equals("PendingFragment")){
            if (billModel.getStatus()==5){
                layout_cencor.setBackground(ContextCompat.getDrawable(context,R.drawable.background_deposit));
                layout_item_cencor.setBackground(ContextCompat.getDrawable(context,R.drawable.background_deposit));
            }
            if (billModel.getStatus()==6){
                layout_cencor.setBackground(ContextCompat.getDrawable(context,R.drawable.background_cencor));
                layout_item_cencor.setBackground(ContextCompat.getDrawable(context,R.drawable.background_cencor));
            }
       }
       if (sLayout.equals("ApprovedFragment")){
           if (billModel.getStatus() == 1){
               layout_cencor.setBackground(ContextCompat.getDrawable(context,R.drawable.background_cencor));
               layout_item_cencor.setBackground(ContextCompat.getDrawable(context,R.drawable.background_cencor));
           }
           if (billModel.getStatus()==3){
               layout_cencor.setBackground(ContextCompat.getDrawable(context,R.drawable.background_deposit));
               layout_item_cencor.setBackground(ContextCompat.getDrawable(context,R.drawable.background_deposit));
           }
       }
        tv_type_booking.setText(sTypeBooking);
        tv_number_dish.setText(billModel.getAmount()+"");
        tv_oderer.setText(billModel.getName());
        tv_price.setText(billModel.getPrice());

        return convertView;
    }
}
