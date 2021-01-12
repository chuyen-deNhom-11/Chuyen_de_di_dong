package com.example.foodonline.Admin.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.foodonline.Admin.model.BillEntity;
import com.example.foodonline.Admin.model.DishEntity;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseHolder;
import com.example.foodonline.base.BaseRecylerAdapter;

import java.util.ArrayList;
import java.util.List;


public class BillAdapter extends BaseRecylerAdapter<BillAdapter.BillHolder, BillEntity> {
    public BillAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_bill;
    }

    @Override
    protected BillHolder getViewHolder(View v) {
        return new BillHolder(v);
    }

    public class BillHolder extends BaseHolder {
        private TextView tvName, tvAddress, tvPhoneNo, tvFood, tvTotalPrice;
        private BillEntity mData;

        public BillHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(int position) {
            mData = mListData.get(position);
            tvName = findViewById(R.id.tv_name);
            tvAddress = findViewById(R.id.tv_address);
            tvPhoneNo = findViewById(R.id.tv_phone_no);
            tvFood = findViewById(R.id.tv_food);
            tvTotalPrice = findViewById(R.id.tv_total_price);

            tvName.setText(mData.getName());
            tvAddress.setText(mData.getAdress());
            tvPhoneNo.setText(mData.getPhone() + "");

            List<DishEntity> listfood = mData.getDish();
            String dish = "Các món ăn:";
            for (DishEntity food :
                    listfood) {
                dish += ("\n"+"-" + food.getNameFood() + "-" + food.getSoLuong() + "x" + food.getPrice());
            }
            tvFood.setText(dish);
            tvTotalPrice.setText("Tổng="+mData.getPrice());
        }
    }
}
