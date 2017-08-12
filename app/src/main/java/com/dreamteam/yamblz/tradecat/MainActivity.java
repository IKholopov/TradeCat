package com.dreamteam.yamblz.tradecat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dreamteam.yamblz.tradecat.ui.graphlist.GraphListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new GraphListFragment())
                .commit();
    }
}
