package com.example.boardgames.Classes;

import android.app.Activity;
import android.content.Intent;

import java.util.HashMap;


public class IntentService {

    Intent intent;
    Activity fromActivity;

    public IntentService(Activity from, Class<?> to) {
        if (to instanceof Class<?>) {
            this.intent = new Intent(from, to);
        }
        this.fromActivity = from;
    }

    public void startActivity() {
        this.fromActivity.startActivity(this.intent);
    }

    public void putExtra(HashMap extra) {
        if (extra instanceof HashMap && extra.size() > 0) {
            this.intent.putExtra("hashMap", extra);
        }
    }

    public String getExtraByKey(String key) {
        Intent intent = this.fromActivity.getIntent();
        HashMap<String, String> extraMap = (HashMap<String, String>)intent.getSerializableExtra("hashMap");
        return extraMap.get(key);
    }

    public HashMap<String,String> getAllExtra(String key) {
        Intent intent = this.fromActivity.getIntent();
        return (HashMap<String, String>)intent.getSerializableExtra(key);
    }

    public void finishActivity() {
        this.fromActivity.finish();
    }
}
