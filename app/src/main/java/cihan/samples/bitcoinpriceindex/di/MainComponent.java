package cihan.samples.bitcoinpriceindex.di;

import javax.inject.Singleton;

import cihan.samples.bitcoinpriceindex.main.MainActivity;
import cihan.samples.bitcoinpriceindex.ui.dashboard.DashboardViewModel;
import dagger.Component;

@Singleton
@Component(modules = {MainModule.class, RepoModule.class})
public interface MainComponent {

    void inject(MainActivity mainActivity);

    void inject(DashboardViewModel dashboardViewModel);

}
