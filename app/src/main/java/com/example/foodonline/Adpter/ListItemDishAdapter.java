package com.example.foodonline.Adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.example.foodonline.DataModel.DishModel;
import com.example.foodonline.R;

import java.util.ArrayList;
import java.util.List;

public class ListItemDishAdapter extends ArrayAdapter<DishModel> {
    private Context context;
    private int resource;
    private List<DishModel> arrCustomer;

    public ListItemDishAdapter(Context context, int resource, ArrayList<DishModel> arrCustomer) {
        super(context, resource, arrCustomer);
        this.context = context;
        this.resource = resource;
        this.arrCustomer = arrCustomer;
    }

    private class ViewHolder{
        TextView name_dish, price;
        ImageView img_dish;
        Button btn_order;
    }

    @NonNull
    @Override
    public View getView(int position,  View view,  ViewGroup parent) {

        ViewHolder viewHolder;

        if(view == null){
            view  = LayoutInflater.from(context).inflate(R.layout.item_dish,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.btn_order = view.findViewById(R.id.btn_order);
            viewHolder.img_dish = view.findViewById(R.id.img_dish);
            viewHolder.name_dish = view.findViewById(R.id.name_dish);
            viewHolder.price = view.findViewById(R.id.price);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        DishModel dishModel = arrCustomer.get(position);
        viewHolder.price.setText(dishModel.getPrice());
        viewHolder.name_dish.setText(dishModel.getName());

        return view;
    }
}
