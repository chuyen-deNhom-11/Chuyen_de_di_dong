package com.example.foodonline.Admin.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.Admin.adapter.FoodAdapter;
import com.example.foodonline.Admin.model.DishTypeEntity;
import com.example.foodonline.Admin.model.FoodEntity;
import com.example.foodonline.Admin.widget.ProgressLoading;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseFragment;
import com.example.foodonline.utils.firebase.FRealtimeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Set;

public class M003FoodFragment extends BaseFragment implements FRealtimeRequest.OnRealtimeCallBack {


    public static final String TAG = M003FoodFragment.class.getName();
    private static final String KEY_GET_ALL_DISH = "KEY_GET_ALL_DISH";
    private static final String KEY_GET_DISH = "KEY_GET_DISH";
    private Spinner spinner;
    private ArrayList<DishTypeEntity> listDishType;
    private RecyclerView rvFood;
    private FoodAdapter adapter;

    @Override
    protected void initViews() {
        findViewById(R.id.iv_back).setVisibility(View.GONE);
        spinner = findViewById(R.id.spinner_food_type);

        rvFood = findViewById(R.id.rv_dish);
        rvFood.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    protected void initListenners() {
        registerListenner(
                findViewById(R.id.iv_add)
        );
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DishTypeEntity choosenType = listDishType.get(i);
                Set<String> setKey = choosenType.getDish().keySet();
                adapter.submitList(new ArrayList<>());
                for (String dishKey : setKey) {
                    getDish(dishKey);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getDish(String dishKey) {
        new FRealtimeRequest("Dish/"+dishKey)
                .ignorUpdate(true)
                .callRequest(KEY_GET_DISH, this);

    }

    @Override
    protected void initComponent() {
        adapter = new FoodAdapter(mContext);
        rvFood.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        getAllFoodType();
    }

    private void getAllFoodType() {
        ProgressLoading.show(mContext);
        new FRealtimeRequest("typeOfDish")
                .callRequest(KEY_GET_ALL_DISH, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mon_an;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_add) {
            navController.navigate(R.id.action_to_add_food);
        }
    }

    @Override
    public void onRealtimeUpdate(String tag, DataSnapshot data) {
        ProgressLoading.dismiss();
        if (tag.equals(KEY_GET_ALL_DISH)) {
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

        }else if (tag.equals(KEY_GET_DISH)){
            FoodEntity entity = data.getValue(FoodEntity.class);
            if (entity!= null){
                entity.setKey(data.getKey());
                adapter.add(entity);
            }
        }
    }

    @Override
    public void onRealtimeError(String tag, DatabaseError databaseError) {
        ProgressLoading.dismiss();
    }
}