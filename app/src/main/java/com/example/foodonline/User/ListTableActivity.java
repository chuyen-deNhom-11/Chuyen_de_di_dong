package com.example.foodonline.User;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.Adpter.ListTableAdapter;
import com.example.foodonline.DataModel.BookingTableModel;
import com.example.foodonline.DataModel.TableModel;
import com.example.foodonline.R;
import com.example.foodonline.User.Fragment.HomUserFragment;
import com.example.foodonline.User.Fragment.SetTableFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.foodonline.utils.Constant.COMBO_ID;
import static com.example.foodonline.utils.Constant.USER_ID;

public class ListTableActivity extends AppCompatActivity implements ListTableAdapter.ClickLisener{
    ArrayList<TableModel> tableModels;
    RecyclerView list_table;
    ListTableAdapter listTableAdapter;
    FirebaseDatabase fData = FirebaseDatabase.getInstance();
    EditText edt_date,edt_time;
    TextView name_table;
    ImageView img_select_date, img_select_time;
    Button btn_Cancel,btn_booking;
    Intent intent;
    String userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Danh sách bàn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_list_table);
        list_table = findViewById(R.id.list_table);
        intent = getIntent();
        userId = intent.getStringExtra(USER_ID);
        setItemTable();
    }
    private void setItemTable(){
        tableModels = new ArrayList<>();
        fData.getReference().child("Table").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TableModel table = snapshot.getValue(TableModel.class);
                tableModels.add(new TableModel(snapshot.getKey(),table.getNameTable(),table.getNumberPeople(),table.getStatus()));
                listTableAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (int i =0;i<tableModels.size();i++){
                    if (snapshot.getKey().equals(tableModels.get(i).getId())){
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
        LinearLayoutManager layoutManager = new GridLayoutManager(this,3);
        list_table.setLayoutManager(layoutManager);
        listTableAdapter = new ListTableAdapter(tableModels,this);
        list_table.setAdapter(listTableAdapter);
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
    private void showDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_select_table, null);

        edt_date = alertLayout.findViewById(R.id.edt_bokking_day);
        edt_time = alertLayout.findViewById(R.id.edt_time_booking);
        name_table = alertLayout.findViewById(R.id.name_table);
        img_select_date = alertLayout.findViewById(R.id.img_select_date);
        img_select_time = alertLayout.findViewById(R.id.img_select_time);
        btn_Cancel = alertLayout.findViewById(R.id.btn_Cancel);
        btn_booking = alertLayout.findViewById(R.id.btn_booking);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                sDateBooking = edt_date.getText().toString();
                sTimeBooking = edt_time.getText().toString();
                if ( sDateBooking.equals("") || sTimeBooking.equals("")) {
                    Toast.makeText(ListTableActivity.this, R.string.check_input_text, Toast.LENGTH_SHORT).show();
                } else {
                    BookingTableModel bk = new BookingTableModel(sNameTable,sDateBooking, sTimeBooking, sTableID);
                    fData.getReference().child("Cart").child(userId).child("table").setValue(bk).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.dismiss();
                            finish();
                            Toast.makeText(ListTableActivity.this, "Chọn bàn thành công!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    String sDateBooking,sTimeBooking,sTableID,sNameTable;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    public void onClick(int posotion) {
        if (tableModels.get(posotion).getStatus()!=2){
            showDialog();
            sTableID = tableModels.get(posotion).getId();
            sNameTable = tableModels.get(posotion).getNameTable();
            name_table.setText(sNameTable);
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
            Toast.makeText(this,"Bạn này đã có người sử dụng",Toast.LENGTH_SHORT).show();
        }

    }
    private void datePikerDialog() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        sDateBooking = day + "/" + month + "/" + year;
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                mDateSetListener, year, month, day);
        dialog.show();
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                sDateBooking = day + "/" + month + "/" + year;
                edt_date.setText(sDateBooking);
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
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                sTimeBooking = selectedHour + ":" + selectedMinute;
                edt_time.setText(sTimeBooking);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Chọn thời gian");
        mTimePicker.show();
    }
}
