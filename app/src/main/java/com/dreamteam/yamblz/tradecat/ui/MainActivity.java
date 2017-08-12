package com.dreamteam.yamblz.tradecat.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dreamteam.yamblz.tradecat.data.DataService;

import com.dreamteam.yamblz.tradecat.R;

public class MainActivity extends AppCompatActivity {

    private final int container = R.id.container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataService.CoinType[] coins = {DataService.CoinType.BTC, DataService.CoinType.ETH, DataService.CoinType.Gold};
        DataService.getInstance().init(coins, DataService.CatPride.HARD);
        setContentView(R.layout.activity_main);
        if (getSupportFragmentManager().findFragmentByTag(GraphListFragment.TAG) == null) {
            navigateToGraphList();
        }
    }

    public void navigateToGraphList() {
        ChartDetailsFragment fragment = ChartDetailsFragment.newInstance(DataService.CoinType.BTC);
        getSupportFragmentManager().beginTransaction()
                .replace(container, fragment, GraphListFragment.TAG)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }


}
