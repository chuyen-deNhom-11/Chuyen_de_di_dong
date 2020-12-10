package com.example.foodonline.User.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodonline.Adpter.ListDishBillAdapter;
import com.example.foodonline.DataModel.BillModel;
import com.example.foodonline.R;

import java.util.ArrayList;

public class BillFragment extends Fragment {
    Context context;
    ArrayList<BillModel> billModels;
    ListView list_dish;

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        BillFragment fragment = new BillFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        list_dish = view.findViewById(R.id.list_dish);
        setListDish();
        return view;
    }

    private void setListDish(){
        billModels = new ArrayList<>();
        billModels.add(new BillModel("asd","asd","Lẩu hải sản","100.000đ",1));
        billModels.add(new BillModel("asd","asd","Gà nướng","100.000đ",1));
        ListDishBillAdapter listDishBillAdapter = new ListDishBillAdapter(context,R.layout.fragment_bill,billModels);
        list_dish.setAdapter(listDishBillAdapter);
    }
}
