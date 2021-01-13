package com.example.foodonline.utils.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class FRealtimeRequest {

    public static final String TAG = FRealtimeRequest.class.getName();
    public static final String METHOD_GET = "METHOD_GET";
    public static final String METHOD_PUSH = "METHOD_PUSH";
    public static final String METHOD_SET = "METHOD_SET";
    public static final String METHOD_CHILD_ADDED = "METHOD_LISTEN_CHILD_ADDED";


    private final String path;
    private final DatabaseReference dbRef;
    private String methodType;
    private Object data;
    private Query query;
    private boolean ignoreUpdate;

    private boolean firstUpdate = true;

    public FRealtimeRequest(String path) {
        this.path = path;
        this.methodType = METHOD_GET;
        dbRef = FirebaseDatabase.getInstance().getReference();
    }

    public FRealtimeRequest method(String methodType) {
        this.methodType = methodType;
        return this;
    }

    public FRealtimeRequest ignorUpdate(boolean ignorUpdate) {
        this.ignoreUpdate = ignorUpdate;
        return this;
    }

    public FRealtimeRequest data(Object data) {
        this.data = data;
        return this;
    }

    public FRealtimeRequest query(String child, String value) {
        DatabaseReference raf = dbRef.child(path);
        query = raf.orderByChild(child).equalTo(value);
        return this;
    }

    public FRealtimeRequest query(String child, int value) {
        DatabaseReference raf = dbRef.child(path);
        query = raf.orderByChild(child).equalTo(value);
        return this;
    }

    public FRealtimeRequest query(String child, long value) {
        DatabaseReference raf = dbRef.child(path);
        query = raf.orderByChild(child).equalTo(value);
        return this;
    }


    public FRealtimeRequest order(String childSort) {
        DatabaseReference raf = dbRef.child(path);
        query = raf.orderByChild(childSort);
        return this;
    }

    public void callRequest(String tag, OnRealtimeCallBack mCallBack) {
        DatabaseReference child = dbRef.child(path);

        switch (methodType) {
            case METHOD_GET:
                // 3 variable for query data case
                ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mCallBack.onRealtimeUpdate(tag, dataSnapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        mCallBack.onRealtimeError(tag, databaseError);
                    }
                };
                if (ignoreUpdate) {
                    (query != null ? query : child).addListenerForSingleValueEvent(listener);
                } else {
                    (query != null ? query : child).addValueEventListener(listener);
                }
                break;
            case METHOD_PUSH:
            case METHOD_SET:
                if (data == null) {
                    Log.e(TAG, "callRequest: data null when call push ", new NullPointerException());
                    return;
                }
                String key = "";
                if (methodType.equals(METHOD_PUSH)) {
                    key = child.push().getKey();
                }

                String finalKey = key;
                (key == null ? child : child.child(key)).setValue(data).addOnCompleteListener(task -> {
                    mCallBack.onRealtimeSetSuccess(tag, finalKey);
                }).addOnFailureListener(e -> {
                    mCallBack.onRealtimeSetFailed(tag);
                });
                break;
            case METHOD_CHILD_ADDED:
                child.limitToLast(1).addChildEventListener(new ChildEventAdapter() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (firstUpdate) {
                            firstUpdate = false;
                            return;
                        }
                        mCallBack.onRealtimeUpdate(tag, snapshot);
                    }
                });
                break;
        }
    }

    public interface OnRealtimeCallBack {
        default void onRealtimeUpdate(String tag, DataSnapshot data) {

        }

        default void onRealtimeError(String tag, DatabaseError databaseError) {
            Log.d(TAG, "onRealtimeCancel: ");
        }

        default void onRealtimeSetSuccess(String tag, String key) {

        }

        default void onRealtimeSetFailed(String tag) {

        }
    }

}
