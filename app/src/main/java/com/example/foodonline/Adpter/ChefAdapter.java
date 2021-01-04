package com.example.foodonline.Adpter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.foodonline.Chef.ChefActivity;
import com.example.foodonline.DataModel.ChefModel;
import com.example.foodonline.LoginActivity;
import com.example.foodonline.R;
import com.example.foodonline.SignInActivity;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        // Ánh xạ
        TextView txt_name_food = (TextView) convertView.findViewById(R.id.name_food);
        TextView txt_amount = (TextView) convertView.findViewById(R.id.amount);
        TextView txt_table = (TextView) convertView.findViewById(R.id.table);
        Button btn_cancel = (Button) convertView.findViewById(R.id.btn_cancel);
        Button btn_done_cook = (Button) convertView.findViewById(R.id.btn_done_cook);
        //Gán giá trị
        final ChefModel chefModel = data.get(position);
        txt_name_food.setText(chefModel.getNameFood());
        txt_amount.setText(chefModel.getAmount());
        txt_table.setText(chefModel.getTable());
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View alertLayout = inflater.inflate(R.layout.dialog_cancel, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(alertLayout);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Hủy", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        btn_done_cook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Món " + data.get(position).getNameFood() + " hoàn thành");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        data.remove(position);
                        notifyDataSetChanged();
                    }
                });

                Dialog dialog = builder.create();
                dialog.show();

            }
        });


        return convertView;
    }
}
