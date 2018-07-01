package edu.utexas.mpc.warble3.model;

import java.util.List;

public abstract class Thing {
    public static final String TAG = "Thing";

    private String name;
    private String friendlyName;
    private String accessName;
    private String accessPasscode;

    private String manufacturerSerialNumber;
    private String manufacturerModelName;
    private String manufacturerModelNumber;
    private String manufacturerName;

    private String coarsetype;
    private String finetype;

    private List<String> connections;
    private List<String> discoveries;
}
