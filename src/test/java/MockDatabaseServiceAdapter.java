import interaction.Interaction;
import service.BaseDatabaseServiceAdapter;
import thing.component.Thing;
import thing.connection.Connection;
import thing.credential.ThingAccessCredential;
import user.User;
import vendor.PhilipsHue.component.PhilipsHueBridge;
import vendor.PhilipsHue.component.PhilipsHueLight;

import java.util.List;

public class MockDatabaseServiceAdapter extends BaseDatabaseServiceAdapter {
    private List<User> users;
    private List<Thing> things;
    private List<Interaction> interactions;

    @Override
    public void onInitialize() {
        users.add(new User("ys", "password"));

        PhilipsHueBridge bridge1 = new PhilipsHueBridge("123abc");
        PhilipsHueLight light1 = new PhilipsHueLight("456def");
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void addUser(User newUser) {

    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public void deleteAllUsers() {

    }

    @Override
    public List<Thing> getThings() {
        return null;
    }

    @Override
    public Thing getThingByUuid(String uuid) {
        return null;
    }

    @Override
    public Thing getThingByDbid(long dbid) {
        return null;
    }

    @Override
    public long saveThing(Thing thing) {
        return 0;
    }

    @Override
    public List<Long> saveThings(List<Thing> things) {
        return null;
    }

    @Override
    public Thing loadThing(Thing thing) {
        return null;
    }

    @Override
    public void deleteAllThings() {

    }

    @Override
    public List<Connection> getConnections() {
        return null;
    }

    @Override
    public Connection getConnectionByDbid(long dbid) {
        return null;
    }

    @Override
    public List<Connection> getConnectionsBySourceId(long thingDbid) {
        return null;
    }

    @Override
    public List<Connection> getConnectionsByDestinationId(long thingDbid) {
        return null;
    }

    @Override
    public long saveConnection(Connection connection) {
        return 0;
    }

    @Override
    public List<Long> saveConnections(List<Connection> connections) {
        return null;
    }

    @Override
    public Connection loadConnection(Connection connection) {
        return null;
    }

    @Override
    public void deleteAllConnections() {

    }

    @Override
    public List<ThingAccessCredential> getThingAccessCredentials() {
        return null;
    }

    @Override
    public ThingAccessCredential getThingAccessCredentialByDbid(long dbid) {
        return null;
    }

    @Override
    public List<ThingAccessCredential> getThingAccessCredentialsByThingId(long thingId) {
        return null;
    }

    @Override
    public long saveThingAccessCredential(ThingAccessCredential thingAccessCredential) {
        return 0;
    }

    @Override
    public List<Long> saveThingAccessCredentials(List<ThingAccessCredential> thingAccessCredentials) {
        return null;
    }

    @Override
    public ThingAccessCredential loadThingAccessCredential(ThingAccessCredential thingAccessCredential) {
        return null;
    }

    @Override
    public void deleteAllThingAccessCredentials() {

    }

    @Override
    public List<Interaction> getInteraction() {
        return null;
    }

    @Override
    public long saveInteraction(Interaction interaction) {
        return 0;
    }

    @Override
    public void deleteAllInteractions() {

    }
}
