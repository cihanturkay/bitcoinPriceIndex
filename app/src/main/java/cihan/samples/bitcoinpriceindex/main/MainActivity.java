package cihan.samples.bitcoinpriceindex.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import cihan.samples.bitcoinpriceindex.R;
import cihan.samples.bitcoinpriceindex.databinding.ActivityMainBinding;
import cihan.samples.bitcoinpriceindex.main.utils.AppExecutors;
import cihan.samples.bitcoinpriceindex.ui.dashboard.DashboardFragment;

public class MainActivity extends AppCompatActivity {

    @Inject
    AppExecutors appExecutors;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApp) getApplication()).getMainComponent().inject(MainActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        replaceFragment(DashboardFragment.newInstance(), false);
    }


    private void replaceFragment(Fragment fragment, boolean addBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        //transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        transaction.replace(R.id.container, fragment);
        if (addBackStack)
            transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
}
