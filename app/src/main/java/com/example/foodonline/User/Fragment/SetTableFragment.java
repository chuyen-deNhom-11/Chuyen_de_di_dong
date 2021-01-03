package com.example.foodonline.User.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

import com.example.foodonline.Adpter.ListTableAdapter;
import com.example.foodonline.DataModel.BookingTableModel;
import com.example.foodonline.DataModel.TableModel;
import com.example.foodonline.DataModel.UserModel;
import com.example.foodonline.R;
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
    String userID, sUserName, sPhone, sAdress, sDateBooking, sTimeBooking, sTableID, sTableName;
    ImageView img_select_date, img_select_time;
    Button btn_Cancel, btn_booking;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public static Fragment newInstance(String userId) {
        Bundle args = new Bundle();
        SetTableFragment fragment = new SetTableFragment();
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
        View view = inflater.inflate(R.layout.fragment_set_table, container, false);
        list_table = view.findViewById(R.id.list_table);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userID = bundle.getString(USER_ID);
        }
        readData();
        setItemTable();
        return view;
    }

    private void setItemTable() {
        tableModels = new ArrayList<>();
        fData.getReference().child("Table").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TableModel table = snapshot.getValue(TableModel.class);
                tableModels.add(new TableModel(snapshot.getKey(), table.getNameTable(), table.getNumberPeople(), table.getStatus()));
                listTableAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (int i = 0; i < tableModels.size(); i++) {
                    if (snapshot.getKey().equals(tableModels.get(i).getId())) {
                        tableModels.get(i).setNameTable(snapshot.child("nameTable").getValue(String.class));
                        tableModels.get(i).setNumberPeople(snapshot.child("numberPeople").getValue(Integer.class));
                        tableModels.get(i).setStatus(snapshot.child("status").getValue(Integer.class));
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
        sTableName = tableModels.get(posotion).getNameTable();
        sTableID = tableModels.get(posotion).getId();
        dialogBooking();
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
