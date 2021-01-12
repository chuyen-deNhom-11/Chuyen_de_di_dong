package com.example.foodonline.Admin.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodonline.Admin.model.ProfileEntity;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseDialog;
import com.example.foodonline.utils.firebase.FRealtimeRequest;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class M005ChangePassDialog extends BaseDialog<ProfileEntity> implements FRealtimeRequest.OnRealtimeCallBack {
    private static final String KEY_UPDATE_NEW_PROFILE = "KEY_UPDATE_NEW_PROFILE";
    private final ProfileEntity mProfile;
    private TextView tvTitle, edtLastPass, edtNewPass, tvSave;
    private ImageView ivBack;

    public M005ChangePassDialog(@NonNull Context context, ProfileEntity data) {
        super(context, data);
        mProfile = data;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_change_pass_dialog;
    }

    @Override
    protected void initViews() {
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        edtLastPass = findViewById(R.id.edt_last_pass);
        edtNewPass = findViewById(R.id.edt_new_pass);

        tvSave = findViewById(R.id.tv_save);
    }

    @Override
    protected void initListenner() {
        registerListenners(tvSave, ivBack);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back) {
            dismiss();
        } else if (view.getId() == R.id.tv_save) {
            String lastPass = edtLastPass.getText().toString();
            String newPass = edtNewPass.getText().toString();
            if (!lastPass.equals(mProfile.getPassword())) {
                edtLastPass.setError("Mật khẩu cũ không chính xác!");
                return;
            }
            if (newPass.length() < 6) {
                edtNewPass.setError("Mật khẩu phải có ít nhất 6 ký tự");
                return;
            }

            if (newPass.contains(" ")) {
                edtNewPass.setError("Mật khẩu không được phép có khoảng trống!");
                return;
            }

            changePass(mProfile.getEmail(), mProfile.getPassword(), newPass);
            dismiss();

        }
    }

    private void changePass(String email, String password, String newPass) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.updatePassword(newPass).addOnCompleteListener(task1 -> {
                            Toast.makeText(mContext, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            mProfile.setPassword(newPass);
                            updateUserToRealtime(mProfile);
                        });
                    }
                });
    }

    private void updateUserToRealtime(ProfileEntity mProfile) {
        new FRealtimeRequest("Users/" + mProfile.getId())
                .method(FRealtimeRequest.METHOD_SET)
                .data(mProfile)
                .callRequest(KEY_UPDATE_NEW_PROFILE, this);
        mCallback.callBack("EXIT", null);
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initData() {

    }

}
