package com.example.foodonline.Admin.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.foodonline.Admin.model.AccountEntity;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseHolder;
import com.example.foodonline.base.BaseRecylerAdapter;
import com.example.foodonline.utils.Constant;

public class StaffAdapter extends BaseRecylerAdapter<StaffAdapter.StaffHolder, AccountEntity> {
    public static final String KEY_CLICK_STAFF = "KEY_CLICK_STAFF";

    public StaffAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_staff;
    }

    @Override
    protected StaffHolder getViewHolder(View v) {
        return new StaffHolder(v);
    }

    public class StaffHolder extends BaseHolder {
        private ImageView ivAvatar;
        private TextView tvStaffName, tvJob;
        private AccountEntity data;
        private View itemStaff;

        public StaffHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(int position) {
            data = mListData.get(position);
            ivAvatar = findViewById(R.id.iv_atatar);
            tvStaffName = findViewById(R.id.tv_staff_name);
            tvJob = findViewById(R.id.tv_job);
            itemStaff = findViewById(R.id.item_staff);

            String type = data.getType();
            Glide.with(mContext)
                    .load(type.equals(Constant.TYPE_MODERATOR) ? R.drawable.ic_kiem_duyet_vien :
                            type.equals(Constant.TYPE_ATTENDER) ? R.drawable.ic_phuc_vu
                                    : R.drawable.ic_chef)
                    .circleCrop()
                    .into(ivAvatar);
            tvStaffName.setText(data.getName());
            String job = type.equals(Constant.TYPE_MODERATOR) ? Constant.MODERATOR :
                    type.equals(Constant.TYPE_ATTENDER) ? Constant.ATTENDER
                            : Constant.CHIEF;
            tvJob.setText(job);

            registerListenners(itemStaff);

        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.item_staff){
                mCallBack.callBack(KEY_CLICK_STAFF, data);
            }
        }
    }

}
