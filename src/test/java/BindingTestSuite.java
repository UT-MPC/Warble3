import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import context.Location;
import correlation.DBinding;
import correlation.Plan;
import selector.NearestThingSelector;
import selector.Selector;
import selector.ThingConcreteTypeSelector;
import thing.component.Light;
import thing.component.THING_CONCRETE_TYPE;
import thing.component.Thermostat;
import thing.component.Thing;
import thing.component.ThingState;

public class BindingTestSuite {
    @Test
    public void useNative() {
        Location myLoc = new Location(100, 100, 0, 0, 0);

        while (true) {

        }
    }

    @Test
    public void useOneTimeBind() {
        Location myLoc = new Location(100, 100, 0, 0, 0);

        Warble warble = new Warble(); //initiates discovery

        List<Selector> template = new ArrayList<>();
        template.add(new ThingConcreteTypeSelector(
                THING_CONCRETE_TYPE.LIGHT,
                THING_CONCRETE_TYPE.THERMOSTAT));
        template.add(new NearestThingSelector(myLoc));

        HashMap<String, ThingState> oriThingStates = new HashMap<>();
        while (true) {
            HashMap<String, ThingState> newOriThingStates = new HashMap<>();

            for (Thing thing : warble.fetch(template, 3)) {
                if (oriThingStates.containsKey(thing.getUuid())) {
                    newOriThingStates.put(thing.getUuid(), oriThingStates.get(thing.getUuid()));
                    oriThingStates.remove(thing.getUuid());
                } else {
                    newOriThingStates.put(thing.getUuid(), thing.getThingState());
                }

                if (thing instanceof Light) {
                    Light light = (Light) thing;
                    // light.on(); //simplified, Command Pattern used
                } else if (thing instanceof Thermostat) {
                    Thermostat thermostat = (Thermostat) thing;
                    // thermostat.setTemperature(298); //in Kelvin
                }

                for (String uuid : oriThingStates.keySet()) {
                    Thing retThing = warble.fetch(uuid);
                    // retThing.setThingState(oriThingStates.get(uuid)); //simplified, Command Pattern used
                }

                oriThingStates = newOriThingStates;
            }
        }
    }

    @Test
    public void useDynamicBind() {
        Location myLoc = new Location(100, 100, 0, 0, 0);

        Warble warble = new Warble(); //initiates discovery

        List<Selector> template = new ArrayList<>();
        template.add(new ThingConcreteTypeSelector(
                THING_CONCRETE_TYPE.LIGHT,
                THING_CONCRETE_TYPE.THERMOSTAT));
        template.add(new NearestThingSelector(myLoc));

        Plan plan = new Plan();
        plan.set(Plan.Key.LIGHTING_ON, true);
        plan.set(Plan.Key.AMBIENT_TEMPERATURE, 298);

        DBinding dBind = warble.dynamicBind(template, 3, plan);
        dBind.start();
    }
}
