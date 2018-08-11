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
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.utexas.mpc.warble3.database.type_converter.THING_AUTHENTICATION_STATE_converter;
import edu.utexas.mpc.warble3.database.type_converter.THING_BINDING_STATE_converter;
import edu.utexas.mpc.warble3.database.type_converter.THING_CONNECTION_STATE_converter;
import edu.utexas.mpc.warble3.model.thing.component.THING_AUTHENTICATION_STATE;
import edu.utexas.mpc.warble3.model.thing.component.THING_BINDING_STATE;
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
    @TypeConverters(THING_CONNECTION_STATE_converter.class)
    void updateAllConnectionStates(THING_CONNECTION_STATE connectionState);

    @Query("UPDATE ThingDb SET authenticationState=:authenticationState")
    @TypeConverters(THING_AUTHENTICATION_STATE_converter.class)
    void updateAllAuthenticationStates(THING_AUTHENTICATION_STATE authenticationState);

    @Query("UPDATE ThingDb SET bindingState=:bindingState")
    @TypeConverters(THING_BINDING_STATE_converter.class)
    void updateAllBindingStates(THING_BINDING_STATE bindingState);
}
