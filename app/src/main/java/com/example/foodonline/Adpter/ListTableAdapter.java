package com.example.foodonline.Adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.DataModel.TableModel;
import com.example.foodonline.R;

import java.util.ArrayList;

public class ListTableAdapter extends RecyclerView.Adapter<ListTableAdapter.ViewHolder> {
    private ArrayList<TableModel> list;
    private Context context;
    public ListTableAdapter(ArrayList<TableModel>tableModels){
        this.list = tableModels;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_table, parent, false);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TableModel tableModel = list.get(position);
        holder.name_table.setText(tableModel.getNameTable());
        if (tableModel.getStatus() == 1){
            holder.image_table.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_table_1));
            holder.name_table.setTextColor(ContextCompat.getColor(context,R.color.colorYellow));
        }
        else if (tableModel.getStatus() ==2){
            holder.image_table.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_table_2));
            holder.name_table.setTextColor(ContextCompat.getColor(context,R.color.Red));
        }
        else {
            holder.image_table.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_food));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_table;
        TextView name_table;

        public ViewHolder(View convertView) {
            super(convertView);
            image_table = convertView.findViewById(R.id.image_table);
            name_table = convertView.findViewById(R.id.name_table);
        }
    }
}
