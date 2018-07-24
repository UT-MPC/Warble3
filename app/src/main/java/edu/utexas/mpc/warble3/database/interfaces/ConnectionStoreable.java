package edu.utexas.mpc.warble3.database.interfaces;

public interface ConnectionStoreable {
    String toConnectionInfo();
    void fromConnectionInfo(String connectionInfo);
}
