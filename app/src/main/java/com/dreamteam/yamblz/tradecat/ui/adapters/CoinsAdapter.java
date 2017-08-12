package com.dreamteam.yamblz.tradecat.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamteam.yamblz.tradecat.R;
import com.dreamteam.yamblz.tradecat.data.DataService;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.dreamteam.yamblz.tradecat.data.DataService.CoinType;

/**
 * Created by igor on 8/12/17.
 */

public class CoinsAdapter extends Adapter<CoinsAdapter.ViewHolder> {

    List<DataService.CoinType> coins = new ArrayList<>();


    public CoinsAdapter() {
        coins.add(CoinType.BTC);
        coins.add(CoinType.ETC);
        coins.add(CoinType.ETH);
        coins.add(CoinType.Gasoline);
        coins.add(CoinType.Gold);
        coins.add(CoinType.GPY);
        coins.add(CoinType.USD);
        coins.add(CoinType.RUR);
        coins.add(CoinType.LTC);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_coin_view,
                parent, false);;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.coinName.setText(coins.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return DataService.CoinType.COUNT;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.coinName) TextView coinName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
