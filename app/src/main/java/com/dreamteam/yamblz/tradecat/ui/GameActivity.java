package com.dreamteam.yamblz.tradecat.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dreamteam.yamblz.tradecat.R;
import com.dreamteam.yamblz.tradecat.data.DataService;
import com.dreamteam.yamblz.tradecat.ui.graphlist.*;
import com.dreamteam.yamblz.tradecat.ui.graphlist.GraphListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends AppCompatActivity {

    private final int container = R.id.container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataService.CoinType[] coins = {DataService.CoinType.BTC, DataService.CoinType.ETH, DataService.CoinType.Gold};
        DataService.getInstance().init(coins, DataService.CatPride.HARD);
        setContentView(R.layout.activity_game);
        if (getSupportFragmentManager().findFragmentByTag(GraphListFragment.TAG) == null) {
            navigateToGraphList();
        }
    }

    public void navigateToGraphList() {
        GraphListFragment fragment = new GraphListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(container, fragment, com.dreamteam.yamblz.tradecat.ui.graphlist.GraphListFragment.TAG)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

}
