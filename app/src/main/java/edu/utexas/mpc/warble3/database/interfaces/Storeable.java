package edu.utexas.mpc.warble3.database.interfaces;

public interface Storeable {
    void onPostStore(long dbid);
    void onPostLoad(long dbid);
}
