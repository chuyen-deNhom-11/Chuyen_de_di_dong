package com.example.foodonline.Admin.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodonline.Admin.model.TableEntity;
import com.example.foodonline.Admin.widget.ProgressLoading;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseDialog;
import com.example.foodonline.utils.firebase.FRealtimeRequest;

public class M002EditTableDialog extends BaseDialog<TableEntity> implements FRealtimeRequest.OnRealtimeCallBack {
    private static final String KEY_UPDATE_TABLE = "KEY_UPDATE_TABLE";
    private TextView tvTitle;
    private ImageView ivBack;
    private EditText edtPeopleNum;
    private View tvEdit;

    public M002EditTableDialog(@NonNull Context context, TableEntity data) {
        super(context, data);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_edit_table_dialog;
    }

    @Override
    protected void initViews() {
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("Chỉnh sửa bàn ăn");
        tvEdit = findViewById(R.id.tv_edit);

        ivBack = findViewById(R.id.iv_back);
        ivBack.setVisibility(View.VISIBLE);
        edtPeopleNum = findViewById(R.id.edt_people_number);
    }

    @Override
    protected void initListenner() {
        registerListenners(
                ivBack,
                tvEdit
        );
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_edit) {
            ProgressLoading.show(mContext);
            data.setNumberPeople(Integer.parseInt(edtPeopleNum.getText().toString()));
            new FRealtimeRequest("Table/" + data.getKey())
                    .method(FRealtimeRequest.METHOD_SET)
                    .data(data)
                    .callRequest(KEY_UPDATE_TABLE, this);
            dismiss();
        } else if (view.getId() == R.id.iv_back) {
            dismiss();
        }
    }

    @Override
    public void onRealtimeSetSuccess(String tag, String key) {
        ProgressLoading.dismiss();
        Toast.makeText(mContext, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRealtimeSetFailed(String tag) {
        ProgressLoading.dismiss();
        Toast.makeText(mContext, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
    }
}
