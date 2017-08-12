package com.dreamteam.yamblz.tradecat.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Maksim Sukhotski on 8/12/2017.
 */

@Database(entities = {Statistics.class}, version = 1)
public abstract class Db extends RoomDatabase {
    public abstract Dao dao();
}