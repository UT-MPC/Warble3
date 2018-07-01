package edu.utexas.mpc.warble3.model;

import java.util.List;

public abstract class Thing {
    public static final String TAG = "Thing";

    private String name;
    private String friendlyName;
    private String accessName;
    private String accessPasscode;
    private List<String> connections;
    private List<String> discoveries;
}