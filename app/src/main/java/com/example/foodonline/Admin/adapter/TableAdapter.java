package com.example.foodonline.Admin.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodonline.Admin.dialog.M002EditTableDialog;
import com.example.foodonline.Admin.model.TableEntity;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseHolder;
import com.example.foodonline.base.BaseRecylerAdapter;
import com.google.firebase.database.FirebaseDatabase;

public class TableAdapter extends BaseRecylerAdapter<TableAdapter.TableHolder, TableEntity> {
    private static final int STATE_AVAILABLE = 0;
    private static final int STATE_BOOKED = 1;
    private static final int STATE_USING = 2;

    public TableAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_table_admin;
    }

    @Override
    protected TableHolder getViewHolder(View v) {
        return new TableHolder(v);
    }

    public class TableHolder extends BaseHolder implements View.OnLongClickListener {
        private ImageView ivTable;
        private TextView tvTableNum;
        private TableEntity data;

        public TableHolder(@NonNull View itemView) {
            super(itemView);

        }

        @Override
        protected void initView(int position) {
            data = mListData.get(position);
            ivTable = findViewById(R.id.iv_table);
            tvTableNum = findViewById(R.id.tv_table_num);

            ivTable.setColorFilter(data.getStatus() == STATE_AVAILABLE ? mContext.getResources().getColor(R.color.colorGreen) :
                    data.getStatus() == STATE_BOOKED ? mContext.getResources().getColor(R.color.colorYellow) :
                            mContext.getResources().getColor(R.color.colorRed));


            tvTableNum.setText(String.format("Bàn %s", getAdapterPosition() + 1));

            View itemTable = findViewById(R.id.item_table);
            registerListenners(itemTable);
            itemTable.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.item_table) {
                M002EditTableDialog dialog = new M002EditTableDialog(mContext, data);
                dialog.show();
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (view.getId() == R.id.item_table) {
                AlertDialog dialog = new AlertDialog.Builder(mContext).create();
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setTitle("Alert");
                dialog.setMessage("Bạn có muốn xoá bàn này không?");
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", (dialogInterface, i) -> {
                    FirebaseDatabase.getInstance().getReference("Table/" + data.getKey()).removeValue().addOnSuccessListener(aVoid -> {
                        Toast.makeText(mContext, "Delete done", Toast.LENGTH_SHORT).show();
                    });
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Huỷ", (dialogInterface, i) -> {
                    Toast.makeText(mContext, "Huỷ", Toast.LENGTH_SHORT).show();
                });


                dialog.show();
            }
            return false;
        }
    }
}
