package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface InteractionHistoryDbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(InteractionHistoryDb interactionHistoryDb);

    @Update
    void update(InteractionHistoryDb interactionHistoryDb);

    @Delete
    void delete(InteractionHistoryDb interactionHistoryDb);

    @Query("SELECT * FROM InteractionHistoryDb")
    List<InteractionHistoryDb> getInteractionHistoryDbs();

    @Query("DELETE FROM InteractionHistoryDb")
    void deleteAllInteractionHistoryDbs();
}
