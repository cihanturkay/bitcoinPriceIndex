package cihan.samples.bitcoinpriceindex.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import javax.inject.Inject;

import cihan.samples.bitcoinpriceindex.R;
import cihan.samples.bitcoinpriceindex.databinding.ActivityMainBinding;
import cihan.samples.bitcoinpriceindex.main.utils.AppExecutors;
import cihan.samples.bitcoinpriceindex.ui.dashboard.DashboardFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Inject
    AppExecutors appExecutors;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainApp) getApplication()).getMainComponent().inject(MainActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.navigation.setOnNavigationItemSelectedListener(this);
        binding.navigation.setSelectedItemId(R.id.menu_main);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);

        switch (menuItem.getItemId()) {
            case R.id.menu_main:
                replaceFragment(DashboardFragment.newInstance(), false);
                break;
            case R.id.menu_list:
                replaceFragment(DashboardFragment.newInstance(), false);
                break;
        }
        return false;
    }

}
