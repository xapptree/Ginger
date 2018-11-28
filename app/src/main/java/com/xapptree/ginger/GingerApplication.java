package com.xapptree.ginger;

import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;

import com.google.firebase.database.FirebaseDatabase;
import com.moe.pushlibrary.MoEHelper;
import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Akbar on 10/13/2017.
 */

public class GingerApplication extends MultiDexApplication {
    private static GingerApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        //enabledStrictMode();
        LeakCanary.install(this);
        Realm.init(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        sInstance = this;
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setPersistenceEnabled(true)
//                .build();
//        db.setFirestoreSettings(settings);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("gingerapp.realm")
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);

        MoEHelper.getInstance(getApplicationContext()).autoIntegrate(this);
        MoEHelper.getInstance(getApplicationContext()).setExistingUser(false);
    }

    public static synchronized GingerApplication getInstance() {
        return sInstance;
    }

    protected void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        enabledStrictMode();
        LeakCanary.install(this);
    }

    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .penaltyLog()
                .penaltyDeath() //
                .build());
    }

}
