package com.example.foodonline.User.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.foodonline.R;

import java.util.ArrayList;


public class HomUserFragment extends Fragment {

    Context context;
    Spinner spinner_type_of_dish;
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

        spinner_type_of_dish = view.findViewById(R.id.spinner_name_list_dishes);

        setDataSpinner();
        return view;
    }
//    Set data spinner
    private void setDataSpinner() {
        ArrayList<String> data = new ArrayList<>();
        data.add("Món nướng");
        data.add("Món nước");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, data);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type_of_dish.setAdapter(arrayAdapter);
        spinner_type_of_dish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
