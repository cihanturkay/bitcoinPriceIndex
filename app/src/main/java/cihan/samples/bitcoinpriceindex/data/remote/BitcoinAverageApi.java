package cihan.samples.bitcoinpriceindex.data.remote;

import java.util.List;

import cihan.samples.bitcoinpriceindex.data.model.Coin;
import cihan.samples.bitcoinpriceindex.data.model.CoinHistory;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BitcoinAverageApi {

    @GET("indices/{symbol_set}/ticker/{crypto}{currency}")
    Call<Coin> getCoin(@Path("symbol_set") String symbol_set, @Path("crypto") String crypto, @Path("currency") String currency);

    @GET("indices/{symbol_set}/history/{crypto}{currency}?period={period}&?format=json")
    Call<List<CoinHistory>> getCoinDaily(@Path("symbol_set") String symbol_set, @Path("crypto") String crypto, @Path("currency") String currency, @Path("period") String period);

}