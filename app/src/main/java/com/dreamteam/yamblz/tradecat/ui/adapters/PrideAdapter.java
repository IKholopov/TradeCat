package com.dreamteam.yamblz.tradecat.ui.adapters;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dreamteam.yamblz.tradecat.R;
import com.dreamteam.yamblz.tradecat.data.DataService;
import com.dreamteam.yamblz.tradecat.data.DataService.CatPride;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by igor on 8/12/17.
 */

public class PrideAdapter extends RecyclerView.Adapter<PrideAdapter.TagWithSelectionViewHolder> {

    List<Pair<CatPride, Boolean>> coins = new ArrayList<>();


    public PrideAdapter() {
        coins.add(new Pair<>(CatPride.EASY, false));
        coins.add(new Pair<>(CatPride.MEDIUM, false));
        coins.add(new Pair<>(CatPride.HARD, false));
    }

    @Override
    public TagWithSelectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_coin_view,
            parent, false);
        return new TagWithSelectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagWithSelectionViewHolder holder, final int position) {
        final Pair<CatPride, Boolean> tag = coins.get(position);

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
        return DataService.CatPride.values().length;
    }

    public DataService.CatPride[] checkedTypes() {
        DataService.CatPride[] coinTypes = new DataService.CatPride[1];
        int size = 0;
        for (Pair<CatPride, Boolean> coinType : coins) {
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
