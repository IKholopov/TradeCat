package com.dreamteam.yamblz.tradecat.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamteam.yamblz.tradecat.R;
import com.dreamteam.yamblz.tradecat.data.DataService;
import com.dreamteam.yamblz.tradecat.data.exception.NotEnoughMoneyException;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;


/**
 *
 */
public class ChartDetailsFragment extends Fragment {
    public static final String TAG = "ChartDetailsFragment";

    private final static int MAX_ENTRIES = 200;

    @BindView(R.id.graphView) GraphView graph;
    @BindView(R.id.amountToTrade) EditText amountToTrade;
    @BindView(R.id.sellCoin) Button sellButton;
    @BindView(R.id.buyCoin) Button buyButton;
    @BindView(R.id.currentCoin) TextView currentCoinView;
    @BindView(R.id.currentMoney) TextView currentMoneyView;

    private Observable<Double> priceEmitter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private Unbinder unbind;

    private LineGraphSeries<DataPoint> series;
    private DataService.CoinType coinType;

    public ChartDetailsFragment() {
        // Required empty public constructor
    }

    public static ChartDetailsFragment newInstance(@NonNull DataService.CoinType coinType) {
        ChartDetailsFragment fragment = new ChartDetailsFragment();
        fragment.coinType = coinType;

        fragment.series = new LineGraphSeries<>(generateData());    //TODO: CHANGE TO PASSED
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart_details, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedState) {
        unbind = ButterKnife.bind(this, view);
        this.priceEmitter = DataService.getInstance().getCoinTypeObservable(coinType);
        DataPoint[] points = {new DataPoint(0, 0)};
        this.series = new LineGraphSeries<>(points);
        graph.addSeries(series);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(false);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(40);
        disposable.add(priceEmitter.subscribe(newValue -> {
            series.appendData(new DataPoint(series.getHighestValueX() + 1, newValue), true, MAX_ENTRIES);
        }));
        updateTextViews();
        sellButton.setOnClickListener(button -> sell());
        buyButton.setOnClickListener(button -> buy());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        disposable.dispose();
        unbind.unbind();
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().setTitle(coinType.getName());
    }

    private void sell() {
        try {
            int toSell = Integer.valueOf(amountToTrade.getText().toString());
            if(toSell < 0) {
                throw new NumberFormatException();
            }
            DataService.getInstance().decrementCoin(coinType, toSell);
        } catch (NumberFormatException e) {
            Toast toast = Toast.makeText(getContext(), R.string.not_valid_cash, Toast.LENGTH_SHORT);
            toast.show();
        } catch (NotEnoughMoneyException e) {
            Toast toast = Toast.makeText(getContext(), R.string.not_enough_coins, Toast.LENGTH_SHORT);
            toast.show();
        }
        updateTextViews();
    }

    private void buy() {
        try {
            int toBuy = Integer.valueOf(amountToTrade.getText().toString());
            if(toBuy < 0) {
                throw new NumberFormatException();
            }
            DataService.getInstance().incrementCoin(coinType, toBuy);
        } catch (NumberFormatException e) {
            Toast toast = Toast.makeText(getContext(), R.string.not_valid_cash, Toast.LENGTH_SHORT);
            toast.show();
        } catch (NotEnoughMoneyException e) {
            Toast toast = Toast.makeText(getContext(), R.string.not_enough_cash, Toast.LENGTH_SHORT);
            toast.show();
        }
        updateTextViews();
    }

    private void updateTextViews() {
        currentCoinView.setText(String.valueOf(DataService.getInstance().getCoinCount(coinType)));
        currentMoneyView.setText(String.valueOf(Math.round(DataService.getInstance().getCurrentCash())));
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
