/*
 * MIT License
 *
 * Copyright (c) 2018 Yosef Saputra
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

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

    @Query("DELETE FROM ConnectionDb")
    void deleteAllConnectionDbs();

    @Query("SELECT * FROM CONNECTIONDB WHERE dbid=:dbid")
    ConnectionDb getConnectionDbByDbid(long dbid);

    @Query("SELECT * FROM ConnectionDb WHERE sourceId=:sourceId")
    List<ConnectionDb> getConnectionDbBySourceId(long sourceId);

    @Query("SELECT * FROM ConnectionDb WHERE destinationId=:destinationId")
    List<ConnectionDb> getConnectionDbByDestinationId(long destinationId);

    @Query("SELECT * FROM ConnectionDb WHERE sourceId=:sourceId AND destinationId=:destinationId")
    List<ConnectionDb> getConnectionDbBySourceDestinationId(long sourceId, long destinationId);

    @Query("SELECT * FROM ConnectionDb WHERE connectionInfo=:connectionInfo")
    List<ConnectionDb> getConnectionDbByInfo(String connectionInfo);
}
