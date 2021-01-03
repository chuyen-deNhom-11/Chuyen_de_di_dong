package com.example.foodonline.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public abstract class BaseRecylerAdapter<VH extends BaseHolder, D> extends RecyclerView.Adapter<VH> {

    protected Context mContext;
    protected ArrayList<D> mListData;
    protected AdapterCallBack mCallBack;

    public BaseRecylerAdapter(Context mContext) {
        this.mContext = mContext;
        mListData = new ArrayList<>();
    }

    public void setCallBack(AdapterCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @NonNull
    @Override
    public final VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(getItemLayoutId(), parent, false);
        return getViewHolder(v);
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position) {
        holder.initView(position);
    }

    @Override
    public final int getItemCount() {
        return mListData.size();
    }

    protected abstract int getItemLayoutId();

    protected abstract VH getViewHolder(View v);

    public void submitList(ArrayList<D> listData){
        this.mListData = new ArrayList<>(listData);
        notifyDataSetChanged();
    }

    public interface AdapterCallBack{
        void callBack(String key, Object data);
    }


}
