package com.example.foodonline.User.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.Admin.HomeAdminActivity;
import com.example.foodonline.Adpter.LishItemComboAdapter;
import com.example.foodonline.Adpter.ListComboHot;
import com.example.foodonline.Adpter.ListItemDishAdapter;
import com.example.foodonline.DataModel.AcountModel;
import com.example.foodonline.DataModel.ComboModel;
import com.example.foodonline.DataModel.DishModel;
import com.example.foodonline.R;
import com.example.foodonline.User.DataModel.ListOfDishModel;
import com.example.foodonline.User.ListDishComboActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.foodonline.utils.Constant.COMBO;
import static com.example.foodonline.utils.Constant.COMBO_ID;
import static com.example.foodonline.utils.Constant.USER_ID;

public class HomUserFragment extends Fragment implements LishItemComboAdapter.OnComboLisener{

    Context context;
    Intent intent;
    Spinner spinner_type_of_dish;
    ListView list_of_dishes;
    RecyclerView list_of_combo;
    ArrayList<DishModel> dataDish;
    ArrayList<ComboModel> dataCombo;
    RecyclerView image_combo_hot;
    private DatabaseReference database;
    private boolean derection = true;
    private int position;
    String[] listDish;
    String userID;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<ListOfDishModel> listOfDishModels = new ArrayList<ListOfDishModel>();
    FirebaseDatabase fData = FirebaseDatabase.getInstance();
    ListItemDishAdapter listItemDishAdapter;


    public static Fragment newInstance(String userId) {
        Bundle args = new Bundle();
        HomUserFragment fragment = new HomUserFragment();
        fragment.setArguments(args);
        args.putString(USER_ID, userId);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_user, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userID = bundle.getString(USER_ID);
        }
        initialization(view);

        setDataSpinner();
        setItemComboHot();
        return view;
    }

    //    Set data spinner
    private void setDataSpinner() {
        data.add("Tất cả");
        data.add("Combo");
        readData();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, data);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type_of_dish.setAdapter(arrayAdapter);
        spinner_type_of_dish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
//                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();
                if (tutorialsName.equals("Combo")) {
                    list_of_combo.setVisibility(View.VISIBLE);
                    list_of_dishes.setVisibility(View.GONE);
                    setItemDishCombo(position);
                } else {
                    list_of_combo.setVisibility(View.GONE);
                    list_of_dishes.setVisibility(View.VISIBLE);
                    setItemDish(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setItemDish(int position) {
        if (position == 0) {
            dataDish = new ArrayList<>();
            fData.getReference().child("Dish").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    DishModel dishModel = snapshot.getValue(DishModel.class);
                    dataDish.add(new DishModel(snapshot.getKey(), dishModel.getName(), dishModel.getImage(), dishModel.getPrice(), dishModel.getDescription(), dishModel.getIdTypeDish(), dishModel.getIdCombo()));
                    ListItemDishAdapter listItemDishAdapter = new ListItemDishAdapter(context, R.layout.fragment_home_user, dataDish,userID);
                    list_of_dishes.setAdapter(listItemDishAdapter);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Log.d("TAG", "onChildChanged");
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    Log.d("TAG", "onChildRemoved");
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Log.d("TAG", "onChildRemoved");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("TAG", "onCancelled");
                }
            });
        } else if (position > 1){
            listDish = listOfDishModels.get(position - 2).getDish().split("\\,");
            dataDish = new ArrayList<>();
            fData.getReference().child("Dish").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    for (String item : listDish) {
                        if (item.equals(snapshot.getKey())) {
                            DishModel dishModel = snapshot.getValue(DishModel.class);
                            dataDish.add(new DishModel(snapshot.getKey(), dishModel.getName(), dishModel.getImage(), dishModel.getPrice(), dishModel.getDescription(), dishModel.getIdTypeDish(), dishModel.getIdCombo()));
                        }
                    }
                    ListItemDishAdapter listItemDishAdapter = new ListItemDishAdapter(context, R.layout.fragment_home_user, dataDish,userID);
                    list_of_dishes.setAdapter(listItemDishAdapter);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Log.d("TAG", "onChildChanged");
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    Log.d("TAG", "onChildRemoved");
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Log.d("TAG", "onChildRemoved");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("TAG", "onCancelled");
                }
            });
        }
    }

    private void setItemDishCombo(int position) {
        dataCombo = new ArrayList<>();

        fData.getReference().child(COMBO).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ComboModel comboModel = snapshot.getValue(ComboModel.class);
                dataCombo.add(new ComboModel(snapshot.getKey(),comboModel.getNameCombo(),comboModel.getImageCombo(),comboModel.getTotalDish(),comboModel.getPriceCombo(),comboModel.getDish()));
                LinearLayoutManager layoutManager = new GridLayoutManager(context, 2);
                list_of_combo.setLayoutManager(layoutManager);
                LishItemComboAdapter listItemDishAdapter = new LishItemComboAdapter(dataCombo, HomUserFragment.this);
                list_of_combo.setAdapter(listItemDishAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("TAG", "onChildChanged");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.d("TAG", "onChildRemoved");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("TAG", "onChildRemoved");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "onCancelled");
            }
        });
    }


    private void setItemComboHot() {
        dataCombo = new ArrayList<>();
        dataCombo.add(new ComboModel("combo", "Tên Combo", "a", "7", "100.000","asd"));
        dataCombo.add(new ComboModel("combo", "Tên Combo", "a", "7", "100.000","asd"));
        dataCombo.add(new ComboModel("combo", "Tên Combo", "a", "7", "100.000","asd"));
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
                    position++;
                    if (position >= dataCombo.size()) {
                        derection = false;
                    }
                } else {
                    position = 0;
                    if (position <= 0) {
                        derection = true;
                    }
                }
                image_combo_hot.smoothScrollToPosition(position);
                handler.postDelayed(this, 3000);
            }
        });
    }

    private void initialization(View view) {
        spinner_type_of_dish = view.findViewById(R.id.spinner_name_list_dishes);
        list_of_dishes = view.findViewById(R.id.list_of_dishes);
        list_of_combo = view.findViewById(R.id.list_of_combo);
        image_combo_hot = view.findViewById(R.id.image_combo_hot);
    }

    private void readData() {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("typeOfDish").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ListOfDishModel listOfDishModel = snapshot.getValue(ListOfDishModel.class);
                data.add(listOfDishModel.getName());
                listOfDishModels.add(new ListOfDishModel(listOfDishModel.getName(), listOfDishModel.getDish()));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("TAG", "onChildChanged");
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.d("TAG", "onChildRemoved");
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("TAG", "onChildRemoved");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "onCancelled");
            }
        });
    }

    @Override
    public void onComboClick(int position) {
        intent = new Intent(context, ListDishComboActivity.class);
        intent.putExtra(COMBO_ID,dataCombo.get(position).getId());
        intent.putExtra(USER_ID,userID);
        startActivity(intent);
    }
}
