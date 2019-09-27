package edu.utexas.mpc.warble3.warble.correlation;

import edu.utexas.mpc.warble3.warble.plan.Plan;

public class DBinding extends Binding {
    private static String TAG = "DBinding";

    private Plan plan;

    public DBinding() {
    }

    public Plan getPlan() {
        return plan;
    }

    public void bind(Plan plan) {
        this.plan = plan;
    }
}
