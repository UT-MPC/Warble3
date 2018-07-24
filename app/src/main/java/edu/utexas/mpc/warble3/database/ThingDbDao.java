package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ThingDbDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    long insert(ThingDb thingDb);

    @Update
    void update(ThingDb thingDb);

    @Delete
    void delete(ThingDb thingDb);

    @Query("SELECT * FROM ThingDb")
    List<ThingDb> getAllThingDbs();

    @Query("DELETE FROM ThingDb")
    void deleteAllThingDbs();

    @Query("SELECT * FROM ThingDb WHERE dbid=:dbid")
    ThingDb getThingDb(long dbid);

    @Query("SELECT * FROM ThingDb WHERE uuid=:uuid")
    ThingDb getThingDbByUuid(String uuid);
}
