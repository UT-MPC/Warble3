package correlation;

import java.util.List;

import selector.Selector;
import thing.component.Thing;
import thing.component.ThingType;
import warble.Warble;

public class DBindingThread implements Runnable {
    public static int pollIntervalInSeconds = 1;
    private Warble warble;
    private List<Selector> template;
    private int numberOfThings;
    private Plan plan;
    private volatile boolean stopFlag = false;

    public DBindingThread(Warble warble, List<Selector> template, Plan plan) {
        this(warble, template, -1, plan);
    }

    public DBindingThread(Warble warble, List<Selector> template, int numberOfThings, Plan plan) {
        this.warble = warble;
        this.template = template;
        this.numberOfThings = numberOfThings;
        this.plan = plan;
    }

    @Override
    public void run() {
        if (warble == null) {
            return;
        }

        while (!stopFlag) {
            // TODO: what to do each loop

            for (Thing thing : warble.fetch(template)) {
                applyPlan(thing);
            }
        }
        stopFlag = false;
    }

    public void stop() {
        stopFlag = true;
    }

    private void applyPlan(Thing thing) {
        for (ThingType thingType : thing.getThingTypes()) {
            // TODO: apply the algorithm on how to apply the plan to a thing
        }
    }
}
