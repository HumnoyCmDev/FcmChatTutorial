package com.humnoydeveloper.fcmchattutorial;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * สร้างสรรค์ผลงานโดย humnoyDeveloper ลงวันที่ 18/6/59.19:31น.
 *
 * @FcmChatTutorial
 */
public class MainApplication extends Application{
    private FirebaseDatabase database;
    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance();
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public String getUserId(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
