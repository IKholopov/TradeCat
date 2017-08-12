package com.dreamteam.yamblz.tradecat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.dreamteam.yamblz.tradecat.ui.adapters.CoinsAdapter;
import com.dreamteam.yamblz.tradecat.ui.adapters.PrideAdapter;
import com.dreamteam.yamblz.tradecat.ui.graphlist.GraphListFragment;
import com.dreamteam.yamblz.tradecat.data.DataService;
import com.dreamteam.yamblz.tradecat.data.DataService.CoinType;
import com.dreamteam.yamblz.tradecat.data.DataService.CatPride;

import com.dreamteam.yamblz.tradecat.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.coinsRecycler)
    RecyclerView coinsRecycler;
    @BindView(R.id.prideRecycler)
    RecyclerView prideRecycler;
    @BindView(R.id.startGame)
    Button startGame;

    private final int container = R.id.container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        coinsRecycler.setAdapter(new CoinsAdapter());
        coinsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        prideRecycler.setAdapter(new PrideAdapter());
        prideRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataService.getInstance().init(((CoinsAdapter) coinsRecycler.getAdapter()).checkedTypes(),
                    ((PrideAdapter) prideRecycler.getAdapter()).checkedTypes());
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DataService.removeInstance();
    }
}
