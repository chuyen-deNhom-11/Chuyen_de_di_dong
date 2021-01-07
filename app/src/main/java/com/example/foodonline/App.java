package com.example.foodonline;

import android.app.Application;

public class App extends Application {
    private static App instance;
    private Storage storage;


    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        storage = new Storage();
        super.onCreate();
    }

    public Storage getStorage() {
        if (storage == null){
            storage = new Storage();
        }
        return storage;
    }
}
