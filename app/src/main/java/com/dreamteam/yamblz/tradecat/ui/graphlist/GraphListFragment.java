package com.dreamteam.yamblz.tradecat.ui.graphlist;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_list, container, false);

        GraphView graph = rootView.findViewById(R.id.graph);
        mSeries1 = new LineGraphSeries<>();
        graph.addSeries(mSeries1);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(false);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);

        GraphView graph2 = rootView.findViewById(R.id.graph2);
        mSeries2 = new LineGraphSeries<>();
        graph2.addSeries(mSeries2);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setScalable(true);
        graph2.getViewport().setScrollable(false);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(40);

        GraphView graph3 = rootView.findViewById(R.id.graph3);
        mSeries3 = new LineGraphSeries<>();
        graph3.addSeries(mSeries3);
        graph3.getViewport().setXAxisBoundsManual(true);
        graph3.getViewport().setScalable(true);
        graph3.getViewport().setScrollable(false);
        graph3.getViewport().setMinX(0);
        graph3.getViewport().setMaxX(40);

        dataService = DataService.getInstance();
        dataService.init(new DataService.CoinType[] {DataService.CoinType.BTC,
                DataService.CoinType.USD, DataService.CoinType.RUR}, DataService.CatPride.MEDIUM);
        BTCemitter = dataService.getCoinTypeObservable(DataService.CoinType.BTC);
        USDemitter = dataService.getCoinTypeObservable(DataService.CoinType.USD);
        RURemitter = dataService.getCoinTypeObservable(DataService.CoinType.RUR);
        BTCemitter.subscribe(aDouble -> {
            mSeries1.appendData(new DataPoint(mSeries1.getHighestValueX() + 1, aDouble), true, 40);
        });
        USDemitter.subscribe(aDouble -> {
            mSeries2.appendData(new DataPoint(mSeries2.getHighestValueX() + 1, aDouble), true, 40);
        });
        RURemitter.subscribe(aDouble -> {
            mSeries3.appendData(new DataPoint(mSeries3.getHighestValueX() + 1, aDouble), true, 40);
        });
        return rootView;
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        mTimer1 = new Runnable() {
//            @Override
//            public void run() {
//                mSeries1.resetData(generateData());
//                mHandler.postDelayed(this, 300);
//            }
//        };
//        mHandler.postDelayed(mTimer1, 300);
//
//        mTimer2 = new Runnable() {
//            @Override
//            public void run() {
//                graph2LastXValue += 1d;
//                mSeries2.appendData(new DataPoint( graph2LastXValue, getRandom()), true, 40);
//                mHandler.postDelayed(this, 200);
//            }
//        };
//        mHandler.postDelayed(mTimer2, 1000);
//
//        mTimer3 = new Runnable() {
//            @Override
//            public void run() {
//                graph2LastXValue += 1d;
//                mSeries3.appendData(new DataPoint( graph2LastXValue, getRandom()), true, 40);
//                mHandler.postDelayed(this, 200);
//            }
//        };
//        mHandler.postDelayed(mTimer3, 1000);
//    }
//
//    @Override
//    public void onPause() {
//        mHandler.removeCallbacks(mTimer1);
//        mHandler.removeCallbacks(mTimer2);
//        mHandler.removeCallbacks(mTimer3);
//        super.onPause();
//    }
//
//    private DataPoint[] generateData() {
//        int count = 30;
//        DataPoint[] values = new DataPoint[count];
//        for (int i=0; i<count; i++) {
//            double x = i;
//            double f = mRand.nextDouble()*0.15+0.3;
//            double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
//            DataPoint v = new DataPoint(x, y);
//            values[i] = v;
//        }
//        return values;
//    }
//
//    double mLastRandom = 2;
//    Random mRand = new Random();
//    private double getRandom() {
//        return mLastRandom += mRand.nextDouble()*0.5 - 0.25;
//    }
}
