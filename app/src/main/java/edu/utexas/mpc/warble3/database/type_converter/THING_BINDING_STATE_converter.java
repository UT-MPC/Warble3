package edu.utexas.mpc.warble3.database.type_converter;

import android.arch.persistence.room.TypeConverter;

import edu.utexas.mpc.warble3.model.thing.component.THING_BINDING_STATE;

public class THING_BINDING_STATE_converter {
    @TypeConverter
    public static String toString(THING_BINDING_STATE thingBindingState) {
        return thingBindingState.toString();
    }

    @TypeConverter
    public static THING_BINDING_STATE toTHINGBINDINGSTATE(String string) {
        return THING_BINDING_STATE.valueOf(string);
    }
}
