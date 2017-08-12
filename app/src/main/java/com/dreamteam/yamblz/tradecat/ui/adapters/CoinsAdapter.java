package com.dreamteam.yamblz.tradecat.ui.adapters;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

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

public class CoinsAdapter extends Adapter<CoinsAdapter.TagWithSelectionViewHolder> {

    List<Pair<CoinType, Boolean>> coins = new ArrayList<>();


    public CoinsAdapter() {
        coins.add(new Pair<>(CoinType.BTC, false));
        coins.add(new Pair<>(CoinType.ETC, false));
        coins.add(new Pair<>(CoinType.ETH, false));
        coins.add(new Pair<>(CoinType.Gasoline, false));
        coins.add(new Pair<>(CoinType.Gasoline, false));
        coins.add(new Pair<>(CoinType.GPY, false));
        coins.add(new Pair<>(CoinType.USD, false));
        coins.add(new Pair<>(CoinType.RUR, false));
        coins.add(new Pair<>(CoinType.LTC, false));
    }

    @Override
    public TagWithSelectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_coin_view,
                parent, false);
        return new TagWithSelectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagWithSelectionViewHolder holder, final int position) {
        final Pair<CoinType, Boolean> tag = coins.get(position);

        holder.setName(tag.first.getName());
        holder.setChecked(tag.second);

        holder.setOnClickedListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                coins.set(position, new Pair<>(tag.first, isChecked));
            }
        });
    }

    @Override
    public int getItemCount() {
        return CoinType.values().length;
    }

    public CoinType[] checkedTypes() {
        CoinType[] coinTypes = new CoinType[3];
        int size = 0;
        for (Pair<CoinType, Boolean> coinType : coins) {
            if (coinType.second) {
                coinTypes[size] = coinType.first;
                size++;
            }
        }
        return coinTypes;
    }

    // -------------------------------------- inner types -------------------------------------------

    static class TagWithSelectionViewHolder extends RecyclerView.ViewHolder {

        private TextView textName;
        private ToggleButton toggleButton;
        private CompoundButton.OnCheckedChangeListener listener;

        private TagWithSelectionViewHolder(View view) {
            super(view);
            toggleButton = view.findViewById(R.id.toggle_button);
            textName = view.findViewById(R.id.text_name);
        }

        private void setName(String name) {
            textName.setText(name);
        }

        private void setChecked(boolean checked) {
            toggleButton.setOnCheckedChangeListener(null);
            toggleButton.setChecked(checked);
            setListenerLocal();
        }

        private void setOnClickedListener(CompoundButton.OnCheckedChangeListener listener) {
            this.listener = listener;
            setListenerLocal();
        }

        private void setListenerLocal() {
            toggleButton.setOnCheckedChangeListener(listener);
        }
    }
}
