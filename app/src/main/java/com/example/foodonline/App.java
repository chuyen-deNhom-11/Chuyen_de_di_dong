package com.example.foodonline;

import android.app.Application;

public class App extends Application {
    private static App instance;
    private Storage storage;
    private Object sendData;


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
        if (storage == null) {
            storage = new Storage();
        }
        return storage;
    }

    public Object getSentData() {
        return sendData;
    }

    public void sendData(Object autoRemoveData) {
        this.sendData = autoRemoveData;
    }
}
