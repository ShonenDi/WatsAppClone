package com.shonen.ukr.watsappclone;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("2kse0xGTSLDy21El2rw0r6wuiO10dkPSAOhXlhUb")
                .clientKey("QW4r7kSLREnvrJpEliAZaBCbYTp4FKVVF77ScBeB")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
