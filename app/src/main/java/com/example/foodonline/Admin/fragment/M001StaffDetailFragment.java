package com.example.foodonline.Admin.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodonline.Admin.model.AccountEntity;
import com.example.foodonline.Admin.model.ScheduleEntity;
import com.example.foodonline.Admin.widget.ProgressLoading;
import com.example.foodonline.App;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseFragment;
import com.example.foodonline.utils.Constant;
import com.example.foodonline.utils.firebase.FRealtimeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class M001StaffDetailFragment extends BaseFragment implements FRealtimeRequest.OnRealtimeCallBack {
    private static final String KEY_GET_SHEDULE = "KEY_GET_SHEDULE";
    private static final String KEY_SAVE_SCHEDULE = "KEY_SAVE_SCHEDULE";
    private final CheckBox[][] checkBoxes = new CheckBox[7][3];
    private TextView tvName, tvRole, tvPhone, tvEmail, tvAddress, tvSave;
    private LinearLayout lnDay;
    private AccountEntity staff;
    private ImageView ivAvt, ivBack;
    private DataSnapshot mCurrentData;

    @Override
    protected void initViews() {
        ivAvt = findViewById(R.id.iv_atatar);
        ivBack = findViewById(R.id.iv_back);
        tvName = findViewById(R.id.tv_name);
        tvRole = findViewById(R.id.tv_role);
        tvPhone = findViewById(R.id.tv_phone);
        tvEmail = findViewById(R.id.tv_email);
        tvAddress = findViewById(R.id.tv_address);

        tvSave = findViewById(R.id.tv_save);
        lnDay = findViewById(R.id.ln_days);


        addViews();
    }

    private void addViews() {
        lnDay.removeAllViews();
        for (int i = 0; i < Constant.DAYS_IN_WEEK.length; i++) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_day, null);
            TextView tvDay = itemView.findViewById(R.id.tv_day);
            tvDay.setText(Constant.DAYS_IN_WEEK[i]);

            CheckBox rdShift1 = itemView.findViewById(R.id.rd_shift_1);
            CheckBox rdShift2 = itemView.findViewById(R.id.rd_shift_2);
            CheckBox rdShift3 = itemView.findViewById(R.id.rd_shift_3);

            checkBoxes[i][0] = rdShift1;
            checkBoxes[i][1] = rdShift2;
            checkBoxes[i][2] = rdShift3;

            lnDay.addView(itemView);
        }
    }

    @Override
    protected void initListenners() {
        registerListenner(ivBack, tvSave);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                navController.popBackStack();
                break;
            case R.id.tv_save:
                saveSchedule();
                break;
        }
    }

    private void saveSchedule() {
        ProgressLoading.show(mContext);
        deleteOldData();
        for (int i = 0; i < checkBoxes.length; i++) {
            for (int j = 0; j < checkBoxes[0].length; j++) {
                if (checkBoxes[i][j].isChecked()) {
                    addNewSchedule(i + 2, j + 1, staff.getId(), staff.getName(), staff.getType());
                }
                if (i == checkBoxes.length-1&& j == checkBoxes[0].length-1){
                    navController.popBackStack();
                    Toast.makeText(mContext, "Thay đổi thành công!!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private void addNewSchedule(int day, int shift, String id, String name, String type) {
        new FRealtimeRequest("work_schedule")
                .method(FRealtimeRequest.METHOD_PUSH)
                .data(new ScheduleEntity(day, shift, id, name, type))
                .callRequest(KEY_SAVE_SCHEDULE, this);
    }

    private void deleteOldData() {
        for (DataSnapshot child :
                mCurrentData.getChildren()) {
            child.getRef().removeValue();
        }
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initData() {
        staff = App.getInstance().getStorage().getSavedStaff();
        tvName.setText(staff.getName());
        tvAddress.setText(staff.getAdress());
        tvEmail.setText(staff.getEmail());
        tvPhone.setText(staff.getNumberPhone());

        String type = staff.getType();
        Glide.with(mContext)
                .load(type.equals(Constant.TYPE_MODERATOR) ? R.drawable.ic_kiem_duyet_vien :
                        type.equals(Constant.TYPE_ATTENDER) ? R.drawable.ic_phuc_vu
                                : R.drawable.ic_chef)
                .circleCrop()
                .into(ivAvt);
        String job = type.equals(Constant.TYPE_MODERATOR) ? Constant.MODERATOR :
                type.equals(Constant.TYPE_ATTENDER) ? Constant.ATTENDER
                        : Constant.CHIEF;
        tvRole.setText(job);

        getWorkShedule();
    }

    private void getWorkShedule() {
        ProgressLoading.show(mContext);
        new FRealtimeRequest("work_schedule")
                .query("staff_id", staff.getId())
                .callRequest(KEY_GET_SHEDULE, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_staff_detail;
    }


    @Override
    public void onRealtimeUpdate(String tag, DataSnapshot data) {
        mCurrentData = data;
        ProgressLoading.dismiss();
        if (tag.equals(KEY_GET_SHEDULE)) {
            for (DataSnapshot childData :
                    data.getChildren()) {
                ScheduleEntity entity = childData.getValue(ScheduleEntity.class);
                checkBoxes[entity.getDay() - 2][entity.getShift() - 1].setChecked(true);
            }
        }
    }

    @Override
    public void onRealtimeSetSuccess(String tag, String key) {
        ProgressLoading.dismiss();
        if (tag.equals(KEY_SAVE_SCHEDULE)) {
        }
    }

    @Override
    public void onRealtimeSetFailed(String tag) {
        ProgressLoading.dismiss();
        Toast.makeText(mContext, "Some thing went wrong!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRealtimeError(String tag, DatabaseError databaseError) {
        ProgressLoading.dismiss();
        Toast.makeText(mContext, "Some thing went wrong!!", Toast.LENGTH_SHORT).show();
    }
}
