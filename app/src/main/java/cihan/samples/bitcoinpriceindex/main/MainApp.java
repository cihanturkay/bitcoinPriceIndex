package cihan.samples.bitcoinpriceindex.main;

import android.app.Application;

import cihan.samples.bitcoinpriceindex.di.DaggerMainComponent;
import cihan.samples.bitcoinpriceindex.di.MainComponent;
import cihan.samples.bitcoinpriceindex.di.MainModule;

public class MainApp extends Application {

    private MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mainComponent = createTransportationComponent();
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    private MainComponent createTransportationComponent() {
        return DaggerMainComponent
                .builder()
                .mainModule(new MainModule(this))
                .build();
    }
}