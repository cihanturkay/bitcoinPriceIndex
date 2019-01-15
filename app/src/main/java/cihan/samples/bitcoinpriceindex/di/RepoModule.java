package cihan.samples.bitcoinpriceindex.di;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import cihan.samples.bitcoinpriceindex.data.remote.BitcoinAverageApi;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class RepoModule {

    private static final String BTC_API_ENDPOINT = "https://apiv2.bitcoinaverage.com/";


    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Singleton
    @Provides
    BitcoinAverageApi provideBitcoinAverageApi(Gson gson, OkHttpClient client) {

        final Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BTC_API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(BitcoinAverageApi.class);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        return builder.build();
    }


}
