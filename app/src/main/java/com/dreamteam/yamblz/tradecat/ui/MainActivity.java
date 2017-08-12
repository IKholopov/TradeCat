package com.dreamteam.yamblz.tradecat.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dreamteam.yamblz.tradecat.R;
import com.dreamteam.yamblz.tradecat.ui.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private final int containerMain = R.id.container;
    private final int containerBottomSheet = R.id.container_bottom_sheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportFragmentManager().findFragmentByTag(GraphListFragment.TAG) == null) {
            navigateToGraphList();
            navigateToProfile();
        }
    }

    public void navigateToGraphList() {
        GraphListFragment fragment = new GraphListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(containerMain, fragment, GraphListFragment.TAG)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    public void navigateToProfile() {
        ProfileFragment fragment = new ProfileFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(containerBottomSheet, fragment, ProfileFragment.TAG)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }


}
