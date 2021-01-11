package com.example.foodonline.base;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.foodonline.Admin.event.OnActionCallBack;

public abstract class BaseDialog<T> extends Dialog implements View.OnClickListener {
    protected Context mContext;
    protected T data;
    protected OnActionCallBack mCallback;

    public BaseDialog(@NonNull Context context, T data) {
        super(context);
        init(context, data);
    }


    public void setCallback(OnActionCallBack callback) {
        this.mCallback = callback;
    }

    public BaseDialog(@NonNull Context context, int themeResId, T data) {
        super(context, themeResId);
        init(context, data);
    }

    private void init(Context context, T data) {
        mContext = context;
        this.data = data;
        setContentView(getLayoutId());
        setCancelable(false);
        initViews();
        initListenner();
        initComponent();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected abstract void initListenner();

    protected abstract void initComponent();

    protected abstract void initData();

    protected final void registerListenners(View... views) {
        for (View v : views) {
            if (v != null) {
                v.setOnClickListener(this);
            }
        }
    }


    @Override
    public void onClick(View view) {

    }
}
