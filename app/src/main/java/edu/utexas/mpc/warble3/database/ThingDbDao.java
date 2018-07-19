package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

@Dao
public interface ThingDbDao {
    @Insert
    public void insert(ThingDb... thingDbs);

    @Update
    public void update(ThingDb... thingDbs);

    @Delete
    public void delete(ThingDb... thingDbs);
}
