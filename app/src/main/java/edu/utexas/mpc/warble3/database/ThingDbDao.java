package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.utexas.mpc.warble3.model.thing.component.THING_CONNECTION_STATE;

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

    @Query("SELECT dbid FROM ThingDb WHERE uuid=:uuid")
    long getDbidByUuid(String uuid);

    @Query("UPDATE ThingDb SET connectionState=:connectionState")
    void updateAllConnectionStates(THING_CONNECTION_STATE connectionState);
}
