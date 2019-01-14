package cihan.samples.bitcoinpriceindex.di;

import android.content.Context;

import javax.inject.Singleton;

import cihan.samples.bitcoinpriceindex.main.utils.AppExecutors;
import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private Context context;

    public MainModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }
}
