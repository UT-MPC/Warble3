package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ConnectionDbDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    long insert(ConnectionDb connectionDb);

    @Update
    void update(ConnectionDb connectionDb);

    @Delete
    void delete(ConnectionDb connectionDb);

    @Query("SELECT * FROM ConnectionDb")
    List<ConnectionDb> getAllConnectionDbs();

    @Query("SELECT * FROM ConnectionDb WHERE sourceId=:sourceId")
    List<ConnectionDb> getConnectionDbBySourceId(long sourceId);

    @Query("SELECT * FROM ConnectionDb WHERE destinationId=:destinationId")
    List<ConnectionDb> getConnectionDbByDestinationId(long destinationId);

    @Query("SELECT * FROM ConnectionDb WHERE sourceId=:sourceId AND destinationId=:destinationId")
    List<ConnectionDb> getConnectionDbBySourceDestinationId(long sourceId, long destinationId);

    @Query("SELECT * FROM ConnectionDb WHERE connectionInfo=:connectionInfo")
    List<ConnectionDb> getConnectionDbByInfo(String connectionInfo);
}
