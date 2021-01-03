package com.example.foodonline.Admin.dialog;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodonline.Admin.model.CombooEntity;
import com.example.foodonline.Admin.widget.ProgressLoading;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseDialog;
import com.example.foodonline.utils.firebase.FRealtimeRequest;
import com.example.foodonline.utils.firebase.FStoreRequest;

public class M003AddCombooDialog extends BaseDialog implements FRealtimeRequest.OnRealtimeCallBack, FStoreRequest.OnFStoreCallBack {


    public static final String KEY_SHOW_PICKER = "KEY_SHOW_PICKER";
    private static final Object CODE_SHOW_PICKER = 1002;
    private static final String KEY_SAVE_COMBOO_IMG = "KEY_SAVE_COMBOO_IMG";
    private static final String KEY_SAVE_COMBOO = "KEY_SAVE_COMBOO";
    private EditText edtCombooName, edtPrice;
    private ImageView ivComboo;
    private Uri mCombooImage;


    public M003AddCombooDialog(@NonNull Context context) {
        super(context, null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_add_comboo_dialog;
    }

    @Override
    protected void initViews() {
        ((TextView) findViewById(R.id.tv_title)).setText("Thêm comboo");
        edtCombooName = findViewById(R.id.edt_comboo_name);
        edtPrice = findViewById(R.id.edt_price);
        ivComboo = findViewById(R.id.iv_comboo);
    }

    @Override
    protected void initListenner() {
        registerListenners(
                ivComboo,
                findViewById(R.id.tv_add),
                findViewById(R.id.iv_back)
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
        switch (view.getId()) {
            case R.id.iv_back:
                dismiss();
                break;
            case R.id.iv_comboo:
                if (mCallback != null) {
                    mCallback.callBack(KEY_SHOW_PICKER, CODE_SHOW_PICKER);
                }
                break;
            case R.id.tv_add:
                if (edtCombooName.getText().toString().isEmpty()) {
                    edtCombooName.setError("Không bỏ trống mục này");
                    return;
                }
                if (edtPrice.getText().toString().isEmpty()) {
                    edtPrice.setError("Không bỏ trống mục này");
                    return;
                }
                if (mCombooImage == null) {
                    Toast.makeText(mContext, "Bạn phải tải ảnh lên!", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveCombooImg(mCombooImage);
                break;
        }
    }

    private void saveCombooImg(Uri mCombooImage) {
        ProgressLoading.show(mContext);
        new FStoreRequest("combo", mCombooImage)
                .callRequest(KEY_SAVE_COMBOO_IMG, this);

    }


    @Override
    public void onRealtimeSetSuccess(String tag, String key) {
        ProgressLoading.dismiss();
        if (tag.equals(KEY_SAVE_COMBOO)) {
            dismiss();
        }
    }

    @Override
    public void onRealtimeSetFailed(String tag) {
        ProgressLoading.dismiss();
    }

    public void setFileUri(Uri file) {
        mCombooImage = file;
        ivComboo.setImageURI(file);
    }

    @Override
    public void uploadFileDone(String tag, String link) {
        ProgressLoading.dismiss();
        if (tag.equals(KEY_SAVE_COMBOO_IMG)) {
            Toast.makeText(mContext, "upload image done" + link, Toast.LENGTH_SHORT).show();
            saveComboo(link);
        }
    }

    private void saveComboo(String link) {
        ProgressLoading.show(mContext);
        CombooEntity entity = new CombooEntity( link,edtCombooName.getText().toString(), edtPrice.getText().toString());
        new FRealtimeRequest("Combo")
                .method(FRealtimeRequest.METHOD_PUSH)
                .data(entity)
                .callRequest(KEY_SAVE_COMBOO, this);
    }

    @Override
    public void uploadFailded(String tag, Exception e) {
        ProgressLoading.dismiss();
    }
}
