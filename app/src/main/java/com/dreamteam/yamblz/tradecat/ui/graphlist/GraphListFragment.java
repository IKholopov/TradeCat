package com.dreamteam.yamblz.tradecat.ui.graphlist;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamteam.yamblz.tradecat.R;
import com.dreamteam.yamblz.tradecat.data.DataService;
import com.dreamteam.yamblz.tradecat.data.exception.NotEnoughMoneyException;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class GraphListFragment extends Fragment {

    public static final String TAG = "graph_list_tag";

    @BindView(R.id.count_textview) TextView count1;
    @BindView(R.id.count2_textview) TextView count2;
    @BindView(R.id.count3_textview) TextView count3;

    private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private LineGraphSeries<DataPoint> mSeries3;

    private DataService dataService;
    private Observable<Double> BTCemitter;
    private Observable<Double> USDemitter;
    private Observable<Double> RURemitter;

    private final static int MAX_ENTRIES = 50;
    private OnGraphClick onGraphClick;

    private DataService.CoinType[] coinTypes;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onGraphClick = (OnGraphClick) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_list, container, false);
        ButterKnife.bind(this, rootView);
        setUpGraphs(rootView);
        setUpButtons(rootView);
        return rootView;
    }

    private void setUpGraphs(View rootView) {

        dataService = DataService.getInstance();
        coinTypes = dataService.getCurrentTypes();
        BTCemitter = dataService.getCoinTypeObservable(coinTypes[0]);
        USDemitter = dataService.getCoinTypeObservable(coinTypes[1]);
        RURemitter = dataService.getCoinTypeObservable(coinTypes[2]);

        GraphView graph = rootView.findViewById(R.id.graph);
        graph.setOnClickListener(view -> onGraphClick.onGraphClick(coinTypes[0]));
        mSeries1 = new LineGraphSeries<>();
        graph.addSeries(mSeries1);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(false);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);

        GraphView graph2 = rootView.findViewById(R.id.graph2);
        graph2.setOnClickListener(view -> onGraphClick.onGraphClick(coinTypes[1]));
        mSeries2 = new LineGraphSeries<>();
        graph2.addSeries(mSeries2);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setScalable(true);
        graph2.getViewport().setScrollable(false);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(40);

        GraphView graph3 = rootView.findViewById(R.id.graph3);
        graph3.setOnClickListener(view -> onGraphClick.onGraphClick(coinTypes[2]));
        mSeries3 = new LineGraphSeries<>();
        graph3.addSeries(mSeries3);
        graph3.getViewport().setXAxisBoundsManual(true);
        graph3.getViewport().setScalable(true);
        graph3.getViewport().setScrollable(false);
        graph3.getViewport().setMinX(0);
        graph3.getViewport().setMaxX(40);


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
        (rootView.findViewById(R.id.arrow_up)).setOnClickListener(view -> {
            incrementAndHAndleException(coinTypes[0]);
            count1.setText(dataService.getCoinCount(coinTypes[0]) + "");
        });
        (rootView.findViewById(R.id.arrow_down)).setOnClickListener(view -> {
            decrementAndHAndleException(coinTypes[0]);
            count1.setText(dataService.getCoinCount(coinTypes[0]) + "");
        });
        (rootView.findViewById(R.id.arrow_up2)).setOnClickListener(view -> {
            incrementAndHAndleException(coinTypes[1]);
            count2.setText(dataService.getCoinCount(coinTypes[1]) + "");
        });
        (rootView.findViewById(R.id.arrow_down2)).setOnClickListener(view -> {
            decrementAndHAndleException(coinTypes[1]);
            count2.setText(dataService.getCoinCount(coinTypes[1]) + "");
        });
        (rootView.findViewById(R.id.arrow_up3)).setOnClickListener(view -> {
            incrementAndHAndleException(coinTypes[2]);
            count3.setText(dataService.getCoinCount(coinTypes[2]) + "");
        });
        (rootView.findViewById(R.id.arrow_down3)).setOnClickListener(view -> {
            decrementAndHAndleException(coinTypes[2]);
            count3.setText(dataService.getCoinCount(coinTypes[2]) + "");
        });
    }

    public interface OnGraphClick {
        void onGraphClick(DataService.CoinType coinType);
    }

    private void incrementAndHAndleException(DataService.CoinType coinType) {
        try {
            dataService.incrementCoin(coinType, 1);
        } catch (NotEnoughMoneyException e) {
            Toast.makeText(getActivity(), R.string.not_enough_cash, Toast.LENGTH_SHORT).show();
        }
    }

    private void decrementAndHAndleException(DataService.CoinType coinType) {
        try {
            dataService.decrementCoin(coinType, 1);
        } catch (NotEnoughMoneyException e) {
            Toast.makeText(getActivity(), R.string.not_enough_cash, Toast.LENGTH_SHORT).show();
        }
    }
}
