package com.example.foodonline.Admin.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.Admin.adapter.StaffAdapter;
import com.example.foodonline.Admin.dialog.M001AddStaffDialog;
import com.example.foodonline.Admin.model.AccountEntity;
import com.example.foodonline.Admin.widget.ProgressLoading;
import com.example.foodonline.App;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseFragment;
import com.example.foodonline.base.BaseRecylerAdapter;
import com.example.foodonline.utils.Constant;
import com.example.foodonline.utils.firebase.FRealtimeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class M001ListStaffFragment extends BaseFragment implements FRealtimeRequest.OnRealtimeCallBack, BaseRecylerAdapter.AdapterCallBack {
    private static final String KEY_GET_STAFF = "KEY_GET_STAFF";


    private TextView tvAttender, tvChief, tvModerator;
    private TextView tvEmpty;
    private RecyclerView rvStaff;
    private StaffAdapter staffAdapter;
    private ImageView ivEdit,ivAdd;

    @Override
    protected void initViews() {
        tvAttender = findViewById(R.id.tv_attender);
        tvChief = findViewById(R.id.tv_chief);
        tvModerator = findViewById(R.id.tv_moderator);
        tvEmpty = findViewById(R.id.tv_empty);
        rvStaff = findViewById(R.id.rv_staff);
        rvStaff.setLayoutManager(new LinearLayoutManager(mContext));
        ivEdit = findViewById(R.id.iv_edit);
        ivAdd = findViewById(R.id.iv_add);
        findViewById(R.id.iv_back).setVisibility(View.GONE);
    }

    @Override
    protected void initListenners() {
        registerListenner(tvAttender, tvChief, tvModerator, ivEdit, ivAdd);
    }

    @Override
    protected void initComponent() {
        staffAdapter = new StaffAdapter(mContext);
        rvStaff.setAdapter(staffAdapter);
        staffAdapter.setCallBack(this);
    }

    @Override
    protected void initData() {
        changeTabAt(R.id.tv_attender, R.id.tv_chief, R.id.tv_moderator);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list_staff;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_attender:
                changeTabAt(R.id.tv_attender, R.id.tv_chief, R.id.tv_moderator);
                break;

            case R.id.tv_chief:
                changeTabAt(R.id.tv_chief, R.id.tv_attender, R.id.tv_moderator);
                break;

            case R.id.tv_moderator:
                changeTabAt(R.id.tv_moderator, R.id.tv_chief, R.id.tv_attender);
                break;

            case R.id.iv_edit:
                navController.navigate(R.id.action_to_schedule);
                break;

            case R.id.iv_add:
                showAddDialog();
                break;

        }
    }

    private void showAddDialog() {
        M001AddStaffDialog dialog = new M001AddStaffDialog(mContext, null);
        dialog.show();
    }

    private void changeTabAt(int... ids) {
        findViewById(ids[0]).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        findViewById(ids[1]).setBackgroundColor(getResources().getColor(R.color.colorBlueDark));
        findViewById(ids[2]).setBackgroundColor(getResources().getColor(R.color.colorBlueDark));
        String type = ids[0] == R.id.tv_attender ? Constant.TYPE_ATTENDER
                : ids[0] == R.id.tv_chief ? Constant.TYPE_CHIEF
                : Constant.TYPE_MODERATOR;

        getListStaff(type);
    }

    // Lấy danh sách nhân viên
    private void getListStaff(String type) {
        ProgressLoading.show(mContext);
        new FRealtimeRequest("Users")
                .query("type", type)
                .callRequest(KEY_GET_STAFF, this);
    }

    //lấy data từ firebase về -> callback dữ liệu về đây
    @Override
    public void onRealtimeUpdate(String tag, DataSnapshot data) {
        ProgressLoading.dismiss();
        if (tag.equals(KEY_GET_STAFF)) {
            ArrayList<AccountEntity> listAcc = new ArrayList<>();

            for (DataSnapshot singleData :
                    data.getChildren()) {
                AccountEntity child = singleData.getValue(AccountEntity.class);
                child.setId(singleData.getKey());
                listAcc.add(child);
            }
            notifyEmpty(listAcc.size() == 0);
            staffAdapter.submitList(listAcc);
        }
    }

    @Override
    public void onRealtimeError(String tag, DatabaseError databaseError) {
        ProgressLoading.dismiss();
    }

    private void notifyEmpty(boolean isEmpty) {
        tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        rvStaff.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }


    @Override
    public void callBack(String key, Object data) {
        if (key.equals(StaffAdapter.KEY_CLICK_STAFF)) {
            navController.navigate(R.id.action_to_staff_detail);
            App.getInstance().getStorage().setSavedStaff((AccountEntity)data);
        }
    }
}
