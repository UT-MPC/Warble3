package edu.utexas.mpc.warble3.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserDbDao {
    @Insert
    void insert(UserDb... userDbs);

    @Update
    void update(UserDb... userDbs);

    @Delete
    void delete(UserDb... userDbs);

    @Query("SELECT * FROM UserDb")
    List<UserDb> getAllUserDbs();

    @Query("DELETE FROM UserDb")
    void deleteAllUserDbs();
}
