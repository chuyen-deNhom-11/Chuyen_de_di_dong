package com.example.foodonline.User.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.Adpter.ListDishBillAdapter;
import com.example.foodonline.Adpter.ListTableAdapter;
import com.example.foodonline.DataModel.BillModel;
import com.example.foodonline.DataModel.BookingTableModel;
import com.example.foodonline.DataModel.CartModel;
import com.example.foodonline.DataModel.TableModel;
import com.example.foodonline.DataModel.UserModel;
import com.example.foodonline.LoginActivity;
import com.example.foodonline.R;
import com.example.foodonline.SignInActivity;
import com.example.foodonline.User.HistoryActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.foodonline.utils.Constant.USER_ID;
import static com.example.foodonline.utils.Constant.USER_REFERENCES;

public class SetTableFragment extends Fragment implements ListTableAdapter.ClickLisener {

    Context context;
    ArrayList<TableModel> tableModels;
    RecyclerView list_table;
    TextView name_table, total_people;
    EditText edt_Name, edt_PhoneNumber, edt_adress, edt_bokking_day, edt_time_booking;
    FirebaseDatabase fData = FirebaseDatabase.getInstance();
    ListTableAdapter listTableAdapter;
    String userID,sType,sNvoice, sUserName, sPhone, sAdress, sDateBooking, sTimeBooking, sTableID, sTableName;
    ImageView img_select_date, img_select_time,img_close;;
    Button btn_Oder,btn_Cancel, btn_booking;
    LinearLayout layout_close;
    ArrayList<CartModel> cartModels;
    ListView list_dish;
    LinearLayout layout_ship;
    Intent intent;
    TextView total_price,tv_name_phone,address,tv_name_date_booking,tv_time,change_tables;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public static Fragment newInstance(String userId,String sType) {
        Bundle args = new Bundle();
        SetTableFragment fragment = new SetTableFragment();
        args.putString(USER_ID, userId);
        args.putString("type", sType);
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
        View view = inflater.inflate(R.layout.fragment_set_table, container, false);
        list_table = view.findViewById(R.id.list_table);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userID = bundle.getString(USER_ID);
            sType = bundle.getString("type");
        }
        readData();
        setItemTable();
        return view;
    }
    int j=0;
    private void setItemTable() {
        tableModels = new ArrayList<>();
        fData.getReference().child("Table").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                tableModels.add(snapshot.getValue(TableModel.class));
                tableModels.get(j).setId(snapshot.getKey());
                j++;
                listTableAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (int i = 0; i < tableModels.size(); i++) {
                    if (snapshot.getKey().equals(tableModels.get(i).getId())) {
                        tableModels.get(i).setNameTable(snapshot.child("nameTable").getValue(String.class));
                        tableModels.get(i).setNumberPeople(snapshot.child("numberPeople").getValue(Integer.class));
                        tableModels.get(i).setStatus(snapshot.child("status").getValue(Integer.class));
                        sNvoice = snapshot.child("id_nvoice").getValue(String.class);
                        if(sNvoice != null){
                            tableModels.get(i).setId_nvoice(sNvoice);
                        }
                        listTableAdapter.notifyDataSetChanged();
                    }
                }
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
        LinearLayoutManager layoutManager = new GridLayoutManager(context, 3);
        list_table.setLayoutManager(layoutManager);
        listTableAdapter = new ListTableAdapter(tableModels, this);
        list_table.setAdapter(listTableAdapter);
    }

    private void readData() {
        fData.getReference().child(USER_REFERENCES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().equals(userID)) {
                    UserModel user = snapshot.getValue(UserModel.class);
                    sUserName = user.getName();
                    sPhone = user.getPhoneNumber();
                    sAdress = user.getAdress();
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

    private void dialogBooking() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_set_the_table, null);

        name_table = alertLayout.findViewById(R.id.name_table);
        total_people = alertLayout.findViewById(R.id.total_people);
        edt_Name = alertLayout.findViewById(R.id.edt_Name);
        edt_adress = alertLayout.findViewById(R.id.edt_adress);
        edt_bokking_day = alertLayout.findViewById(R.id.edt_bokking_day);
        edt_PhoneNumber = alertLayout.findViewById(R.id.edt_PhoneNumber);
        edt_time_booking = alertLayout.findViewById(R.id.edt_time_booking);
        img_select_date = alertLayout.findViewById(R.id.img_select_date);
        img_select_time = alertLayout.findViewById(R.id.img_select_time);
        btn_Cancel = alertLayout.findViewById(R.id.btn_Cancel);
        btn_booking = alertLayout.findViewById(R.id.btn_booking);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(alertLayout);
        final AlertDialog dialog = builder.create();
        dialog.show();
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sUserName = edt_Name.getText().toString();
                sAdress = edt_adress.getText().toString();
                sPhone = edt_PhoneNumber.getText().toString();
                sDateBooking = edt_bokking_day.getText().toString();
                sTimeBooking = edt_time_booking.getText().toString();
                if (sUserName.equals("") || sAdress.equals("") || sPhone.equals("") || sDateBooking.equals("") || sTimeBooking.equals("")) {
                    Toast.makeText(context, R.string.check_input_text, Toast.LENGTH_SHORT).show();
                } else {
                    BookingTableModel bk = new BookingTableModel(userID, sUserName, sPhone, sAdress, sTableName, sDateBooking, sTimeBooking, sTableID);
                    fData.getReference().child("BookingTable").push().setValue(bk).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            fData.getReference().child("Table").child(sTableID).child("status").setValue(1);
                            dialog.dismiss();
                            Toast.makeText(context, "Đặt bàn thành công!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    @Override
    public void onClick(int posotion) {
        if(tableModels.get(posotion).getStatus()!=2){
            dialogBooking();
            sTableName = tableModels.get(posotion).getNameTable();
            sTableID = tableModels.get(posotion).getId();
            name_table.setText(sTableName);
            total_people.setText(tableModels.get(posotion).getNumberPeople() + " người");
            edt_Name.setText(sUserName);
            edt_PhoneNumber.setText(sPhone);
            edt_adress.setText(sAdress);
            img_select_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    datePikerDialog();
                }
            });
            img_select_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    timePikerDialog();
                }
            });
        }else {
            if (sType.equals("4")){
                dialogBill(posotion);
            }else {
                Toast.makeText(context,"Bàn này đang có người sử dụng",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void dialogBill( final int positon){
        final Dialog dialog = new Dialog(context, R.style.MyAlertDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_bill);
        layout_close = dialog.findViewById(R.id.layout_close);
        layout_close.setVisibility(View.VISIBLE);
        img_close = dialog.findViewById(R.id.img_close);
        list_dish = dialog.findViewById(R.id.list_dish);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        total_price = dialog.findViewById(R.id.total_price);
        btn_Oder = dialog.findViewById(R.id.btn_Oder);
        btn_Cancel = dialog.findViewById(R.id.btn_Cancel);
        layout_ship = dialog.findViewById(R.id.layout_ship);
        layout_ship.setVisibility(View.GONE);
        readDataBill(tableModels.get(positon).getId_nvoice());
        sTableID = tableModels.get(positon).getId();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        dialog.show();
        btn_Oder.setText("Thanh toán");
        btn_Oder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fData.getReference().child("Bill").child(tableModels.get(positon).getId_nvoice()).child("status").setValue(4).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       fData.getReference().child("Table").child(sTableID).child("status").setValue(0);
                       fData.getReference().child("Table").child(sTableID).child("id_nvoice").removeValue();
                       dialog.dismiss();
                       Toast.makeText(context, "Thanh Toán thành công", Toast.LENGTH_SHORT).show();
                   }
               });
            }
        });
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Bạn có muốn hủy đặt bàn không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        fData.getReference().child("Bill").child(tableModels.get(positon).getId_nvoice()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                fData.getReference().child("Table").child(sTableID).child("status").setValue(0);
                                fData.getReference().child("Table").child(sTableID).child("id_nvoice").removeValue();
                                Toast.makeText(context, "Hủy thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Không", null);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    private void readDataBill(final String sID){
        cartModels = new ArrayList<>();
        fData.getReference().child("Bill").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().equals(sID)){
                    total_price.setText(snapshot.child("price").getValue(String.class));
                    for (DataSnapshot postSnapshot : snapshot.child("Dish").getChildren()){
                        cartModels.add(postSnapshot.getValue(CartModel.class));
                        ListDishBillAdapter listDishBillAdapter = new ListDishBillAdapter(context,R.layout.fragment_bill,cartModels,userID,"HistoryActivity");
                        list_dish.setAdapter(listDishBillAdapter);
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
    private void datePikerDialog() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        sDateBooking = day + "/" + month + "/" + year;
        DatePickerDialog dialog = new DatePickerDialog(context, mDateSetListener, year, month, day);
        dialog.show();
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                sDateBooking = day + "/" + month + "/" + year;
                edt_bokking_day.setText(sDateBooking);
            }
        };
    }

    private void timePikerDialog() {
        // TODO: Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        sTimeBooking = hour + ":" + minute;
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                sTimeBooking = selectedHour + ":" + selectedMinute;
                edt_time_booking.setText(sTimeBooking);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Chọn thời gian");
        mTimePicker.show();
    }
}
