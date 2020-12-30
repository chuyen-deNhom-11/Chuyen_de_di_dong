package com.example.foodonline.Admin.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.foodonline.Admin.model.FoodEntity;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseHolder;
import com.example.foodonline.base.BaseRecylerAdapter;

public class FoodAdapter extends BaseRecylerAdapter<FoodAdapter.FoodHolder, FoodEntity> {
    public FoodAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_dish_staff;
    }

    @Override
    protected FoodHolder getViewHolder(View v) {
        return new FoodHolder(v);
    }

    public void add(FoodEntity entity) {
        mListData.add(entity);
        notifyItemChanged(mListData.size()-1);
    }


    public class FoodHolder extends BaseHolder {
        private FoodEntity data;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(int position) {
            data = mListData.get(position);
            ((TextView) findViewById(R.id.tv_food_name)).setText(data.getName());
            ((TextView) findViewById(R.id.tv_food_price)).setText(data.getPrice());
            ((TextView) findViewById(R.id.tv_food_type)).setText(data.getType());
            Glide.with(mContext)
                    .load(data.getImage())
                    .into((ImageView) findViewById(R.id.iv_food_image));
        }

    }
}
