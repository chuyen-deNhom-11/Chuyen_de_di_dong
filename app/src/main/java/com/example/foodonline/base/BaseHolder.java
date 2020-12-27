package com.example.foodonline.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final View rootView;

    public BaseHolder(@NonNull View itemView) {
        super(itemView);
        this.rootView = itemView;
    }

    protected <T extends View> T findViewById(int id){
        return rootView.findViewById(id);
    }

    protected abstract void initView(int position);



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
