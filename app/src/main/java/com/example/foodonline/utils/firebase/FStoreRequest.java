package com.example.foodonline.utils.firebase;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

public class FStoreRequest {
    private final StorageReference mStore;
    private final Uri file;
    private final String path;


    public FStoreRequest(String sourcePath, Uri file) {
        this.path = sourcePath;
        this.file = file;
        mStore = FirebaseStorage.getInstance().getReference().child(path);
    }

    public void callRequest(String tag, OnFStoreCallBack callBack) {
        String fileName = generateUniqueName();
        StorageReference fileChild = mStore.child(fileName);

        fileChild.putFile(file)
                .addOnSuccessListener(taskSnapshot -> {
                    fileChild.getDownloadUrl().addOnSuccessListener(uri -> {
                        callBack.uploadFileDone(tag, uri.toString());
                    });
                }).addOnFailureListener(e -> {
            callBack.uploadFailded(tag, e);
        });
    }

    private String generateUniqueName() {
        long timeNow = getDateMilisecond();
        String uniqueName = UUID.randomUUID().toString() + timeNow;
        return uniqueName;
    }

    public long getDateMilisecond() {
        long time = System.currentTimeMillis();
        return time;
    }


    public interface OnFStoreCallBack {

        default void uploadFileDone(String tag, String fileLink) {

        }

        default void uploadFailded(String tag, Exception e) {

        }
    }
}
