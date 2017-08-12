package com.dreamteam.yamblz.tradecat;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;


/**
 *
 */
public class ChartDetailsFragment extends Fragment {

    private final static int MAX_ENTRIES = 12;

    @BindView(R.id.graphView) GraphView graph;
    @BindView(R.id.amountToTrade) EditText amountToTrade;
    @BindView(R.id.sellCoin) Button sellButton;
    @BindView(R.id.buyCoin) Button buyButton;
    @BindView(R.id.currentCoin) TextView currentCoinView;
    @BindView(R.id.currentMoney) TextView currentMoneyView;

    private Observable<Double> priceEmitter;
    private CompositeDisposable disposable = new CompositeDisposable();

    private LineGraphSeries<DataPoint> series;

    public ChartDetailsFragment() {
        // Required empty public constructor
    }

    public static ChartDetailsFragment newInstance(@NonNull Observable<Double> priceEmitter,
                                                   @NonNull DataPoint[] initialData) {
        ChartDetailsFragment fragment = new ChartDetailsFragment();
        fragment.priceEmitter = priceEmitter;
        fragment.series = new LineGraphSeries<>(generateData());    //TODO: CHANGE TO PASSED
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chart_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedState) {
        ButterKnife.bind(this, view);
        graph.addSeries(series);
        disposable.add(priceEmitter.subscribe(newValue -> {
            series.appendData(new DataPoint(series.getHighestValueX() + 1, newValue), true, 12);
        }));
        updateTextViews();
        sellButton.setOnClickListener(button -> sell());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        disposable.dispose();
    }

    private void sell() {
        updateTextViews();
        clearTrade();
    }

    private void buy() {
        updateTextViews();
        clearTrade();
    }

    private void clearTrade() {
        amountToTrade.setText("");
    }

    private void updateTextViews() {
        currentCoinView.setText(String.valueOf(10));//insatnce));
        currentMoneyView.setText(String.valueOf(10));//insatnce));
        amountToTrade.setText(String.valueOf(0));
    }

    //DEL
    private static DataPoint[] generateData() {
        int count = 30;
        Random mRand = new Random();
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double x = i;
            double f = mRand.nextDouble()*0.15+0.3;
            double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }

}
