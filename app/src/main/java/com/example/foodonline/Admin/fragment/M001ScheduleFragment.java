package com.example.foodonline.Admin.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.Admin.adapter.StaffAdapter;
import com.example.foodonline.Admin.model.AccountEntity;
import com.example.foodonline.Admin.model.ScheduleEntity;
import com.example.foodonline.Admin.widget.ProgressLoading;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseFragment;
import com.example.foodonline.utils.Constant;
import com.example.foodonline.utils.firebase.FRealtimeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Calendar;


public class M001ScheduleFragment extends BaseFragment implements FRealtimeRequest.OnRealtimeCallBack {


    public static final String TAG = M001ScheduleFragment.class.getName();
    private static final String KEY_GET_SHIFT = "KEY_GET_SHIFT";
    private Spinner spinnerDay;
    private RadioGroup rgShift;
    private RecyclerView rvListStaff;
    private StaffAdapter adapter;
    private ArrayList<ScheduleEntity> listSchedule;

    @Override
    protected void initViews() {
        spinnerDay = findViewById(R.id.spinner_day);
        rgShift = findViewById(R.id.radio_group_shift);
        ((RadioButton) rgShift.getChildAt(0))
                .setChecked(true);
        rvListStaff = findViewById(R.id.rv_listStaff);
        rvListStaff.setLayoutManager(new LinearLayoutManager(mContext));
    }

    private int getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    protected void initListenners() {
        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getDayShift(i + 2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rgShift.setOnCheckedChangeListener((radioGroup, i) -> {
            int index = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));
           doCheckRadio(index);
        });
    }

    private void getDayShift(int day) {
        ProgressLoading.show(mContext);
        new FRealtimeRequest("work_schedule")
                .query("day", day)
                .ignorUpdate(true)
                .callRequest(KEY_GET_SHIFT, this);
    }

    @Override
    protected void initComponent() {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, R.layout.item_text_spinner, Constant.DAYS_IN_WEEK);
        spinnerDay.setAdapter(arrayAdapter);
        int day = getToday();
        int position = day == 1 ? 6 : day - 2;
        spinnerDay.setSelection(position);

        listSchedule = new ArrayList<>();
        adapter = new StaffAdapter(mContext);
        rvListStaff.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        int today = getToday();
        getDayShift(today == 1 ? 8 : today);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_schedule;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRealtimeUpdate(String tag, DataSnapshot data) {
        ProgressLoading.dismiss();
        if (tag.equals(KEY_GET_SHIFT)) {
            listSchedule = new ArrayList<>();
            for (DataSnapshot child :
                    data.getChildren()) {
                ScheduleEntity childData = child.getValue(ScheduleEntity.class);
                childData.setKey(child.getKey());
                listSchedule.add(childData);
            }
            doCheckRadio(0);
        }
    }

    private void doCheckRadio(int index) {
        ArrayList<AccountEntity> shiftList = new ArrayList<>();
        for (ScheduleEntity shift :
                listSchedule) {
            if (shift.getShift() == index + 1) {
                shiftList.add(new AccountEntity(shift.getStaff_name(), shift.getType()));
            }
        }
        adapter.submitList(shiftList);
    }

    @Override
    public void onRealtimeError(String tag, DatabaseError databaseError) {
        ProgressLoading.dismiss();
    }

}