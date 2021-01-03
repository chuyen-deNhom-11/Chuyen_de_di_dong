package com.example.foodonline.Admin.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.foodonline.Admin.dialog.M003AddCombooDialog;
import com.example.foodonline.Admin.event.OnActionCallBack;
import com.example.foodonline.Admin.model.CombooEntity;
import com.example.foodonline.Admin.model.DishTypeEntity;
import com.example.foodonline.Admin.model.FoodEntity;
import com.example.foodonline.Admin.widget.ProgressLoading;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseFragment;
import com.example.foodonline.utils.firebase.FRealtimeRequest;
import com.example.foodonline.utils.firebase.FStoreRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class M003AddFoodFragment extends BaseFragment implements OnActionCallBack, FRealtimeRequest.OnRealtimeCallBack, FStoreRequest.OnFStoreCallBack {
    private static final String KEY_GET_LIST_COMBOO = "KEY_GET_LIST_COMBOO";
    private static final String KEY_SAVE_FOOD_IMG = "KEY_SAVE_FOOD_IMG";
    private static final String KEY_SAVE_FOOD = "KEY_SAVE_FOOD";
    private static final String KEY_SAVE_FOOD_TO_COMBOO = "KEY_SAVE_FOOD_TO_COMBOO";
    private static final String KEY_CHECK_EXIST_FOOD_TYPE = "KEY_CHECK_EXIST_FOOD_TYPE";
    private static final String KEY_SAVE_FOOD_TO_TYPE = "KEY_SAVE_FOOD_TO_TYPE";
    private Uri chooseImage;
    private ImageView ivUploadImg;
    private M003AddCombooDialog addCombooDialog;
    private ArrayList<CombooEntity> listComboo;
    private Spinner spinnerComboo;
    private EditText edtFoodName, edtFoodType, edtPrice, edtUnit, edtDescription;
    private String mFoodKey;
    private boolean unknown;
    private String foodKey;
    private String keyType;

    @Override
    protected void initViews() {
        spinnerComboo = findViewById(R.id.spinner_comboo);
        ivUploadImg = findViewById(R.id.iv_upload_image);
        edtFoodName = findViewById(R.id.edt_food_name);
        edtFoodType = findViewById(R.id.edt_food_type);
        edtPrice = findViewById(R.id.edt_price);
        edtUnit = findViewById(R.id.edt_don_vi);
        edtDescription = findViewById(R.id.edt_description);

    }

    @Override
    protected void initListenners() {
        registerListenner(
                findViewById(R.id.iv_back),
                ivUploadImg,
                findViewById(R.id.tv_add_comboo),
                findViewById(R.id.tv_add)
        );
    }

    @Override
    protected void initComponent() {

    }

    @Override
    protected void initData() {
        getListComboo();
    }

    private void getListComboo() {
        ProgressLoading.show(mContext);
        new FRealtimeRequest("Combo")
                .callRequest(KEY_GET_LIST_COMBOO, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_food;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add:
                if (edtFoodName.getText().toString().isEmpty()) {
                    edtFoodName.setError("Không bỏ trống mục này!");
                    return;
                } else if (edtFoodType.getText().toString().isEmpty()) {
                    edtFoodType.setError("Không bỏ trống mục này!");
                    return;
                } else if (edtPrice.getText().toString().isEmpty()) {
                    edtPrice.setError("Không bỏ trống mục này!");
                    return;
                } else if (edtUnit.getText().toString().isEmpty()) {
                    edtUnit.setError("Không bỏ trống mục này!");
                    return;
                } else if (edtDescription.getText().toString().isEmpty()) {
                    edtDescription.setError("Không bỏ trống mục này!");
                    return;
                } else if (chooseImage == null) {
                    Toast.makeText(mContext, "Bạn phải tải hình ảnh món ăn!", Toast.LENGTH_SHORT).show();
                    return;
                }
                saveFoodImage(chooseImage);
                break;
            case R.id.iv_upload_image:
                showMediaPicker(1001);
                break;
            case R.id.tv_add_comboo:
                addCombooDialog = new M003AddCombooDialog(mContext);
                addCombooDialog.setCallback(this);
                addCombooDialog.show();
                break;
            case R.id.iv_back:
                navController.popBackStack();
                break;
        }
    }

    private void saveFoodImage(Uri chooseImage) {
        ProgressLoading.show(mContext);
        new FStoreRequest("food", chooseImage)
                .callRequest(KEY_SAVE_FOOD_IMG, this);
    }

    private void saveDish(String fileLink) {
        new FRealtimeRequest("Dish")
                .method(FRealtimeRequest.METHOD_PUSH)
                .data(new FoodEntity(
                                listComboo.get(spinnerComboo.getSelectedItemPosition()).getKey(),
                                edtDescription.getText().toString(),
                                fileLink,
                                edtFoodName.getText().toString(),
                                edtPrice.getText().toString(),
                                edtFoodType.getText().toString()
                        )
                ).callRequest(KEY_SAVE_FOOD, this);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Uri file = data.getData();
            ivUploadImg.setImageURI(file);
            chooseImage = file;
        } else if (requestCode == 1002 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            Uri file = data.getData();
            addCombooDialog.setFileUri(file);
        }
    }

    @Override
    public void callBack(String key, Object data) {
        if (key.equals(M003AddCombooDialog.KEY_SHOW_PICKER)) {
            showMediaPicker((int) data);
        }
    }

    @Override
    public void onRealtimeUpdate(String tag, DataSnapshot data) {
        ProgressLoading.dismiss();
        if (tag.equals(KEY_GET_LIST_COMBOO)) {
            listComboo = new ArrayList<>();
            ArrayList<String> combooName = new ArrayList<>();
            for (DataSnapshot child :
                    data.getChildren()) {
                CombooEntity comboo = child.getValue(CombooEntity.class);
                comboo.setKey(child.getKey());
                listComboo.add(comboo);
                combooName.add(comboo.getNameCombo());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(mContext, R.layout.item_text_spinner, combooName);
            spinnerComboo.setAdapter(arrayAdapter);
        } else if (tag.equals(KEY_CHECK_EXIST_FOOD_TYPE)) {
            for (DataSnapshot child :
                    data.getChildren()) {
                // only one result
                addFoodToType(mFoodKey,
                        child.getKey());
                return;
            }
            addFoodToType(mFoodKey, null);
        }
    }

    private void addFoodToType(String foodKey, String keyType) {
        ProgressLoading.show(mContext);
        unknown = keyType == null;
        this.foodKey = foodKey;
        this.keyType = keyType;
        new FRealtimeRequest(unknown ? "typeOfDish" : "typeOfDish/" + keyType + "/dish/" + foodKey)
                .method(unknown ? FRealtimeRequest.METHOD_PUSH : FRealtimeRequest.METHOD_SET)
                .data(unknown ? new DishTypeEntity(edtFoodType.getText().toString().trim()) : foodKey)
                .callRequest(KEY_SAVE_FOOD_TO_TYPE, this);
    }

    @Override
    public void onRealtimeError(String tag, DatabaseError databaseError) {
        ProgressLoading.dismiss();
    }

    @Override
    public void onRealtimeSetSuccess(String tag, String key) {
        ProgressLoading.dismiss();
        if (tag.equals(KEY_SAVE_FOOD)) {
            mFoodKey = key;
            addFoodToComboo(listComboo.get(spinnerComboo.getSelectedItemPosition()).getKey(), key);
            checkFoodTypeExist(edtFoodType.getText().toString().trim());
            Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
            navController.popBackStack();
        } else if (tag.equals(KEY_SAVE_FOOD_TO_COMBOO)) {
            if (unknown) {
                Toast.makeText(mContext, key, Toast.LENGTH_SHORT).show();
                addFoodToType(foodKey, key);
            }

        }
    }

    private void checkFoodTypeExist(String foodType) {
        new FRealtimeRequest("typeOfDish")
                .query("name", foodType)
                .callRequest(KEY_CHECK_EXIST_FOOD_TYPE, this);
    }

    private void addFoodToComboo(String combooKey, String foodKey) {
        ProgressLoading.show(mContext);
        new FRealtimeRequest("Combo/" + combooKey + "/dish/" + foodKey)
                .method(FRealtimeRequest.METHOD_SET)
                .data(foodKey)
                .callRequest(KEY_SAVE_FOOD_TO_COMBOO, this);
    }

    @Override
    public void onRealtimeSetFailed(String tag) {
        ProgressLoading.dismiss();
    }

    @Override
    public void uploadFileDone(String tag, String fileLink) {
        ProgressLoading.dismiss();
        if (tag.equals(KEY_SAVE_FOOD_IMG)) {
            saveDish(fileLink);
        }
    }

    @Override
    public void uploadFailded(String tag, Exception e) {
        ProgressLoading.dismiss();
    }
}
