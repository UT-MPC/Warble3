package correlation;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import selector.Selector;
import thing.component.Thing;
import thing.component.ThingState;
import warble.Warble;

public class DBinding extends Binding {
    private Warble warble;
    private List<Selector> template;
    private int numberOfThings;
    private Plan plan;
    private HashMap<Thing, ThingState> originalStateMap;            // Store the original state of the things first
    private DBindingThread dBindingThread;

    public DBinding(Warble warble, List<Selector> template, int numberOfThings) {
        this.warble = warble;
        this.template = template;
        this.numberOfThings = numberOfThings;
    }

    public DBinding(Warble warble, List<Selector> template, int numberOfThings, Plan plan) {
        this.warble = warble;
        this.template = template;
        this.numberOfThings = numberOfThings;
        bind(plan);
    }

    public void bind(Plan plan) {
        bind(plan, false);
    }

    public void bind(Plan plan, boolean refreshOriginalState) {
        this.plan = plan;

        // TODO: DBinding should consider putting the thing to original state when the thing is not bound anymore

        dBindingThread = new DBindingThread(this.warble, this.template, this.numberOfThings, this.plan);

        stop();
        try {
            TimeUnit.SECONDS.sleep(DBindingThread.pollIntervalInSeconds * 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        start();
    }

    public void start() {
        dBindingThread.run();
    }

    public void stop() {
        dBindingThread.stop();
    }
}
