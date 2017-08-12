package com.dreamteam.yamblz.tradecat;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.dreamteam.yamblz.tradecat.data.Db;
import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Created by Maksim Sukhotski on 8/12/2017.
 */

public class App extends Application {

    private static Db db;

    @Override
    public void onCreate() {
        db = Room.databaseBuilder(getApplicationContext(),
                Db.class, "database-name").build();
        AndroidThreeTen.init(this);
        super.onCreate();
    }

    public static synchronized Db getDb() {
        return db;
    }
}
