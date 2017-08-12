package com.dreamteam.yamblz.tradecat.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Maksim Sukhotski on 8/12/2017.
 */
@Entity(tableName = "stat")
public class Statistics {
    @PrimaryKey
    private long time;
    private long date;
    private double money;

    public Statistics(long time, long date, double money) {
        this.time = time;
        this.date = date;
        this.money = money;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
