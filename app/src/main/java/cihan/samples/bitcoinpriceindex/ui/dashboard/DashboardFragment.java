package cihan.samples.bitcoinpriceindex.ui.dashboard;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import cihan.samples.bitcoinpriceindex.data.remote.CoinRepository;
import cihan.samples.bitcoinpriceindex.databinding.FragmentDashboardBinding;
import cihan.samples.bitcoinpriceindex.ui.binding.ItemSelectedListenerAdapter;


public class DashboardFragment extends Fragment implements TabLayout.BaseOnTabSelectedListener {

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
        viewModel.setPeriod(CoinRepository.HISTORY_PERIODS[0]);
        loadCoinLast();
        loadCoinHistory();
        setupParameters();
        binding.tabs.addOnTabSelectedListener(this);
        return binding.getRoot();
    }

    private void setupParameters() {
        binding.spinnerMarkets.setOnItemSelectedListener(new ItemSelectedListenerAdapter() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                super.onItemSelected(parent, view, position, id);
                String selectedValue = (String) binding.spinnerMarkets.getItemAtPosition(position);
                if (selectedValue != null) {
                    //TODO for other markets
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
                    binding.chart.setCurrency(selectedValue);
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
            viewModel.refreshLast();
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
        viewModel.getCoinHistory().observe(this, listResource -> {
            if (listResource != null) {
                switch (listResource.status) {
                    case ERROR:
                        Toast.makeText(requireContext(), listResource.message, Toast.LENGTH_LONG).show();
                        binding.setChartLoading(false);
                        break;
                    case LOADING:
                        binding.setChartLoading(true);
                        break;
                    case SUCCESS:
                        binding.chart.highlightValue(null);
                        binding.chart.setCoinData(listResource.data);
                        binding.setChartLoading(false);
                        break;
                }
            }
        });
    }

    private void loadCoinLast() {
        viewModel.getCoinLast().observe(this, coinResource -> {
            if (coinResource != null) {
                switch (coinResource.status) {
                    case ERROR:
                        Toast.makeText(requireContext(), coinResource.message, Toast.LENGTH_LONG).show();
                        break;
                    case LOADING:
                        break;
                    case SUCCESS:
                        binding.setCoin(coinResource.data);
                        Log.d("DashboardFragment", "coin :: " + coinResource.data);
                        break;
                }
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        binding.chart.setPeriodIndex(tab.getPosition());
        viewModel.setPeriod(CoinRepository.HISTORY_PERIODS[tab.getPosition()]);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}