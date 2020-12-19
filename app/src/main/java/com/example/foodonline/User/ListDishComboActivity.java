package com.example.foodonline.User;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodonline.Adpter.ListDishComboAdapter;
import com.example.foodonline.DataModel.BillModel;
import com.example.foodonline.DataModel.CartModel;
import com.example.foodonline.DataModel.ComboModel;
import com.example.foodonline.DataModel.DishModel;
import com.example.foodonline.LoginActivity;
import com.example.foodonline.R;
import com.example.foodonline.SignInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.foodonline.utils.Constant.COMBO;
import static com.example.foodonline.utils.Constant.COMBO_ID;
import static com.example.foodonline.utils.Constant.USER_ID;

public class ListDishComboActivity extends AppCompatActivity {
    String comboId, userId;
    Intent intent;
    ListView lv_list_dish_combo;
    TextView name_combo, price_combo;
    Button btn_oder;
    ListDishComboAdapter listDishComboAdapter;
    ArrayList<DishModel> dataDish;
    FirebaseDatabase fData = FirebaseDatabase.getInstance();
     String[] listDish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Combo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_lish_dish_combo);
        setControl();
        setAdapter();
    }


    private void setControl() {
        intent = getIntent();
        comboId = intent.getStringExtra(COMBO_ID);
        userId = intent.getStringExtra(USER_ID);
        lv_list_dish_combo = findViewById(R.id.lv_list_dish_combo);
        name_combo = findViewById(R.id.name_combo);
        price_combo = findViewById(R.id.price_combo);
        btn_oder = findViewById(R.id.btn_oder);
        btn_oder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oderDish();
            }
        });
    }

    private void setAdapter() {
        fData.getReference().child(COMBO).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().equals(comboId)){
                    ComboModel comboModel = snapshot.getValue(ComboModel.class);
                    price_combo.setText(comboModel.getPriceCombo());
                    name_combo.setText(comboModel.getNameCombo());
                    dataDish = new ArrayList<>();
                    listDish = comboModel.getDish().split("\\,");

                    for (final String item : listDish) {
                        FirebaseDatabase.getInstance().getReference().child("Dish").addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                if (snapshot.getKey().equals(item)) {
                                    DishModel dishModel = snapshot.getValue(DishModel.class);
                                    dataDish.add(new DishModel(item, dishModel.getName(), dishModel.getImage(), dishModel.getPrice(), true));
                                    listDishComboAdapter.notifyDataSetChanged();
                                }
                                listDishComboAdapter = new ListDishComboAdapter(ListDishComboActivity.this, R.layout.activity_lish_dish_combo, dataDish);
                                lv_list_dish_combo.setAdapter(listDishComboAdapter);
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void oderDish(){
        for(int i=0;i<dataDish.size();i++){
            if (dataDish.get(i).isCheck()){
                CartModel cartModel = new CartModel(dataDish.get(i).getName(),1,Integer.parseInt(dataDish.get(i).getPrice()));
                fData.getReference().child("Cart").child(userId).child("dish").child(dataDish.get(i).getId()).setValue(cartModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ListDishComboActivity.this);
                        builder.setMessage("Đã thêm vào hóa đơn :)");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        Dialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
