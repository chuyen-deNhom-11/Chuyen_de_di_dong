package com.example.foodonline.Adpter;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import com.example.foodonline.DataModel.NoficationModel;
import com.example.foodonline.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class ListNofictionAdapter extends ArrayAdapter<NoficationModel> {
    private Context context;
    private int resource;
    private List<NoficationModel> arrCustomer;
    SimpleDateFormat formatter = null;
    String dateString;
    public ListNofictionAdapter(@NonNull Context context, int resource,  ArrayList<NoficationModel> arrCustomer) {
        super(context, resource, arrCustomer);
        this.context = context;
        this.resource = resource;
        this.arrCustomer = arrCustomer;
    }
    private class ViewHover{
        TextView text_nofiction,time_nofication;
        ImageView image_mail;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ViewHover viewHolder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_nofication,parent,false);
            viewHolder = new ViewHover();
            viewHolder.text_nofiction = view.findViewById(R.id.text_nofiction);
            viewHolder.time_nofication = view.findViewById(R.id.time_nofication);
            viewHolder.image_mail = view.findViewById(R.id.image_mail);
        }else {
            viewHolder = (ListNofictionAdapter.ViewHover) view.getTag();
        }
        NoficationModel noficationModel = arrCustomer.get(position);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            formatter = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
            dateString = formatter.format(new Date(noficationModel.getTime()));
        }
        viewHolder.time_nofication.setText(dateString);
        if(noficationModel.getStatus() == 0 ){
            viewHolder.image_mail.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_nofication));
        }else {
            viewHolder.image_mail.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_mail_outline));
        }

        return view;
    }
}
