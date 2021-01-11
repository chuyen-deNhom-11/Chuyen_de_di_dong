package com.example.foodonline.Adpter;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodonline.DataModel.BillModel;
import com.example.foodonline.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter<BillModel> {
    private Context context;
    private int resource;
    private List<BillModel> arrayList;
    String sTitle,sStatus,dateString;
    SimpleDateFormat formatter = null;
    public HistoryAdapter(@NonNull Context context, int resource,ArrayList<BillModel> arrayList) {
        super(context, resource,arrayList);
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }
    private class ViewHover{
        TextView tv_title,tv_time,tv_status;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHover viewHolder;
        Collections.sort(arrayList, new Comparator<BillModel>() {
            @Override
            public int compare(BillModel sv1, BillModel sv2) {
                if (sv1.getTime() < sv2.getTime()) {
                    return 1;
                } else {
                    if (sv1.getTime() == sv2.getTime()) {
                        return 0;
                    } else {
                        return -1;
                    }
                }

            }
        });
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_history,parent,false);
            viewHolder = new ViewHover();
            viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
            viewHolder.tv_time = convertView.findViewById(R.id.tv_time);
            viewHolder.tv_status = convertView.findViewById(R.id.tv_status);

            BillModel bill = arrayList.get(position);
            if (bill.getStatus()==5){
                sStatus = "Yêu cầu đặt cọc";
            }else if(bill.getStatus() ==0){
                sStatus = "Chờ duyệt";
            }else if (bill.getStatus() == 1|| bill.getStatus() == 2){
                sStatus = "Đã nhận đơn hàng";
            }
            else if (bill.getStatus()==4){
                sStatus = "đã giao hàng";
            }else{
                sStatus = "đã đặt hàng";
            }
            viewHolder.tv_status.setText(sStatus);
            if (bill.getType() == 0){
                sTitle = "Đặt giao tại nhà";
            }else {
                sTitle = "Đặt sử dụng tại quán";
            }
            viewHolder.tv_title.setText(sTitle);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                formatter = new SimpleDateFormat("dd/MM/yyyy");
                dateString = formatter.format(new Date(bill.getTime()));
            }
            viewHolder.tv_time.setText(dateString);
        }else {
            viewHolder = (HistoryAdapter.ViewHover) convertView.getTag();
        }
        return convertView;
    }
}
