package com.dreamteam.yamblz.tradecat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dreamteam.yamblz.tradecat.ui.graphlist.GraphListFragment;

public class MainActivity extends AppCompatActivity {

    private final int container = R.id.container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportFragmentManager().findFragmentByTag(GraphListFragment.TAG) == null) {
            navigateToGraphList();
        }
    }

    public void navigateToGraphList() {
        GraphListFragment fragment = new GraphListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(container, fragment, GraphListFragment.TAG)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
