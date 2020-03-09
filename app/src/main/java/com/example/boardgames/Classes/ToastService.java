package com.example.boardgames.Classes;

import android.app.Activity;
import android.widget.Toast;

public class ToastService {

    String message;
    Activity activity;

    public ToastService(String message, Activity activity) {
        this.message = message;
        this.activity = activity;
    }

    public void runToast() {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity,
                        message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
