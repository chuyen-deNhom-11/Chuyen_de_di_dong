package com.example.foodonline.Adpter;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

public class ListComboHot extends RecyclerView.Adapter<ListComboHot.ViewHolder> {
    private ArrayList<ComboModel> list;
    private Context context;

    public ListComboHot(ArrayList<ComboModel> dataRoom) {
        this.list = dataRoom;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout image_combo;

        public ViewHolder(View convertView) {
            super(convertView);
            image_combo = convertView.findViewById(R.id.image_combo);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_combo_hot, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ComboModel comboModel = list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_combo_hot;
    }
}
