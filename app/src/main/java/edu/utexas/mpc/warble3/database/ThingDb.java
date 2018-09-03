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

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import edu.utexas.mpc.warble3.database.type_converter.LocationConverter;
import edu.utexas.mpc.warble3.database.type_converter.THING_AUTHENTICATION_STATE_converter;
import edu.utexas.mpc.warble3.database.type_converter.THING_BINDING_STATE_converter;
import edu.utexas.mpc.warble3.database.type_converter.THING_CONCRETE_TYPE_converter;
import edu.utexas.mpc.warble3.database.type_converter.THING_CONNECTION_STATE_converter;
import edu.utexas.mpc.warble3.warble.thing.component.THING_AUTHENTICATION_STATE;
import edu.utexas.mpc.warble3.warble.thing.component.THING_BINDING_STATE;
import edu.utexas.mpc.warble3.warble.thing.component.THING_CONCRETE_TYPE;
import edu.utexas.mpc.warble3.warble.thing.component.THING_CONNECTION_STATE;
import edu.utexas.mpc.warble3.warble.thing.util.Location;

@Entity(tableName = "ThingDb")
public class ThingDb {
    @Ignore
    public static final String TAG = "ThingDb";

    @PrimaryKey (autoGenerate = true)
    private long dbid;

    private String name;
    private String friendlyName;

    private String uuid;

    private String thingClass;

    private String accessName;
    private String accessUsername;
    private String accessPasscode;

    private String manufacturerSerialNumber;
    private String manufacturerModelName;
    private String manufacturerModelNumber;
    private String manufacturerName;

    @TypeConverters(THING_CONCRETE_TYPE_converter.class)
    private THING_CONCRETE_TYPE thingConcreteType;

    private boolean isCredentialRequired;

    @TypeConverters(THING_CONNECTION_STATE_converter.class)
    private THING_CONNECTION_STATE connectionState;

    @TypeConverters(THING_AUTHENTICATION_STATE_converter.class)
    private THING_AUTHENTICATION_STATE authenticationState;

    @TypeConverters(THING_BINDING_STATE_converter.class)
    private THING_BINDING_STATE bindingState;

    @TypeConverters(LocationConverter.class)
    private Location location;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getThingClass() {
        return thingClass;
    }

    public void setThingClass(String thingClass) {
        this.thingClass = thingClass;
    }

    public String getAccessName() {
        return accessName;
    }

    public void setAccessName(String accessName) {
        this.accessName = accessName;
    }

    public String getAccessUsername() {
        return accessUsername;
    }

    public void setAccessUsername(String accessUsername) {
        this.accessUsername = accessUsername;
    }

    public String getAccessPasscode() {
        return accessPasscode;
    }

    public void setAccessPasscode(String accessPasscode) {
        this.accessPasscode = accessPasscode;
    }

    public String getManufacturerSerialNumber() {
        return manufacturerSerialNumber;
    }

    public void setManufacturerSerialNumber(String manufacturerSerialNumber) {
        this.manufacturerSerialNumber = manufacturerSerialNumber;
    }

    public String getManufacturerModelName() {
        return manufacturerModelName;
    }

    public void setManufacturerModelName(String manufacturerModelName) {
        this.manufacturerModelName = manufacturerModelName;
    }

    public String getManufacturerModelNumber() {
        return manufacturerModelNumber;
    }

    public void setManufacturerModelNumber(String manufacturerModelNumber) {
        this.manufacturerModelNumber = manufacturerModelNumber;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public THING_CONCRETE_TYPE getThingConcreteType() {
        return thingConcreteType;
    }

    public void setThingConcreteType(THING_CONCRETE_TYPE thingConcreteType) {
        this.thingConcreteType = thingConcreteType;
    }

    public boolean getCredentialRequired() {
        return isCredentialRequired;
    }

    public void setCredentialRequired(boolean credentialRequired) {
        isCredentialRequired = credentialRequired;
    }

    public THING_CONNECTION_STATE getConnectionState() {
        return connectionState;
    }

    public void setConnectionState(THING_CONNECTION_STATE connectionState) {
        this.connectionState = connectionState;
    }

    public THING_AUTHENTICATION_STATE getAuthenticationState() {
        return authenticationState;
    }

    public void setAuthenticationState(THING_AUTHENTICATION_STATE authenticationState) {
        this.authenticationState = authenticationState;
    }

    public THING_BINDING_STATE getBindingState() {
        return bindingState;
    }

    public void setBindingState(THING_BINDING_STATE bindingState) {
        this.bindingState = bindingState;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        String string = "";
        string += String.format("%s:%s %s-%s-%s", dbid, friendlyName,
                getConnectionState().toString().substring(0, 3),
                getAuthenticationState().toString().substring(0, 3),
                getBindingState().toString().substring(0, 3));
        return string;
    }
}
