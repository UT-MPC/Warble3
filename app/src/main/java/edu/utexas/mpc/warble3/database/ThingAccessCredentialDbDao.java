package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

@Dao
public interface ThingAccessCredentialDbDao {
    @Insert
    void insert(ThingAccessCredentialDb... thingAccessCredentialDbs);

    @Update
    void update(ThingAccessCredentialDb... thingAccessCredentialDbs);

    @Delete
    void delete(ThingAccessCredentialDb... thingAccessCredentialDbs);
}
