package edu.utexas.mpc.warble3.database.converter;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import edu.utexas.mpc.warble3.database.AppDatabase;
import edu.utexas.mpc.warble3.database.ConnectionDb;
import edu.utexas.mpc.warble3.database.interfaces.ConnectionStoreable;
import edu.utexas.mpc.warble3.model.thing.connect.Connection;
import edu.utexas.mpc.warble3.util.Logging;

public class ConnectionConverter {
    public static final String TAG = "ConnectionConverter";

    public static Connection toConnection(ConnectionDb connectionDb) {
        if (connectionDb == null) {
            return null;
        }
        else {
            try {
                Class<?> connectionClass = Class.forName(connectionDb.getConnectionClass());
                Constructor<?> connectionClassConstructor = connectionClass.getConstructor();
                Object object = connectionClassConstructor.newInstance();

                long connectionDbSourceId = connectionDb.getSourceId();
                if (connectionDbSourceId == 0) {
                    if (Logging.WARN) Log.w(TAG, String.format("connectionDb %s has sourceId = 0", connectionDb.getDbid()));
                }

                long connectionDbDestinationId = connectionDb.getDestinationId();

                Connection connection = (Connection) object;
                connection.setSource(AppDatabase.getDatabase().getThingByDbid(connectionDbSourceId));
                connection.setDestination(AppDatabase.getDatabase().getThingByDbid(connectionDbDestinationId));

                try {
                    ConnectionStoreable c = (ConnectionStoreable) connection;
                    c.fromConnectionInfo(connectionDb.getConnectionInfo());

                    connection = (Connection) c;
                }
                catch (ClassCastException e) {
                    if (Logging.WARN) Log.w(TAG, String.format("Connection %s is unable to cast to ConnectionStoreable, or vice versa", connection.toString()));
                }

                connection.setDbid(connectionDb.getDbid());

                return connection;
            }
            catch (ClassNotFoundException e) {
                if (Logging.ERROR) Log.e(TAG, String.format("Connection Class %s is NOT found", connectionDb.getConnectionClass()));
                return null;
            }
            catch (NoSuchMethodException e) {
                if (Logging.ERROR) Log.e(TAG, String.format("Connection Class Constructor %s is NOT found", connectionDb.getConnectionClass()));
                return null;
            }
            catch (IllegalAccessException e) {
                if (Logging.ERROR) Log.e(TAG, String.format("Connection Class %s has illegal access", connectionDb.getConnectionClass()));
                return null;
            }
            catch (InstantiationException e) {
                if (Logging.ERROR) Log.e(TAG, String.format("Connection Class %s has instantiation error", connectionDb.getConnectionClass()));
                return null;
            }
            catch (InvocationTargetException e) {
                if (Logging.ERROR) Log.e(TAG, String.format("Connection Class %s has invocation target error", connectionDb.getConnectionClass()));
                return null;
            }
        }

    }

    public static List<Connection> toConnections(List<ConnectionDb> connectionDbs) {
        if (connectionDbs == null) {
            return null;
        }
        else {
            List<Connection> connections = new ArrayList<>();
            for (ConnectionDb connectionDb : connectionDbs) {
                connections.add(ConnectionConverter.toConnection(connectionDb));
            }
            return connections;
        }
    }

    public static ConnectionDb toConnectionDb(Connection connection) {
        ConnectionDb connectionDb = new ConnectionDb();

        connectionDb.setSourceId(AppDatabase.getDatabase().thingDbDao().getThingDbByUuid(connection.getSource().getUuid()).getDbid());
        if (connection.getDestination() == null) {
            connectionDb.setDestinationId(0);
        }
        else {
            connectionDb.setDestinationId(AppDatabase.getDatabase().thingDbDao().getThingDbByUuid(connection.getDestination().getUuid()).getDbid());
        }
        connectionDb.setConnectionClass(connection.getClass().getName());

        try {
            ConnectionStoreable c = (ConnectionStoreable) connection;
            connectionDb.setConnectionInfo(c.toConnectionInfo());
        }
        catch (ClassCastException e) {
            if (Logging.WARN) Log.w(TAG, String.format("Connection %s is unable to cast to ConnectionStoreable", connection.toString()));
        }

        connectionDb.setDbid(connection.getDbid());

        return connectionDb;
    }

    public static List<ConnectionDb> toConnectionDbs(List<Connection> connections) {
        if (connections == null) {
            return null;
        }
        else {
            List<ConnectionDb> connectionDbs = new ArrayList<>();
            for (Connection connection : connections) {
                connectionDbs.add(ConnectionConverter.toConnectionDb(connection));
            }
            return connectionDbs;
        }
    }
}
