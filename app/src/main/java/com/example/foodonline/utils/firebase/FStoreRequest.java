package com.neos.caapp.utils.firebase;

import android.content.ContentResolver;
import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.neos.caapp.CAApplication;
import com.neos.caapp.model.mess.ReportMessageEntity;
import com.neos.caapp.view.widget.CADateConverter;

import java.util.UUID;

public class FStoreRequest {
    private final StorageReference mStore;
    private String path;
    private final Uri file;


    public FStoreRequest(String sourcePath, Uri file) {
        this.path = sourcePath;
        this.file = file;
        mStore = FirebaseStorage.getInstance().getReference().child(path);
    }

    public void callRequest(String tag, OnFStoreCallBack callBack) {
        String fileName = generateUniqueName();
        StorageReference fileChild = mStore.child(fileName);
        ContentResolver cR = CAApplication.getInstance().getContentResolver();
        String type = cR.getType(file);
        int filetype = -1;
        if (type.startsWith("image")) {
            filetype = ReportMessageEntity.TYPE_IMG;
        } else if (type.startsWith("video")) {
            filetype = ReportMessageEntity.TYPE_VIDEO;
        } else {
            callBack.uploadFailded(tag, new NullPointerException());
            return;
        }
        int finalFiletype = filetype;
        fileChild.putFile(file)
                .addOnSuccessListener(taskSnapshot -> {
                    fileChild.getDownloadUrl().addOnSuccessListener(uri -> {
                        callBack.uploadFileDone(tag, uri.toString(), finalFiletype);
                    });
                }).addOnFailureListener(e -> {
            callBack.uploadFailded(tag, e);
        });
    }

    private String generateUniqueName() {
        long timeNow = CADateConverter.getCurrentDateMiliseconde();
        String uniqueName = UUID.randomUUID().toString() + timeNow;
        return uniqueName;
    }


    public interface OnFStoreCallBack {

        default void uploadFileDone(String tag, String toString, int fileType) {

        }

        default void uploadFailded(String tag, Exception e) {

        }
    }
}
