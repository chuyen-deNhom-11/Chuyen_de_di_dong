package com.example.foodonline.Adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodonline.DataModel.BillModel;
import com.example.foodonline.DataModel.DishModel;
import com.example.foodonline.R;

import java.util.ArrayList;
import java.util.List;

public class ListDishBillAdapter extends ArrayAdapter<BillModel> {
    private Context context;
    private int resource;
    private List<BillModel> arrCustomer;

    public ListDishBillAdapter(Context context, int resource, ArrayList<BillModel> arrCustomer) {
        super(context, resource, arrCustomer);
        this.context = context;
        this.resource = resource;
        this.arrCustomer = arrCustomer;
    }

    private class ViewHolder {
        TextView name_price_dish, number_dish;
        Button btn_reduction, btn_increase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dish_bill, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name_price_dish = convertView.findViewById(R.id.name_price_dish);
            viewHolder.number_dish = convertView.findViewById(R.id.number_dish);
            viewHolder.btn_increase = convertView.findViewById(R.id.btn_increase);
            viewHolder.btn_reduction = convertView.findViewById(R.id.btn_reduction);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BillModel billModel = arrCustomer.get(position);
        viewHolder.name_price_dish.setText(billModel.getNameDish() + " - " + billModel.getPrice());
        viewHolder.number_dish.setText(billModel.getAmount() + "");
        return convertView;
    }
}
