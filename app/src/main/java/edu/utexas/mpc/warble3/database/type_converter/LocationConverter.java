package edu.utexas.mpc.warble3.database.type_converter;

import android.arch.persistence.room.TypeConverter;

import edu.utexas.mpc.warble3.warble.thing.util.Location;

public class LocationConverter {
    @TypeConverter
    public static String toString(Location location) {
        if (location == null) {
            return null;
        }
        else {
            return location.toString();
        }
    }

    @TypeConverter
    public static Location toLocation(String locationString) {
        return Location.fromString(locationString);
    }
}
