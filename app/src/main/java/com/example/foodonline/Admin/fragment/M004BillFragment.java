package com.example.foodonline.Admin.fragment;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.Admin.adapter.BillAdapter;
import com.example.foodonline.Admin.model.BillEntity;
import com.example.foodonline.Admin.widget.ProgressLoading;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseFragment;
import com.example.foodonline.utils.firebase.FRealtimeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class M004BillFragment extends BaseFragment implements FRealtimeRequest.OnRealtimeCallBack {
    private static final String KEY_GET_BILLS = "KEY_GET_BILLS";
    private static final String FORMAT_DAY = "dd/MM/yyyy";
    private RecyclerView rvBill;
    private EditText edtDay, edtMonth, edtYear;
    private BillAdapter billAdapter;
    private TextView tvFilter;

    public static String milisToDate(long milise, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(new Date(milise));
        Log.d("TAG", "milisToDate: " + dateString);
        return dateString;
    }

    @Override
    protected void initViews() {
        edtDay = findViewById(R.id.edt_day);
        edtMonth = findViewById(R.id.edt_month);
        edtYear = findViewById(R.id.edt_year);
        tvFilter = findViewById(R.id.tv_loc);
        rvBill = findViewById(R.id.rv_bill);
        rvBill.setLayoutManager(new LinearLayoutManager(mContext));

        String day = getCurrentDate(FORMAT_DAY);
        String[] days = day.split("/");
        edtDay.setText(days[0]);
        edtMonth.setText(days[1]);
        edtYear.setText(days[2]);
    }

    @Override
    protected void initListenners() {
        registerListenner(tvFilter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_loc) {
            getBill();
        }
    }

    private void getBill() {
        int year = Integer.parseInt(edtYear.getText().toString());
        int month = Integer.parseInt(edtMonth.getText().toString());
        int day = Integer.parseInt(edtDay.getText().toString());
        if (year < 0) {
            edtYear.setError("Năm nhập vào không phù hợp");
            return;
        }
        if (month <= 0 || month > 12) {
            edtYear.setError("Tháng nhập vào không phù hợp");
            return;
        }
        if (day < 0 || day > 31) {
            edtYear.setError("Ngày nhập vào không phù hợp");
            return;
        }
        getListBill(day + "/" + month + "/" + year);
    }

    @Override
    protected void initComponent() {
        billAdapter = new BillAdapter(mContext);
        rvBill.setAdapter(billAdapter);
    }

    @Override
    protected void initData() {
        getBill();
    }

    private void getListBill(String time) {
        ProgressLoading.show(mContext);
        new FRealtimeRequest("Bill")
                .query("dateBooking", time)
                .callRequest(KEY_GET_BILLS, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bill_admin;
    }

    @Override
    public void onRealtimeUpdate(String tag, DataSnapshot data) {
        ProgressLoading.dismiss();
        if (tag.equals(KEY_GET_BILLS)) {
            ArrayList<BillEntity> listBill = new ArrayList<>();
            for (DataSnapshot child :
                    data.getChildren()) {
                BillEntity bill = child.getValue(BillEntity.class);
                listBill.add(bill);
            }

            billAdapter.submitList(listBill);
        }
    }

    @Override
    public void onRealtimeError(String tag, DatabaseError databaseError) {
        ProgressLoading.dismiss();
    }

    public String getCurrentDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(getCurrentDate());
    }

    public Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public long getDateMilisecond(String myDate, String format) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(myDate);
            long millis = date.getTime();
            return millis;
        } catch (ParseException e) {
            Log.d("TAG", "getDateMilisecond: " + e.getMessage());
        }

        return -1;
    }
}
