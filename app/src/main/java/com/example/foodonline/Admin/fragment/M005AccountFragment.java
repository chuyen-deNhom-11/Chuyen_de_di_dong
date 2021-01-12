package com.example.foodonline.Admin.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodonline.Admin.dialog.M005ChangePassDialog;
import com.example.foodonline.Admin.event.OnActionCallBack;
import com.example.foodonline.Admin.model.ProfileEntity;
import com.example.foodonline.App;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseFragment;
import com.example.foodonline.utils.firebase.FRealtimeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


public class M005AccountFragment extends BaseFragment implements FRealtimeRequest.OnRealtimeCallBack, OnActionCallBack {


    public static final String TAG = M005AccountFragment.class.getName();
    private static final String KEY_GET_USER_INFO = "KEY_GET_USER_INFO";
    private TextView tvChangePass;
    private TextView tvName, tvEmail, tvPhoneNumber, tvAdress;
    private ProfileEntity info;
    private TextView tvLogout;


    @Override
    protected void initViews() {
        tvChangePass = findViewById(R.id.tv_change_pass);
        tvLogout = findViewById(R.id.tv_LogOut);
        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvPhoneNumber = findViewById(R.id.tv_phone_no);
        tvAdress = findViewById(R.id.tv_adress);
    }

    @Override
    protected void initListenners() {
        registerListenner(tvChangePass, tvLogout);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_change_pass) {
            M005ChangePassDialog dialog = new M005ChangePassDialog(mContext, info);
            dialog.setCallback(this);
            dialog.show();
        } else if (view.getId() == R.id.tv_LogOut) {
            getActivity().finish();
        }
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initData() {
        if (App.getInstance().getStorage().getUid() == null) {
            Toast.makeText(mContext, "Phiên đăng nhập hết hạn", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
        new FRealtimeRequest("Users/" + App.getInstance().getStorage().getUid())
                .callRequest(KEY_GET_USER_INFO, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tai_khoan;
    }

    @Override
    public void onRealtimeUpdate(String tag, DataSnapshot data) {
        if (tag.equals(KEY_GET_USER_INFO)) {
            info = data.getValue(ProfileEntity.class);
            tvAdress.setText(info.getAdress());
            tvEmail.setText(info.getEmail());
            tvName.setText(info.getName());
            tvPhoneNumber.setText(info.getPhoneNumber());
        }
    }

    @Override
    public void onRealtimeError(String tag, DatabaseError databaseError) {

    }

    @Override
    public void callBack(String key, Object data) {
        getActivity().finish();
    }
}