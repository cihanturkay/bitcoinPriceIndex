package cihan.samples.bitcoinpriceindex.ui.dashboard;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cihan.samples.bitcoinpriceindex.data.model.Coin;
import cihan.samples.bitcoinpriceindex.data.model.CoinHistory;
import cihan.samples.bitcoinpriceindex.data.remote.Resource;
import cihan.samples.bitcoinpriceindex.databinding.FragmentDashboardBinding;
import cihan.samples.bitcoinpriceindex.ui.binding.ItemSelectedListenerAdapter;


public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DashboardViewModel viewModel;
    private Handler handler = new Handler();

    private final static int REFRESH_INTERVAL_SECONDS = 4;

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        loadCoinLast();
        loadCoinHistory();
        setupParameters();
        return binding.getRoot();
    }

    private void setupParameters() {
        binding.spinnerMarkets.setOnItemSelectedListener(new ItemSelectedListenerAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                super.onItemSelected(parent, view, position, id);
                String selectedValue = (String) binding.spinnerMarkets.getItemAtPosition(position);
                if (selectedValue != null) {

                }
            }
        });

        binding.spinnerCoins.setOnItemSelectedListener(new ItemSelectedListenerAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                super.onItemSelected(parent, view, position, id);
                String selectedValue = (String) binding.spinnerCoins.getItemAtPosition(position);
                if (selectedValue != null) {
                    viewModel.setCoin(selectedValue);
                    binding.setCoinText(selectedValue);
                }
            }
        });

        binding.spinnerCurrencies.setOnItemSelectedListener(new ItemSelectedListenerAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                super.onItemSelected(parent, view, position, id);
                String selectedValue = (String) binding.spinnerCurrencies.getItemAtPosition(position);
                if (selectedValue != null) {
                    viewModel.setCurrency(selectedValue);
                    binding.setCurrencyText(selectedValue);
                }
            }
        });

    }

    private Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d("DashboardFragment", "refreshing");
            viewModel.refresh();
            handler.postDelayed(refreshRunnable, TimeUnit.SECONDS.toMillis(REFRESH_INTERVAL_SECONDS));
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        refreshRunnable.run();
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }

    private void loadCoinHistory() {
        viewModel.getCoinHistory().observe(this, new Observer<Resource<List<CoinHistory>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<CoinHistory>> listResource) {
                if (listResource != null) {
                    switch (listResource.status) {
                        case ERROR:
                            break;
                        case LOADING:
                            break;
                        case SUCCESS:
                            break;
                    }
                }
            }
        });
    }

    private void loadCoinLast() {
        viewModel.getCoinLast().observe(this, new Observer<Resource<Coin>>() {
            @Override
            public void onChanged(@Nullable Resource<Coin> coinResource) {
                if (coinResource != null) {
                    switch (coinResource.status) {
                        case ERROR:
                            break;
                        case LOADING:
                            break;
                        case SUCCESS:
                            binding.setCoin(coinResource.data);
                            Log.d("DashboardFragment", "coin :: " + coinResource.data);
                            break;
                    }
                }
            }
        });
    }
}