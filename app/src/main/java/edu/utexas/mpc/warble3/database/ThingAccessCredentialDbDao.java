package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ThingAccessCredentialDbDao {
    @Insert
    long insert(ThingAccessCredentialDb thingAccessCredentialDb);

    @Update
    void update(ThingAccessCredentialDb thingAccessCredentialDb);

    @Delete
    void delete(ThingAccessCredentialDb thingAccessCredentialDb);

    @Query("SELECT * FROM ThingAccessCredentialDb")
    List<ThingAccessCredentialDb> getAllThingAccessCredentialDbs();

    @Query("DELETE FROM ThingAccessCredentialDb")
    void deleteAllThingAccessCredentialDbs();

    @Query("SELECT * FROM ThingAccessCredentialDb WHERE dbid=:dbid")
    ThingAccessCredentialDb getThingAccessCredentialDbByDbid(long dbid);

    @Query("SELECT * FROM ThingAccessCredentialDb WHERE thingId=:thingId")
    List<ThingAccessCredentialDb> getThingAccessCredentialDbsByThingId(long thingId);
}
