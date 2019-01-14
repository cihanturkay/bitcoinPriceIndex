package cihan.samples.bitcoinpriceindex.di;

import javax.inject.Singleton;

import cihan.samples.bitcoinpriceindex.main.MainActivity;
import dagger.Component;

@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent {

    void inject(MainActivity mainActivity);
}
