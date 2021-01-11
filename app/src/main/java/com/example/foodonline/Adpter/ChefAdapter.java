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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.foodonline.Chef.ChefActivity;
import com.example.foodonline.DataModel.ChefModel;
import com.example.foodonline.LoginActivity;
import com.example.foodonline.R;
import com.example.foodonline.SignInActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChefAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<ChefModel> data;
    String sTable,sReason,sLayout;
    FirebaseDatabase fData = FirebaseDatabase.getInstance();

    public ChefAdapter(Context context, int layout, ArrayList<ChefModel> data,String sLayout){
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
        txt_amount.setText(chefModel.getSoLuong()+"");
        if (chefModel.getType()==0){
            sTable = "Online";
        }if(chefModel.getType()==1){
            sTable = chefModel.getTable()+"(Online)";
        } else {
            sTable = chefModel.getTable();
        }
        txt_table.setText(sTable);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View alertLayout = inflater.inflate(R.layout.dialog_cancel, null)   ;
                EditText edt_other = (EditText) alertLayout.findViewById(R.id.edt_other);
                RadioButton rad_out_of_stock = alertLayout.findViewById(R.id.rad_out_of_stock);
                RadioButton rad_out_of_gas = alertLayout.findViewById(R.id.rad_out_of_gas);
                RadioButton rad_other = alertLayout.findViewById(R.id.rad_other);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(alertLayout);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (rad_out_of_stock.isChecked()){
                            sReason = rad_out_of_stock.getText().toString();
                        }
                        if (rad_out_of_gas.isChecked()){
                            sReason = rad_out_of_gas.getText().toString();
                        }
                        if (rad_other.isChecked()){
                            sReason = edt_other.getText().toString();
                        }
                        if (sReason == null || sReason.equals("")){
                            Toast.makeText(context,"Vui lòng chọn lý do",Toast.LENGTH_SHORT).show();
                        }else {
                            fData.getReference().child("Bill").child(data.get(position).
                                    getIdBill()).child("Dish").child(data.get(position).getKeyDish()).child("status").setValue(1);
                            fData.getReference().child("Bill").child(data.get(position).getIdBill()).child("Dish")
                                            .child(data.get(position).getKeyDish()).child("reason").setValue(sReason);
                            data.remove(position);
                            notifyDataSetChanged();
                        }

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
                        fData.getReference().child("Bill").child(data.get(position).getIdBill()).child("Dish").
                                child(data.get(position).getKeyDish()).child("status").setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                boolean check = false;
                                String id = data.get(position).getIdBill();
                                data.remove(position);
                                notifyDataSetChanged();
                                for (int i =0 ;i <data.size();i++){
                                    if (data.get(i).getIdBill().equals(id)){
                                        check = true;
                                    }
                                }if (check == false){
                                    fData.getReference().child("Bill").child(id).child("status").setValue(3);
                                }
                            }
                        });

                    }
                });

                Dialog dialog = builder.create();
                dialog.show();

            }
        });
        return convertView;
    }
}
