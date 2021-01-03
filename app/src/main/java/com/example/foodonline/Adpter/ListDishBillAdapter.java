package com.example.foodonline.Adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodonline.DataModel.BillModel;
import com.example.foodonline.DataModel.CartModel;
import com.example.foodonline.DataModel.DishModel;
import com.example.foodonline.R;
import com.example.foodonline.User.HistoryActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListDishBillAdapter extends ArrayAdapter<CartModel> {
    private Context context;
    private int resource;
    private List<CartModel> arrCustomer;
    String userId,sLayout;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public ListDishBillAdapter(Context context, int resource, ArrayList<CartModel> arrCustomer,String userId,String sLayout) {
        super(context, resource, arrCustomer);
        this.context = context;
        this.resource = resource;
        this.arrCustomer = arrCustomer;
        this.userId = userId;
        this.sLayout = sLayout;
    }

    private class ViewHolder {
        TextView name_price_dish, number_dish;
        Button btn_reduction, btn_increase;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dish_bill, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name_price_dish = convertView.findViewById(R.id.name_price_dish);
            viewHolder.number_dish = convertView.findViewById(R.id.number_dish);
            viewHolder.btn_increase = convertView.findViewById(R.id.btn_increase);
            viewHolder.btn_reduction = convertView.findViewById(R.id.btn_reduction);
            if (sLayout.equals("HistoryActivity")){
                viewHolder.btn_increase.setVisibility(View.GONE);
                viewHolder.btn_reduction.setVisibility(View.GONE);
            }else {
                viewHolder.btn_increase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = arrCustomer.get(position).getSoLuong();
                        arrCustomer.get(position).setSoLuong(++amount);
                        CartModel cartModel = new CartModel(arrCustomer.get(position).getNameFood(), amount, arrCustomer.get(position).getPrice());
                        mDatabase.child("Cart").child(userId).child("dish").child(arrCustomer.get(position).getIdDish()).setValue(cartModel);
                        viewHolder.number_dish.setText(cartModel.getSoLuong() + "");
                        viewHolder.btn_reduction.setEnabled(true);
                    }
                });
                viewHolder.btn_reduction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int amount = arrCustomer.get(position).getSoLuong();
                        arrCustomer.get(position).setSoLuong(--amount);
                        CartModel cartModel = new CartModel(arrCustomer.get(position).getNameFood(), amount, arrCustomer.get(position).getPrice());
                        mDatabase.child("Cart").child(userId).child("dish").child(arrCustomer.get(position).getIdDish()).setValue(cartModel);
                        viewHolder.number_dish.setText(cartModel.getSoLuong() + "");
                        if (amount == 1) {
                            viewHolder.btn_reduction.setEnabled(false);
                        }

                    }
                });
            }
            CartModel cartModel = arrCustomer.get(position);
            viewHolder.name_price_dish.setText(cartModel.getNameFood() + " - " + cartModel.getPrice());
            viewHolder.number_dish.setText(cartModel.getSoLuong() + "");
            if (cartModel.getSoLuong() == 1){
                viewHolder.btn_reduction.setEnabled(false);
            }
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}
