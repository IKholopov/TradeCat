package com.dreamteam.yamblz.tradecat.ui.profile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamteam.yamblz.tradecat.R;
import com.dreamteam.yamblz.tradecat.data.Statistics;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Maksim Sukhotski on 4/22/2017.
 */

public class StatistcsRecyclerAdapter extends RecyclerView.Adapter<StatistcsRecyclerAdapter.RecyclerViewHolder> {
    private List<Statistics> list;

    public StatistcsRecyclerAdapter(List<Statistics> list) {
        this.list = list;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_statistics, null));
    }

    @Override
    public void onBindViewHolder(StatistcsRecyclerAdapter.RecyclerViewHolder holder, int position) {
        String[] strings = Instant.ofEpochSecond(list.get(position).getDate())
                .atZone(ZoneId.of(TimeZone.getDefault().getID()))
                .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)).split(" ");

        String[] time = strings[4].split(":");
        String readableDate = (strings[0] + " " + strings[1]).replace(",", "") + " " + time[0] + ":" + time[1];
        holder.tvDate.setText(String.valueOf(readableDate));
        holder.tvMoney.setText(String.valueOf(list.get(position).getMoney()));
        holder.tvTime.setText(String.valueOf(list.get(position).getTime()));
    }

    @Override
    public int getItemCount() {
//        return list.size();
        return list.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvMoney) TextView tvMoney;
        @BindView(R.id.tvDate) TextView tvDate;
        @BindView(R.id.tvTime) TextView tvTime;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
