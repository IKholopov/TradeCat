package com.dreamteam.yamblz.tradecat.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamteam.yamblz.tradecat.R;
import com.dreamteam.yamblz.tradecat.data.DataService.CatPride;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by igor on 8/12/17.
 */

public class PrideAdapter extends RecyclerView.Adapter<PrideAdapter.ViewHolder> {

    private List<CatPride> prides;

    public PrideAdapter() {
        prides = new ArrayList<>();
        prides.add(CatPride.EASY);
        prides.add(CatPride.MEDIUM);
        prides.add(CatPride.HARD);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_pride_view,
                parent, false);;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.prideName.setText(prides.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return CatPride.COUNT;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.prideName) TextView prideName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
