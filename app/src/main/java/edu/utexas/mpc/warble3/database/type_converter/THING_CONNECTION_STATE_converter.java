package edu.utexas.mpc.warble3.database.type_converter;

import android.arch.persistence.room.TypeConverter;

import edu.utexas.mpc.warble3.model.thing.component.THING_CONNECTION_STATE;

public class THING_CONNECTION_STATE_converter {
    @TypeConverter
    public static String toString(THING_CONNECTION_STATE thingConnectionState) {
        return thingConnectionState.toString();
    }

    @TypeConverter
    public static THING_CONNECTION_STATE toTHINGCONNECTIONSTATE(String string) {
        return THING_CONNECTION_STATE.valueOf(string);
    }
}
