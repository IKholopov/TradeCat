package com.dreamteam.yamblz.tradecat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.dreamteam.yamblz.tradecat.ui.adapters.CoinsAdapter;
import com.dreamteam.yamblz.tradecat.data.DataService;
import com.dreamteam.yamblz.tradecat.data.DataService.CoinType;
import com.dreamteam.yamblz.tradecat.data.DataService.CatPride;

import com.dreamteam.yamblz.tradecat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.coinsRecycler) RecyclerView coinsRecycler;
    @BindView(R.id.prideRecycler) RecyclerView prideRecycler;
    @BindView(R.id.startGame) Button start;

    private final int container = R.id.container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        coinsRecycler.setAdapter(new CoinsAdapter());
        start.setOnClickListener(button -> startGame());
    }

    @Override
    public void onResume() {
        super.onResume();
        DataService.removeInstance();
    }

    private void startGame() {
        CoinType[] coins = {CoinType.BTC, CoinType.ETH, CoinType.RUR};
        CatPride pride = CatPride.EASY;
        DataService.getInstance().init(coins, pride);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
