package cihan.samples.bitcoinpriceindex.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import cihan.samples.bitcoinpriceindex.data.model.Coin;
import cihan.samples.bitcoinpriceindex.data.model.CoinHistory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class CoinRepository {

    public static String[] HISTORY_PERIODS = {"daily", "monthly", "alltime"};
    private final BitcoinAverageApi bitcoinAverageApi;

    @Inject
    public CoinRepository(@NonNull BitcoinAverageApi bitcoinAverageApi) {
        this.bitcoinAverageApi = bitcoinAverageApi;
    }

    //TODO:Offline mode
    public LiveData<Resource<List<CoinHistory>>> getCoinHistory(String symbolSet, String coin, String currency, String period) {
        final MutableLiveData<Resource<List<CoinHistory>>> coinHistory = new MutableLiveData<>();
        coinHistory.setValue(Resource.loading(null));
        bitcoinAverageApi.getCoinDaily(symbolSet, coin, currency, period).enqueue(new Callback<List<CoinHistory>>() {

            @Override
            public void onResponse(@NonNull Call<List<CoinHistory>> call, @NonNull Response<List<CoinHistory>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    coinHistory.postValue(Resource.success(response.body()));
                } else {
                    coinHistory.postValue(Resource.error("No data found", null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CoinHistory>> call, @NonNull Throwable t) {
                coinHistory.postValue(Resource.error("Couldn't retrieve data from server", null));
            }
        });

        return coinHistory;
    }

    //TODO:Offline mode
    public LiveData<Resource<Coin>> getCoinLast(String symbolSet, String coin, String currency) {
        final MutableLiveData<Resource<Coin>> coinLast = new MutableLiveData<>();
        coinLast.setValue(Resource.loading(null));
        bitcoinAverageApi.getCoin(symbolSet, coin, currency).enqueue(new Callback<Coin>() {

            @Override
            public void onResponse(@NonNull Call<Coin> call, @NonNull Response<Coin> response) {
                if (response.isSuccessful() && response.body() != null) {
                    coinLast.postValue(Resource.success(response.body()));
                } else {
                    coinLast.postValue(Resource.error("No data found", null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Coin> call, @NonNull Throwable t) {
                coinLast.postValue(Resource.error("Couldn't retrieve data from server", null));
            }
        });

        return coinLast;
    }
}
