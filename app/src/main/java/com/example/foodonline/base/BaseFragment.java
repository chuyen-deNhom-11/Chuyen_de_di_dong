package com.example.foodonline.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.foodonline.App;
import com.example.foodonline.R;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {


    protected View mRootView;
    protected Context mContext;
    protected NavController navController;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        navController = Navigation.findNavController(mRootView);
        initViews();
        initListenners();
        initComponent();
        initData();
    }

    public void showMediaPicker(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, App.getInstance()
                .getString(R.string.select_picture)), requestCode);
    }

    protected abstract void initViews();

    protected abstract void initListenners();

    protected abstract void initComponent();

    protected abstract void initData();


    protected <T extends View> T findViewById(int id) {
        return mRootView.findViewById(id);
    }

    protected void registerListenner(View... views) {
        for (View v :
                views) {
            if (v != null) {
                v.setOnClickListener(this);
            }
        }
    }

    protected abstract int getLayoutId();


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}