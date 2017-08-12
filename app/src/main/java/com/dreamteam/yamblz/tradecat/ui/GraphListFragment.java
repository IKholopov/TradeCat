package com.dreamteam.yamblz.tradecat.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamteam.yamblz.tradecat.R;

import butterknife.ButterKnife;

/**
 * Created by Maksim Sukhotski on 8/12/2017.
 */

public class GraphListFragment extends Fragment {
    public static final String TAG = "GraphListFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_graph_list, container, false);
        ButterKnife.bind(this, v);
        return v;
    }
}
