package vendor.PhilipsHue.component;

import java.util.List;
import java.util.logging.Logger;

import service.BaseHttpServiceAdapter;
import service.SERVICE_ADAPTER_TYPE_OUTPUT;
import service.ServiceAdapterManager;
import thing.command.Command;
import thing.command.GenericResponse;
import thing.command.Response;
import thing.component.Bridge;
import thing.component.THING_AUTHENTICATION_STATE;
import thing.component.Thing;
import thing.component.ThingState;
import thing.connection.Connection;
import thing.connection.HttpConnection;
import thing.credential.ThingAccessCredential;
import thing.credential.UsernamePasswordCredential;
import vendor.PhilipsHue.service.PhilipsHueBridgeHttpApi;

public final class PhilipsHueBridge extends Bridge {
    private static final String TAG = PhilipsHueBridge.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(TAG);

    public PhilipsHueBridge(String uuid) {
        super(uuid);
    }

    @Override
    public void setCredentialRequired() {
        LOGGER.warning("setCredentialRequired() is not implemented");
    }

    @Override
    public void setThingAccessCredentialClasses() {
        LOGGER.warning("setThingAccessCredentialClasses() is not implemented");
    }

    @Override
    public ThingState getThingState() {
        LOGGER.warning("getThingState() is not implemented");
        return null;
    }

    private Connection getActiveConnection() {
        Connection activeConnection = null;

        if ((lastActiveConnection != null) && (lastActiveConnection.testConnection())) {
            activeConnection = lastActiveConnection;
        } else {
            for (Connection connection : connections) {
                if (connection.testConnection()) {
                    activeConnection = connection;
                    lastActiveConnection = activeConnection;
                    break;
                }
            }
        }

        if (activeConnection == null) {
            lastActiveConnection = null;
        }

        return activeConnection;
    }

    @Override
    public Response callCommand(ServiceAdapterManager serviceAdapterManager, Command command) {
        Response response = super.preCallCommand(command);

        if (response != null) {
            return response;
        } else {
            response = new GenericResponse();
            response.setCommandName(command.getName());
        }

        if (serviceAdapterManager == null) {
            response.setStatus(false);
            response.setDescription("NO_SERVICE_ADAPTER_DEFINED");
            return response;
        }

        Connection connection = getActiveConnection();
        if (connection == null) {
            response.setStatus(false);
            response.setDescription("NO_ACTIVE_CONNECTION_FOUND");
            return response;
        }

        if (connection instanceof HttpConnection) {
            if (connection.getApi() == null) {
                connection.setApi(
                        new PhilipsHueBridgeHttpApi(
                                (BaseHttpServiceAdapter) serviceAdapterManager.getServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.HTTP),
                                ((HttpConnection) connection).getIpAddress()));
            }
        } else {
            response.setStatus(false);
            response.setDescription("UNKNOWN_CONNECTION");
            return response;
        }

        switch (command.getName()) {
            case AUTHENTICATE: {
                if (command.getRegister1() == null) {
                    response.setStatus(false);
                    response.setDescription("NO_CREDENTIAL");

                    return response;
                }

                boolean result = authenticate(connection, (ThingAccessCredential) command.getRegister1());
                response.setStatus(result);
                if (result) {
                    response.setDescription("");
                } else {
                    response.setDescription("UNKNOWN_REASON");
                }

                break;
            }
            default: {
                response.setStatus(false);
                response.setDescription("UNSUPPORTED_COMMAND");
            }
        }

        return response;
    }

    @Override
    public Response receiveResponse() {
        return null;
    }

    @Override
    public void sendCommand(ServiceAdapterManager serviceAdapterManager, Command command) {

    }

    private boolean authenticate(Connection connection, ThingAccessCredential thingAccessCredential) {
        if (!getCredentialRequired()) {
            setAuthenticationState(THING_AUTHENTICATION_STATE.AUTHENTICATED);
            return true;
        }

        if (!isThingAccessCredentialValid(thingAccessCredential)) {
            return false;
        }

        if (thingAccessCredential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) thingAccessCredential;

            String token = usernamePasswordCredential.getToken();
            if (token == null) {

                token = ((PhilipsHueBridgeHttpApi) connection.getApi()).createUser(usernamePasswordCredential.getUsername());

                if (token == null) {
                    LOGGER.warning("Create User failed");
                } else {
                    usernamePasswordCredential.setToken(token);
                }
            } else {
                token = usernamePasswordCredential.getToken();
            }

            String username = ((PhilipsHueBridgeHttpApi) connection.getApi()).getConfig(token);
            if ((username != null) && (username.equals(usernamePasswordCredential.getUsername()))) {
                setAuthenticationState(THING_AUTHENTICATION_STATE.AUTHENTICATED);
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public boolean isThingAccessCredentialValid(ThingAccessCredential thingAccessCredential) {
        if (!getCredentialRequired()) {
            return thingAccessCredential == null;
        }

        if (((getThingAccessCredentialClasses() == null) || (getThingAccessCredentialClasses().size() == 0))) {
            return false;
        } else {
            boolean result = false;
            for (Class m : getThingAccessCredentialClasses()) {
                if (thingAccessCredential.getClass().equals(m)) {
                    result = true;
                }
            }
            return result;
        }
    }

    @Override
    public List<Thing> getThings(ServiceAdapterManager serviceAdapterManager) {
        Connection connection = getActiveConnection();

        if (connection == null) {
            return null;
        }

        List<Thing> returnThings;

        if (connection instanceof HttpConnection) {
            if (connection.getApi() == null) {
                connection.setApi(
                        new PhilipsHueBridgeHttpApi(
                                (BaseHttpServiceAdapter) serviceAdapterManager.getServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.HTTP),
                                ((HttpConnection) connection).getIpAddress()));
            }

            for (ThingAccessCredential credential : thingAccessCredentials) {
                if (credential instanceof UsernamePasswordCredential) {
                    returnThings = ((PhilipsHueBridgeHttpApi) connection.getApi()).getThings(((UsernamePasswordCredential) credential).getToken());
                    if (returnThings != null) {
                        return returnThings;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public List<ThingState> getThingsState(ServiceAdapterManager serviceAdapterManager) {
        return null;
    }

    @Override
    public boolean updateThingState(ServiceAdapterManager serviceAdapterManager, Thing thing, ThingState thingState) {
        return false;
    }

    @Override
    public boolean updateThingState(ServiceAdapterManager serviceAdapterManager, Thing thing, ThingState thingState, boolean postCheck) {
        return false;
    }
}
