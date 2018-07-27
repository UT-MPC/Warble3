package edu.utexas.mpc.warble3.model.thing.credential;

import edu.utexas.mpc.warble3.database.interfaces.Storeable;
import edu.utexas.mpc.warble3.model.thing.component.Thing;
import edu.utexas.mpc.warble3.model.user.User;

public abstract class ThingAccessCredential implements Storeable {
    public static final String TAG = "ThingAccessCredential";

    private User user;
    private Thing thing;

    private long dbid;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    public long getDbid() {
        return dbid;
    }

    private void setDbid(long dbid) {
        this.dbid = dbid;
    }

    @Override
    public void onPostStore(long dbid) {
        setDbid(dbid);
    }
}
