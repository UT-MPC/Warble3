package correlation;

import java.util.HashMap;

public class Plan {
    private HashMap<Key, Object> settings = new HashMap<>();

    public Object get(Key key) {
        return settings.get(key);
    }

    public void set(Key key, Object val) {
        settings.put(key, val);
    }

    public enum Key {
        LIGHTING_ON,
        LIGHTING_COLOR,
        LIGHTING_BRIGHTNESS,

        AMBIENT_TEMPERATURE,
        AMBIENT_HUMIDITY
    }
}