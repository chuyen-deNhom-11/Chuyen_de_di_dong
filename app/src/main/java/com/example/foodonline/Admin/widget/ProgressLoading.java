package com.example.foodonline.Admin.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.Window;

import com.example.foodonline.R;


public class ProgressLoading {

    private static Dialog pdLoading;
    private static boolean isHide;

    private ProgressLoading() {
        // do nothing
    }

    public static void donShow() {
        isHide = true;
    }

    public static void show(Context context) {

        if (!isLoading() && context != null && !isHide) {
            try {
                if (pdLoading == null) {
                    pdLoading = new Dialog(context);
                    pdLoading.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    pdLoading.setContentView(R.layout.progress_loading);
                    if (pdLoading.getWindow() != null) {
                        pdLoading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    }
                    pdLoading.setCanceledOnTouchOutside(false);
                    pdLoading.getWindow().setGravity(Gravity.CENTER);
                    pdLoading.setCancelable(false);
                }
                pdLoading.show();
            } catch (Exception ignored) {
                //ignored.printStackTrace();
            }
        }

        isHide = false;
    }

    public static void dismiss() {
        //For Unit Test
        if (pdLoading != null && pdLoading.isShowing()) {
            new Handler().postDelayed(() -> {
                try {
                    if (pdLoading != null && pdLoading.isShowing()) {
                        pdLoading.dismiss();
                        pdLoading = null;
                    }
                } catch (Exception ignored) {
                    //ignored.printStackTrace();
                }
            }, 200);
        }
    }


    private static boolean isLoading() {
        return pdLoading != null && pdLoading.isShowing();
    }
}
