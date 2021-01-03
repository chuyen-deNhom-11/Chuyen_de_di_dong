package com.example.foodonline.Adpter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.foodonline.DataModel.DishModel;
import com.example.foodonline.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListDishComboAdapter extends ArrayAdapter<DishModel> {
    private Context context;
    private int resource;
    private List<DishModel> arrCustomer;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    public ListDishComboAdapter(Context context, int resource, ArrayList<DishModel> arrCustomer) {
        super(context, resource, arrCustomer);
        this.context = context;
        this.resource = resource;
        this.arrCustomer = arrCustomer;
    }
    private class ViewHolder {
        TextView name_dish, tv_price;
        ImageView img_dish;
        CheckBox cb_dish;
    }

    @NonNull
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_dish_combo, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.cb_dish = view.findViewById(R.id.cb_dish);
            viewHolder.img_dish = view.findViewById(R.id.img_dish);
            viewHolder.name_dish = view.findViewById(R.id.name_dish);
            viewHolder.tv_price = view.findViewById(R.id.tv_price);

            final DishModel dishModel = arrCustomer.get(position);
            viewHolder.tv_price.setText("Giá: "+dishModel.getPrice());
            viewHolder.name_dish.setText(dishModel.getName());
            viewHolder.cb_dish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                    dishModel.setCheck(check);
                    notifyDataSetChanged();
                }
            });
            Glide.with(context).load(dishModel.getImage()).into(viewHolder.img_dish);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
       return view;
    }
}
