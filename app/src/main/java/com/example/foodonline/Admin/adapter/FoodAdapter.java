package com.example.foodonline.Admin.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.foodonline.Admin.model.FoodEntity;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseHolder;
import com.example.foodonline.base.BaseRecylerAdapter;

public class FoodAdapter extends BaseRecylerAdapter<FoodAdapter.FoodHolder, FoodEntity> {
    public static final String KEY_LONG_CLICK_ITEM = "KEY_LONG_CLICK_ITEM";

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
        notifyItemChanged(mListData.size() - 1);
    }

    public void showCheckBox(boolean visible) {
        for (FoodEntity food :
                mListData) {
            food.setVisible(visible);
        }
        notifyDataSetChanged();
    }


    public class FoodHolder extends BaseHolder implements View.OnLongClickListener {
        private FoodEntity data;
        private CheckBox checkBox;

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
            findViewById(R.id.item_dish).setOnLongClickListener(this);

            checkBox = findViewById(R.id.checkbox);
            checkBox.setVisibility(data.isVisible() ? View.VISIBLE : View.GONE);

            checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
                data.setCheck(b);
            });
        }


        @Override
        public boolean onLongClick(View view) {
            if (view.getId() == R.id.item_dish) {
                for (FoodEntity item :
                        mListData) {
                    item.setVisible(true);
                }
                notifyDataSetChanged();
                mCallBack.callBack(KEY_LONG_CLICK_ITEM, null);
                return true;
            }
            return false;
        }
    }
}
