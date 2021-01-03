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
    private ClickLisener onClickLisener;
    public ListTableAdapter(ArrayList<TableModel>tableModels,ClickLisener onClickLisener){
        this.list = tableModels;
        this.onClickLisener = onClickLisener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent.getContext();
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_table, parent, false);
        return new ViewHolder(layout,onClickLisener);
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
            holder.name_table.setTextColor(ContextCompat.getColor(context,R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image_table;
        TextView name_table;
        ClickLisener onClickLisener;
        public ViewHolder(View convertView, ClickLisener onClickLisener) {
            super(convertView);
            image_table = convertView.findViewById(R.id.image_table);
            name_table = convertView.findViewById(R.id.name_table);
            this.onClickLisener = onClickLisener;
            convertView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickLisener.onClick(getAdapterPosition());
        }
    }
    public interface ClickLisener {
        void onClick(int posotion);
    }
}
