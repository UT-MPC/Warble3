package edu.utexas.mpc.warble3.database.type_converter;

import android.arch.persistence.room.TypeConverter;

import edu.utexas.mpc.warble3.model.thing.component.THING_AUTHENTICATION_STATE;

public class THING_AUTHENTICATION_STATE_converter {
    @TypeConverter
    public static String toString(THING_AUTHENTICATION_STATE thingAuthenticationState) {
        return thingAuthenticationState.toString();
    }

    @TypeConverter
    public static THING_AUTHENTICATION_STATE toTHINGAUTHENTICATIONSTATE(String string) {
        return THING_AUTHENTICATION_STATE.valueOf(string);
    }
}
