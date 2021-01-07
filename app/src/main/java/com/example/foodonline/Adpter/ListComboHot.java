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

import com.bumptech.glide.Glide;
import com.example.foodonline.DataModel.ComboModel;
import com.example.foodonline.DataModel.DishModel;
import com.example.foodonline.R;

import java.util.ArrayList;
import java.util.List;

public class ListComboHot extends RecyclerView.Adapter<ListComboHot.ViewHolder> {
    private ArrayList<ComboModel> list;
    private Context context;
    private OnComboHotLisener mOncomboHot;

    public ListComboHot(ArrayList<ComboModel> dataRoom,OnComboHotLisener mOncomboHot) {
        this.list = dataRoom;
        this.mOncomboHot = mOncomboHot;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image_combo;
        OnComboHotLisener onComboHotLisener;
        public ViewHolder(View convertView,OnComboHotLisener onComboHotLisener) {
            super(convertView);
            image_combo = convertView.findViewById(R.id.img_combo);
            this.onComboHotLisener = onComboHotLisener;
            convertView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            onComboHotLisener.onComboHotClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_combo_hot, parent, false);
        return new ViewHolder(layout,mOncomboHot);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ComboModel comboModel = list.get(position);
        if (comboModel.getImageCombo()!=null){
            Glide.with(context).load(comboModel.getImageCombo()).into(holder.image_combo);
        }
    }

    @Override
    public int getItemCount() {
            return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_combo_hot;
    }
    public interface OnComboHotLisener {
        void onComboHotClick(int position);
    }
}
