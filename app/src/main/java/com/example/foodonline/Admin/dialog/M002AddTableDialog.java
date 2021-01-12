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

public class M002AddTableDialog extends BaseDialog implements FRealtimeRequest.OnRealtimeCallBack {
    private static final String KEY_ADD_TABLE = "KEY_ADD_TABLE";
    private ImageView ivBack;
    private EditText edtTablePeople;
    private TextView tvTitle;

    public M002AddTableDialog(@NonNull Context context, Object data) {
        super(context, data);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_add_table_dialog;
    }

    @Override
    protected void initViews() {
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText("Thêm bàn ăn");

        ivBack.setVisibility(View.VISIBLE);
        edtTablePeople = findViewById(R.id.edt_people_number);

    }

    @Override
    protected void initListenner() {
        registerListenners(
                ivBack,
                findViewById(R.id.tv_add)
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
        if (view.getId() == R.id.iv_back) {
            dismiss();
        } else if (view.getId() == R.id.tv_add) {
            dismiss();
            ProgressLoading.show(mContext);
            new FRealtimeRequest("Table")
                    .method(FRealtimeRequest.METHOD_PUSH)
                    .data(new TableEntity(Integer.parseInt(edtTablePeople.getText().toString()), 0))
                    .callRequest(KEY_ADD_TABLE, this);
        }
    }

    @Override
    public void onRealtimeSetSuccess(String tag, String key) {
        ProgressLoading.dismiss();
        Toast.makeText(mContext, "Thêm bàn thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRealtimeSetFailed(String tag) {
        ProgressLoading.dismiss();
        Toast.makeText(mContext, "Thêm bàn thất bại", Toast.LENGTH_SHORT).show();

    }
}
