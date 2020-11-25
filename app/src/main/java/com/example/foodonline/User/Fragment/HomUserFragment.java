package com.example.foodonline.User.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.Adpter.LishItemComboAdapter;
import com.example.foodonline.Adpter.ListComboHot;
import com.example.foodonline.Adpter.ListItemDishAdapter;
import com.example.foodonline.DataModel.ComboModel;
import com.example.foodonline.DataModel.DishModel;
import com.example.foodonline.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class HomUserFragment extends Fragment {

    Context context;
    Spinner spinner_type_of_dish;
    ListView list_of_dishes;
    RecyclerView list_of_combo;
    ArrayList<DishModel> dataDish;
    ArrayList<ComboModel> dataCombo;
    RecyclerView  image_combo_hot;
    private boolean derection = true;
    private int position;


    public static Fragment newInstance() {
        Bundle args = new Bundle();
        HomUserFragment fragment = new HomUserFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_user, container, false);

        initialization(view);

        setDataSpinner();
        setItemComboHot();
        return view;
    }
//    Set data spinner
    private void setDataSpinner() {
        ArrayList<String> data = new ArrayList<>();
        data.add("Tất cả");
        data.add("Món nướng");
        data.add("Món nước");
        data.add("Combo");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, data);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type_of_dish.setAdapter(arrayAdapter);
        spinner_type_of_dish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();
                if (tutorialsName.equals("Combo")){
                    list_of_combo.setVisibility(View.VISIBLE);
                    list_of_dishes.setVisibility(View.GONE);
                    setItemDishCombo();
                }else {
                    list_of_combo.setVisibility(View.GONE);
                    list_of_dishes.setVisibility(View.VISIBLE);
                    setItemDish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setItemDish(){
        dataDish=new ArrayList<>();
        dataDish.add(new DishModel("a","Tên món ăn","b","100","a","s","s"));
        dataDish.add(new DishModel("a","Tên món ăn","b","100","a","s","s"));
        dataDish.add(new DishModel("a","Tên món ăn","b","100","a","s","s"));
        ListItemDishAdapter listItemDishAdapter = new ListItemDishAdapter(context,R.layout.fragment_home_user,dataDish);
        list_of_dishes.setAdapter(listItemDishAdapter);
    }
    private void setItemDishCombo(){
        dataCombo=new ArrayList<>();
        dataCombo.add(new ComboModel("combo","Tên Combo","a","7","100.000"));
        dataCombo.add(new ComboModel("combo","Tên Combo","a","7","100.000"));
        dataCombo.add(new ComboModel("combo","Tên Combo","a","7","100.000"));
        dataCombo.add(new ComboModel("combo","Tên Combo","a","7","100.000"));
        dataCombo.add(new ComboModel("combo","Tên Combo","a","7","100.000"));
        dataCombo.add(new ComboModel("combo","Tên Combo","a","7","100.000"));
        dataCombo.add(new ComboModel("combo","Tên Combo","a","7","100.000"));
        dataCombo.add(new ComboModel("combo","Tên Combo","a","7","100.000"));
        LinearLayoutManager layoutManager = new GridLayoutManager(context,2);
        list_of_combo.setLayoutManager(layoutManager);
        LishItemComboAdapter listItemDishAdapter = new LishItemComboAdapter(dataCombo);
        list_of_combo.setAdapter(listItemDishAdapter);
    }
    private void setItemComboHot(){
        dataCombo=new ArrayList<>();
        dataCombo.add(new ComboModel("combo","Tên Combo","a","7","100.000"));
        dataCombo.add(new ComboModel("combo","Tên Combo","a","7","100.000"));
        dataCombo.add(new ComboModel("combo","Tên Combo","a","7","100.000"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        image_combo_hot.setLayoutManager(layoutManager);
        ListComboHot listComboHot = new ListComboHot(dataCombo);
        image_combo_hot.setAdapter(listComboHot);
        scrollBytime();
    }

    private void scrollBytime() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (derection) {
                    position ++;
                    if(position>=dataCombo.size()){
                        derection = false;
                    }
                }else{
                    position =0;
                    if (position <= 0){
                        derection = true;
                    }
                }
                image_combo_hot.smoothScrollToPosition(position);
                handler.postDelayed(this, 3000);
            }
        });
    }

    private void initialization(View view){
        spinner_type_of_dish = view.findViewById(R.id.spinner_name_list_dishes);
        list_of_dishes = view.findViewById(R.id.list_of_dishes);
        list_of_combo = view.findViewById(R.id.list_of_combo);
        image_combo_hot = view.findViewById(R.id.image_combo_hot);
    }
}
