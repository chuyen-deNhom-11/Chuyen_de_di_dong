package com.example.foodonline.User.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.foodonline.Adpter.ListDishBillAdapter;
import com.example.foodonline.DataModel.CartModel;
import com.example.foodonline.DataModel.UserModel;
import com.example.foodonline.R;
import com.example.foodonline.User.ListTableActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.foodonline.utils.Constant.USER_ID;
import static com.example.foodonline.utils.Constant.USER_REFERENCES;

public class BillFragment extends Fragment {
    Context context;
    ArrayList<CartModel> cartModels;
    ListView list_dish;
    String userID;
    ImageView btn_reset;
    FirebaseDatabase fData = FirebaseDatabase.getInstance();
    String name, phoneNumber, adress;
    TextView tv_name_phone, tv_address,tv_select_table,change_tables;
    Button btn_Cancel,btn_Oder;
    ListDishBillAdapter listDishBillAdapter;
    int totalPrice = 0;
    Intent intent;

    public static Fragment newInstance(String userId) {
        Bundle args = new Bundle();
        BillFragment fragment = new BillFragment();
        args.putString(USER_ID, userId);
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
        btn_reset = view.findViewById(R.id.btn_reset);
        tv_name_phone = view.findViewById(R.id.tv_name_phone);
        tv_address = view.findViewById(R.id.address);
        tv_select_table = view.findViewById(R.id.tv_select_table);
        btn_Cancel = view.findViewById(R.id.btn_Cancel);
        btn_Oder = view.findViewById(R.id.btn_order);
        change_tables = view.findViewById(R.id.change_tables);

        Bundle bundle = getArguments();
        if (bundle != null) {
            userID = bundle.getString(USER_ID);
        }

        eventClickListener();
        readData();
        setListDish();
        setResetInfornationListener();
        return view;
    }

    private void eventClickListener(){
        list_dish.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removeDish(i);
                return false;
            }
        });
        tv_select_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTableListener();
            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muôn hủy hóa đơn không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fData.getReference().child("Cart").child(userID).removeValue();
                        for (int i=0 ; i<cartModels.size();i++){
                            cartModels.remove(i);
                        }
                        listDishBillAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Không", null);
                Dialog dialog = builder.create();
                dialog.show();

            }
        });
        change_tables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTableListener();
            }
        });
    }
    private void setListDish() {
        totalPrice =0;
        cartModels = new ArrayList<>();
        fData.getReference().child("Cart").child(userID).child("dish").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                CartModel cart = snapshot.getValue(CartModel.class);
                cartModels.add(new CartModel(snapshot.getKey(), cart.getNameFood(), cart.getSoLuong(), cart.getPrice()));
                totalPrice = totalPrice + (cart.getPrice()* cart.getSoLuong());
                listDishBillAdapter.notifyDataSetChanged();
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
        listDishBillAdapter = new ListDishBillAdapter(context, R.layout.fragment_bill, cartModels, userID);
        list_dish.setAdapter(listDishBillAdapter);
    }

    private void setResetInfornationListener() {
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.dialog_reset_infomation_booking, null);

                final EditText etName = (EditText) alertLayout.findViewById(R.id.et_Name);
                final EditText etPhoneNumber = (EditText) alertLayout.findViewById(R.id.et_PhoneNumber);
                final EditText etAdress = (EditText) alertLayout.findViewById(R.id.et_Adress);

                etName.setText(name);
                etPhoneNumber.setText(phoneNumber);
                etAdress.setText(adress);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(alertLayout);
                builder.setPositiveButton("Thay đổi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        name = etName.getText().toString();
                        phoneNumber = etPhoneNumber.getText().toString();
                        adress = etAdress.getText().toString();
                        setTextInfonation();
                    }
                });
                builder.setNegativeButton("Hủy", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void readData() {

        fData.getReference().child(USER_REFERENCES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().equals(userID)) {
                    UserModel user = snapshot.getValue(UserModel.class);
                    name = user.getName();
                    phoneNumber = user.getNumberPhone();
                    adress = user.getAdress();
                    setTextInfonation();
                }
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

    private void setTextInfonation() {
        tv_name_phone.setText(name + " - " + phoneNumber);
        tv_address.setText(adress);
    }
    private void removeDish(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa món "+ cartModels.get(position).getNameFood() +" khỏi hóa đơn không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fData.getReference().child("Cart").child(userID).child("dish").child(cartModels.get(position).getIdDish()).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        cartModels.remove(position);
                        Log.d("Position", position+"");
                        listDishBillAdapter.notifyDataSetChanged();
                        list_dish.setAdapter(listDishBillAdapter);
                        setTotalPrice();
                    }
                });
            }
        });
        builder.setNegativeButton("Không", null);
        Dialog dialog = builder.create();
        dialog.show();
    }
    private void setTotalPrice(){
        totalPrice = 0 ;
        for (int i=0;i< cartModels.size();i++){
            totalPrice = totalPrice + (cartModels.get(i).getPrice()* cartModels.get(i).getSoLuong());
        }
        Log.d("totalPrice:", totalPrice+"");
    }
    private void selectTableListener(){
        intent = new Intent(context, ListTableActivity.class);
        startActivity(intent);
    }
}
