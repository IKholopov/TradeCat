package com.dreamteam.yamblz.tradecat.ui.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.dreamteam.yamblz.tradecat.App;
import com.dreamteam.yamblz.tradecat.R;
import com.dreamteam.yamblz.tradecat.data.DataService;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.dreamteam.yamblz.tradecat.data.DataService.CatPride.MEDIUM;
import static com.dreamteam.yamblz.tradecat.data.DataService.CoinType.BTC;
import static com.dreamteam.yamblz.tradecat.data.DataService.CoinType.RUR;
import static com.dreamteam.yamblz.tradecat.data.DataService.CoinType.USD;

/**
 * Created by Maksim Sukhotski on 8/12/2017.
 */

public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";

    @BindView(R.id.recycler_view_history) RecyclerView recyclerView;
    @BindView(R.id.rating_bar) RatingBar ratingBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DataService.getInstance().init(new DataService.CoinType[]{BTC, RUR, USD}, MEDIUM);
        DataService.getInstance().getCatLifecycleObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> ratingBar.setNumStars(integer),
                        this::saveStatistics);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Single.fromCallable(() -> App.getDb().dao().getAll())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(statisticses -> recyclerView.setAdapter(new StatistcsRecyclerAdapter(statisticses)));
    }

    private void saveStatistics(Throwable throwable) {
        Completable.fromAction(() -> App.getDb().dao().insert(DataService.getInstance().getStatistics()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}
