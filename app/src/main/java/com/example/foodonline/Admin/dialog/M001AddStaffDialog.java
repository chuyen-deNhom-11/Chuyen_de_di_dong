package com.example.foodonline.Admin.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.foodonline.Admin.model.AccountEntity;
import com.example.foodonline.Admin.widget.ProgressLoading;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseDialog;
import com.example.foodonline.utils.Constant;
import com.example.foodonline.utils.firebase.FRealtimeRequest;

public class M001AddStaffDialog extends BaseDialog implements FRealtimeRequest.OnRealtimeCallBack {
    private static final String KEY_ADD_MEM = "KEY_ADD_MEM";
    private static final String KEY_SET_ID = "KEY_SET_ID";
    private ImageView ivBack;
    private TextView tvTitle;
    private EditText edtName, edtPhone, edtEmail, edtAddress;
    private Spinner spRole;
    private TextView tvAdd;

    public M001AddStaffDialog(@NonNull Context context, Object data) {
        super(context, data);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_add_staff_dialog;
    }

    @Override
    protected void initViews() {
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("Thêm nhân viên");

        edtName = findViewById(R.id.edt_name);
        spRole = findViewById(R.id.spinner_role);
        edtPhone = findViewById(R.id.edt_phone);
        edtEmail = findViewById(R.id.edt_email);
        edtAddress = findViewById(R.id.edt_address);
        tvAdd = findViewById(R.id.tv_add);

    }

    @Override
    protected void initListenner() {
        registerListenners(ivBack, tvAdd);
    }

    @Override
    protected void initComponent() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.item_text_spinner, Constant.ROLES);
        spRole.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                dismiss();
                break;
            case R.id.tv_add:
                if (edtName.getText().toString().isEmpty()){
                    edtName.setError("Không bỏ trống mục này");
                    return;
                }
                if (edtPhone.getText().toString().isEmpty()){
                    edtPhone.setError("Không bỏ trống mục này");
                    return;
                }
                if (edtEmail.getText().toString().isEmpty()){
                    edtEmail.setError("Không bỏ trống mục này");
                    return;
                }
                if (edtAddress.getText().toString().isEmpty()){
                    edtAddress.setError("Không bỏ trống mục này");
                    return;
                }

                addStaff(edtName.getText().toString(),
                        edtPhone.getText().toString(),
                        edtEmail.getText().toString(),
                        edtAddress.getText().toString(),
                        spRole.getSelectedItemPosition() + 2);
                break;
        }
    }

    private void addStaff(String name, String phone, String email, String address, int type) {
        ProgressLoading.show(mContext);
        AccountEntity entity = new AccountEntity(address, email, null, name, "12345678", phone, type + "");
        new FRealtimeRequest("Users")
                .method(FRealtimeRequest.METHOD_PUSH)
                .data(entity)
                .callRequest(KEY_ADD_MEM, this);
    }

    @Override
    public void onRealtimeSetSuccess(String tag, String key) {
        ProgressLoading.dismiss();
        dismiss();
        if (tag.equals(KEY_ADD_MEM)) {
            new FRealtimeRequest("Users/" + key + "id")
                    .method(FRealtimeRequest.METHOD_SET)
                    .data(key)
                    .callRequest(KEY_SET_ID, this);
        }
    }

    @Override
    public void onRealtimeSetFailed(String tag) {
        ProgressLoading.dismiss();
    }
}
