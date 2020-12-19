package com.example.foodonline.Adpter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.foodonline.DataModel.CartModel;
import com.example.foodonline.DataModel.DishModel;
import com.example.foodonline.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListItemDishAdapter extends ArrayAdapter<DishModel> {
    private Context context;
    private int resource;
    private List<DishModel> arrCustomer;
    private String userId;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    public ListItemDishAdapter(Context context, int resource, ArrayList<DishModel> arrCustomer, String userId) {
        super(context, resource, arrCustomer);
        this.context = context;
        this.resource = resource;
        this.arrCustomer = arrCustomer;
        this.userId = userId;
    }


    private class ViewHolder {
        TextView name_dish, price;
        ImageView img_dish;
        Button btn_order;
    }

    @NonNull
    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_dish, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.btn_order = view.findViewById(R.id.btn_order);
            viewHolder.img_dish = view.findViewById(R.id.img_dish);
            viewHolder.name_dish = view.findViewById(R.id.name_dish);
            viewHolder.price = view.findViewById(R.id.price);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        DishModel dishModel = arrCustomer.get(position);
        viewHolder.price.setText(dishModel.getPrice());
        viewHolder.name_dish.setText(dishModel.getName());

        viewHolder.btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(position);
            }
        });
        storageRef.child("food/" + dishModel.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri.toString()).into(viewHolder.img_dish);
            }
        });
        return view;
    }

    private void showDialog(final int position) {
        final NumberPicker numberPicker = new NumberPicker(getContext());
        numberPicker.setLayoutParams(new LinearLayout.LayoutParams(
                convertToPx(140),
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(60);

        // Instantiate the parent layout.
        LinearLayout parent = new LinearLayout(getContext());
        parent.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        parent.setPadding(convertToPx(56), 0, convertToPx(56), 0);
        parent.addView(numberPicker);

        // Build the AlertDilaog and set a NumberPicker.
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Chọn số lượng");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("week", numberPicker.getValue() + "");
                int price = Integer.parseInt(arrCustomer.get(position).getPrice());
                CartModel cartModel = new CartModel(arrCustomer.get(position).getName(), numberPicker.getValue(), price);
                mDatabase.child("Cart").child(userId).child("dish").child(arrCustomer.get(position).getId()).setValue(cartModel);
                Toast.makeText(context, "Đã thêm món " + arrCustomer.get(position).getName() + " vào hóa đơn", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setView(parent);
        Dialog dialog = builder.create();
        dialog.show();
    }

    private int convertToPx(int dp) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int) (dp * metrics.density);
    }
}
