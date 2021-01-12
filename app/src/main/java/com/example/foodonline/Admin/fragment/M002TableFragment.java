package com.example.foodonline.Admin.fragment;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodonline.Admin.adapter.TableAdapter;
import com.example.foodonline.Admin.dialog.M002AddTableDialog;
import com.example.foodonline.Admin.model.TableEntity;
import com.example.foodonline.Admin.widget.ProgressLoading;
import com.example.foodonline.R;
import com.example.foodonline.base.BaseFragment;
import com.example.foodonline.utils.firebase.FRealtimeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Collections;


public class M002TableFragment extends BaseFragment implements FRealtimeRequest.OnRealtimeCallBack {


    public static final String TAG = M002TableFragment.class.getName();
    private static final String KEY_GET_TABLE = "KEY_GET_TABLE";
    private RecyclerView rvTable;
    private TableAdapter adapter;
    private ImageView ivAdd;


    @Override
    protected void initViews() {
        rvTable = findViewById(R.id.rv_table);
        rvTable.setLayoutManager(new GridLayoutManager(mContext, 3));
        ivAdd = findViewById(R.id.iv_add);

    }

    @Override
    protected void initListenners() {
        registerListenner(ivAdd);
    }

    @Override
    protected void initComponent() {
        adapter = new TableAdapter(mContext);
        rvTable.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        ProgressLoading.show(mContext);
        new FRealtimeRequest("Table")
                .callRequest(KEY_GET_TABLE, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ban;
    }

    @Override
    public void onRealtimeUpdate(String tag, DataSnapshot data) {
        ProgressLoading.dismiss();
        if (tag.equals(KEY_GET_TABLE)) {
            ArrayList<TableEntity> listTable = new ArrayList<>();
            for (DataSnapshot child :
                    data.getChildren()) {
                TableEntity entity = child.getValue(TableEntity.class);
                entity.setKey(child.getKey());
                listTable.add(entity);
            }
            Collections.reverse(listTable);
            adapter.submitList(listTable);
        }
    }

    @Override
    public void onRealtimeError(String tag, DatabaseError databaseError) {
        ProgressLoading.dismiss();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_add) {
            M002AddTableDialog dialog = new M002AddTableDialog(mContext, null);
            dialog.show();
        }
    }
}