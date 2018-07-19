package edu.utexas.mpc.warble3.database.converter;

import android.arch.persistence.room.TypeConverter;

import edu.utexas.mpc.warble3.model.thing.component.THING_CONCRETE_TYPE;

public class THING_CONCRETE_TYPE_converter {
    @TypeConverter
    public static String toString(THING_CONCRETE_TYPE thingConcreteType) {
        return thingConcreteType.toString();
    }

    @TypeConverter
    public static THING_CONCRETE_TYPE toTHINGCONCRETETYPE(String string) {
        return THING_CONCRETE_TYPE.valueOf(string);
    }
}
