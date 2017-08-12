package com.dreamteam.yamblz.tradecat.data;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Maksim Sukhotski on 8/12/2017.
 */

@android.arch.persistence.room.Dao
public interface Dao {
    @Query("SELECT * FROM stat")
    List<Statistics> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Statistics statistics);
}
