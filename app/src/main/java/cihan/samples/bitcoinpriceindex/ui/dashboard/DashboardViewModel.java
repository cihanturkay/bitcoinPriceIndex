package cihan.samples.bitcoinpriceindex.ui.dashboard;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import cihan.samples.bitcoinpriceindex.data.model.Coin;
import cihan.samples.bitcoinpriceindex.data.model.CoinHistory;
import cihan.samples.bitcoinpriceindex.data.remote.CoinRepository;
import cihan.samples.bitcoinpriceindex.data.remote.Resource;
import cihan.samples.bitcoinpriceindex.main.MainApp;

public class DashboardViewModel extends AndroidViewModel {

    @Inject
    CoinRepository coinRepository;

    private final MutableLiveData<Boolean> refresh = new MutableLiveData<>();
    private String currency;
    private String coin;
    private String period = "monthly";
    private String market;

    private LiveData<Resource<List<CoinHistory>>> coinHistory;
    private LiveData<Resource<Coin>> coinLast;

    public DashboardViewModel(@NonNull Application application) {
        super(application);
        ((MainApp) application.getApplicationContext()).getMainComponent().inject(this);
        coinLast = Transformations.switchMap(refresh, input -> coinRepository.getCoinLast("global", coin, currency));
        coinHistory = Transformations.switchMap(refresh, input -> coinRepository.getCoinHistory("global", coin, currency, period));
    }

    public LiveData<Resource<List<CoinHistory>>> getCoinHistory() {
        return coinHistory;
    }

    public LiveData<Resource<Coin>> getCoinLast() {
        return coinLast;
    }

    public void refresh() {
        refresh.setValue(true);
    }

    public void setCurrency(String currency) {
        this.currency = currency;
        refresh();
    }

    public void setCoin(String coin) {
        this.coin = coin;
        refresh();
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
