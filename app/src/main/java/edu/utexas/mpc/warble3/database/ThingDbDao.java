package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ThingDbDao {
    @Insert
    public void insert(ThingDb... thingDbs);

    @Update
    public void update(ThingDb... thingDbs);

    @Delete
    public void delete(ThingDb... thingDbs);

    @Query("SELECT * FROM ThingDb")
    List<ThingDb> getAllThingDbs();

    @Query("DELETE FROM UserDb")
    void deleteAllThingDbs();

    @Query("SELECT * FROM ThingDb WHERE dbid=:dbid")
    UserDb getThingDb(long dbid);

    @Query("SELECT * FROM ThingDb WHERE uuid=:uuid")
    UserDb getThingDb(String uuid);
}
