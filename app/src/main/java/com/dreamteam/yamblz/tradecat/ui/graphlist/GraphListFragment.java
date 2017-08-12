package com.dreamteam.yamblz.tradecat.ui.graphlist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamteam.yamblz.tradecat.R;
import com.dreamteam.yamblz.tradecat.data.DataService;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import io.reactivex.Observable;

public class GraphListFragment extends Fragment {

    public static final String TAG = "graph_list_tag";

    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private LineGraphSeries<DataPoint> mSeries3;

    private DataService dataService;
    private Observable<Double> BTCemitter;
    private Observable<Double> USDemitter;
    private Observable<Double> RURemitter;

    private final static int MAX_ENTRIES = 50;
    private OnGraphClick onGraphClick;

    private DataService.CoinType[] coins;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onGraphClick = (OnGraphClick) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_list, container, false);
        setUpGraphs(rootView);
        return rootView;
    }

    private void setUpGraphs(View rootView) {
        GraphView graph = rootView.findViewById(R.id.graph);
        graph.setOnClickListener(view -> onGraphClick.onGraphClick(DataService.CoinType.BTC));
        mSeries1 = new LineGraphSeries<>();
        graph.addSeries(mSeries1);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(false);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);

        GraphView graph2 = rootView.findViewById(R.id.graph2);
        graph2.setOnClickListener(view -> onGraphClick.onGraphClick(DataService.CoinType.USD));
        mSeries2 = new LineGraphSeries<>();
        graph2.addSeries(mSeries2);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setScalable(true);
        graph2.getViewport().setScrollable(false);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(40);

        GraphView graph3 = rootView.findViewById(R.id.graph3);
        graph3.setOnClickListener(view -> onGraphClick.onGraphClick(DataService.CoinType.RUR));
        mSeries3 = new LineGraphSeries<>();
        graph3.addSeries(mSeries3);
        graph3.getViewport().setXAxisBoundsManual(true);
        graph3.getViewport().setScalable(true);
        graph3.getViewport().setScrollable(false);
        graph3.getViewport().setMinX(0);
        graph3.getViewport().setMaxX(40);

        dataService = DataService.getInstance();
        DataService.CoinType[] coinTypes = dataService.getCurrentTypes();
        BTCemitter = dataService.getCoinTypeObservable(coinTypes[0]);
        USDemitter = dataService.getCoinTypeObservable(coinTypes[1]);
        RURemitter = dataService.getCoinTypeObservable(coinTypes[2]);
        BTCemitter.subscribe(aDouble -> {
            mSeries1.appendData(new DataPoint(
                    mSeries1.getHighestValueX() + 1, aDouble), true, MAX_ENTRIES);
        });
        USDemitter.subscribe(aDouble -> {
            mSeries2.appendData(new DataPoint(
                    mSeries2.getHighestValueX() + 1, aDouble), true, MAX_ENTRIES);
        });
        RURemitter.subscribe(aDouble -> {
            mSeries3.appendData(new DataPoint(
                    mSeries3.getHighestValueX() + 1, aDouble), true, MAX_ENTRIES);
        });
    }

    private void setUpButtons(View rootView) {
        
    }

    public interface OnGraphClick {
        void onGraphClick(DataService.CoinType coinType);
    }
}
