package com.example.foodonline.Adpter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.foodonline.DataModel.BookingTableModel;
import com.example.foodonline.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DepositMoneyAdapter extends ArrayAdapter<BookingTableModel> {
    private Context context;
    private int resource;
    private List<BookingTableModel> arrCustomer;
    String sLayout;
    FirebaseDatabase fData = FirebaseDatabase.getInstance();
    public DepositMoneyAdapter(@NonNull Context context, int resource, ArrayList<BookingTableModel> arrCustomer, String sLayout) {
        super(context, resource, arrCustomer);
        this.context = context;
        this.resource = resource;
        this.arrCustomer = arrCustomer;
        this.sLayout = sLayout;
    }
    private class ViewHover{
        TextView name_table,time,name,phone;
        LinearLayout ln_booking,ln_confirm;
        Button btn_cancel,btn_done,btn_confirm;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHover viewHover;
        if (convertView ==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_browse_table,parent,false);
            viewHover = new ViewHover();
            viewHover.name =convertView.findViewById(R.id.name);
            viewHover.name_table = convertView.findViewById(R.id.name_table);
            viewHover.time = convertView.findViewById(R.id.time);
            viewHover.phone  = convertView.findViewById(R.id.phone);
            viewHover.ln_booking = convertView.findViewById(R.id.ln_booking);
            viewHover.ln_confirm = convertView.findViewById(R.id.ln_confirm);
            viewHover.btn_cancel = convertView.findViewById(R.id.btn_cancel);
            viewHover.btn_done = convertView.findViewById(R.id.btn_done);
            viewHover.btn_confirm = convertView.findViewById(R.id.btn_confirm);
        }else{
            viewHover =(DepositMoneyAdapter.ViewHover) convertView.getTag();
        }
        BookingTableModel tableModel = arrCustomer.get(position);
        viewHover.name.setText(tableModel.getUserName());
        viewHover.name_table.setText(tableModel.getTableName());
        viewHover.time.setText(tableModel.getDateBooking()+" - "+tableModel.getTimeBooking());
        viewHover.phone.setText(tableModel.getPhoneNumber());
            if (sLayout.equals("BrowseTableFragment")){
            viewHover.btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Hủy "+arrCustomer.get(position).getTableName());
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fData.getReference().child("BookingTable").child(arrCustomer.get(position).getId()).removeValue();
                        }
                    });
                    builder.setNegativeButton("Không", null);
                    Dialog dialog = builder.create();
                    dialog.show();
                }
            });
            viewHover.btn_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Xác nhận "+arrCustomer.get(position).getTableName());
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fData.getReference().child("BookingTable").child(arrCustomer.get(position).getId()).child("status").setValue(1);
                        }
                    });
                    builder.setNegativeButton("Không", null);
                    Dialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
            if (tableModel.getStatus()==1 && sLayout.equals("HistoryActivity")){
                viewHover.ln_booking.setVisibility(View.GONE);
                viewHover.ln_confirm.setVisibility(View.GONE);
            }
        if (sLayout.equals("BrowseFragment")||sLayout.equals("HistoryActivity")){
            viewHover.ln_booking.setVisibility(View.GONE);
            viewHover.ln_confirm.setVisibility(View.VISIBLE);
            viewHover.btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Hủy "+arrCustomer.get(position).getTableName());
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            fData.getReference().child("BookingTable").child(arrCustomer.get(position).getId()).removeValue();
                        }
                    });
                    builder.setNegativeButton("Không", null);
                    Dialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
        return convertView;
    }
}
