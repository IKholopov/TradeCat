package com.dreamteam.yamblz.tradecat.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamteam.yamblz.tradecat.R;
import com.dreamteam.yamblz.tradecat.data.DataService.CatPride;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by igor on 8/12/17.
 */

public class PrideAdapter extends RecyclerView.Adapter<PrideAdapter.ViewHoder> {

    private List<CatPride> prides;

    public PrideAdapter() {
        prides = new ArrayList<>();
        prides.add(CatPride.EASY);
        prides.add(CatPride.MEDIUM);
        prides.add(CatPride.HARD);
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return CatPride.COUNT;
    }

    static class ViewHoder extends RecyclerView.ViewHolder {
        
        @BindView(R.id.prideName) TextView prideName;

        public ViewHoder(View itemView) {
            super(itemView);

        }
    }
}
