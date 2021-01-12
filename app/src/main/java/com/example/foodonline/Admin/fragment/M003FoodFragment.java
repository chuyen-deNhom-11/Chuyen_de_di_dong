package com.example.foodonline.Admin.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.Admin.adapter.FoodAdapter;
import com.example.foodonline.Admin.model.DishTypeEntity;
import com.example.foodonline.Admin.model.FoodEntity;
import com.example.foodonline.Admin.widget.ProgressLoading;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseFragment;
import com.example.foodonline.base.BaseRecylerAdapter;
import com.example.foodonline.utils.firebase.FRealtimeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Set;

public class M003FoodFragment extends BaseFragment implements FRealtimeRequest.OnRealtimeCallBack, BaseRecylerAdapter.AdapterCallBack {

    public static final String TAG = M003FoodFragment.class.getName();
    private static final String KEY_GET_ALL_DISH_TYPE = "KEY_GET_ALL_DISH";
    private static final String KEY_GET_DISH = "KEY_GET_DISH";
    private Spinner spinner;
    private ArrayList<DishTypeEntity> listDishType;
    private RecyclerView rvFood;
    private FoodAdapter adapter;
    private TextView tvCancel, tvDelete;
    private ImageView ivAdd;
    private ArrayList<DataSnapshot> listFoodSnapshot;
    private DishTypeEntity choosenType;


    @Override
    protected void initViews() {
        findViewById(R.id.iv_back).setVisibility(View.GONE);
        spinner = findViewById(R.id.spinner_food_type);

        rvFood = findViewById(R.id.rv_dish);
        rvFood.setLayoutManager(new LinearLayoutManager(mContext));
        tvCancel = findViewById(R.id.tv_cancel);

        ivAdd = findViewById(R.id.iv_add);
        tvDelete = findViewById(R.id.tv_delete);
    }

    @Override
    protected void initListenners() {
        registerListenner(
                ivAdd,
                tvCancel,
                tvDelete
        );
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choosenType = listDishType.get(i);
                getListDish(choosenType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getListDish(DishTypeEntity choosenType) {
        Set<String> setKey = choosenType.getDish().keySet();
        adapter.submitList(new ArrayList<>());
        for (String dishKey : setKey) {
            getDish(dishKey);
        }
    }

    private void getDish(String dishKey) {
        new FRealtimeRequest("Dish/" + dishKey)
                .ignorUpdate(true)
                .callRequest(KEY_GET_DISH, this);

    }

    @Override
    protected void initComponent() {
        adapter = new FoodAdapter(mContext);
        adapter.setCallBack(this);
        rvFood.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        listFoodSnapshot = new ArrayList<>();
        getAllFoodType();
    }

    private void getAllFoodType() {
        ProgressLoading.show(mContext);
        new FRealtimeRequest("typeOfDish")
                .ignorUpdate(true)
                .callRequest(KEY_GET_ALL_DISH_TYPE, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mon_an;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_add) {
            navController.navigate(R.id.action_to_add_food);
        } else if (view.getId() == R.id.tv_cancel) {
            adapter.showCheckBox(false);
            tvCancel.setVisibility(View.GONE);
            tvDelete.setVisibility(View.GONE);
            ivAdd.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.tv_delete) {
            ArrayList<FoodEntity> data = new ArrayList<>(adapter.getListData());
            for (int i = listFoodSnapshot.size() - 1; i >= 0; i--) {
                if (data.get(i).isCheck()) {
                    listFoodSnapshot.get(i).getRef().removeValue();
                }
            }
            listFoodSnapshot = new ArrayList<>();
            getListDish(choosenType);
        }
    }

    @Override
    public void onRealtimeUpdate(String tag, DataSnapshot data) {
        ProgressLoading.dismiss();
        if (tag.equals(KEY_GET_ALL_DISH_TYPE)) {
            listDishType = new ArrayList<>();
            ArrayList<String> arrString = new ArrayList<>();
            for (DataSnapshot child :
                    data.getChildren()) {
                DishTypeEntity dishType = child.getValue(DishTypeEntity.class);
                dishType.setKey(child.getKey());
                listDishType.add(dishType);
                arrString.add(dishType.getName());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, R.layout.item_text_spinner, arrString);
            spinner.setAdapter(arrayAdapter);

        } else if (tag.equals(KEY_GET_DISH)) {
            FoodEntity entity = data.getValue(FoodEntity.class);
            if (entity != null) {
                entity.setKey(data.getKey());
                adapter.add(entity);
                listFoodSnapshot.add(data);

            }
        }
    }

    @Override
    public void onRealtimeError(String tag, DatabaseError databaseError) {
        ProgressLoading.dismiss();
    }

    @Override
    public void callBack(String key, Object data) {
        if (key.equals(FoodAdapter.KEY_LONG_CLICK_ITEM)) {
            tvCancel.setVisibility(View.VISIBLE);
            tvDelete.setVisibility(View.VISIBLE);
            ivAdd.setVisibility(View.GONE);
        }
    }
}