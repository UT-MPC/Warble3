package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Update;

@Dao
public interface ThingAccessCredentialDbDao {
    @Insert
    public void insert(ThingAccessCredentialDb... thingAccessCredentialDbs);

    @Update
    public void update(ThingAccessCredentialDb... thingAccessCredentialDbs);

    @Delete
    public void delete(ThingAccessCredentialDb... thingAccessCredentialDbs);
}
