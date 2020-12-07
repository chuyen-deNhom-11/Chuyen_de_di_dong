package com.example.foodonline.Adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.DataModel.ComboModel;
import com.example.foodonline.DataModel.DishModel;
import com.example.foodonline.R;
import com.example.foodonline.User.ListDishComboActivity;

import java.util.ArrayList;
import java.util.List;

public class LishItemComboAdapter extends RecyclerView.Adapter<LishItemComboAdapter.ViewHolder> {
    private ArrayList<ComboModel> list;
    private Context context;
   private OnComboLisener mOnComboLisener;

    public LishItemComboAdapter(ArrayList<ComboModel> dataRoom, OnComboLisener onComboLisener) {
        this.list = dataRoom;
        this.mOnComboLisener = onComboLisener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout image_combo;
        TextView name_combo, total_dish, price_combo;
        OnComboLisener onComboLisener;

        public ViewHolder(View convertView, OnComboLisener onComboLisener) {
            super(convertView);
            image_combo = convertView.findViewById(R.id.image_combo);
            name_combo = convertView.findViewById(R.id.name_combo);
            total_dish = convertView.findViewById(R.id.total_dish);
            price_combo = convertView.findViewById(R.id.price_combo);
            this.onComboLisener = onComboLisener;
            convertView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onComboLisener.onComboClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_dish_combo, parent, false);
        return new ViewHolder(layout, mOnComboLisener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ComboModel comboModel = list.get(position);
        holder.name_combo.setText(comboModel.getNameCombo());
        holder.total_dish.setText(comboModel.getTotalCombo() + " Món");
        holder.price_combo.setText("Giá: " + comboModel.getPriceCombo());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_dish_combo;
    }

    public interface OnComboLisener {
        void onComboClick(int position);
    }
}
