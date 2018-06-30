package edu.utexas.mpc.warble3.frontend.adapter;

import android.content.Intent;

import java.util.Arrays;
import java.util.List;

public class Criterion {
    public static final int EDITTEXT_TYPE = 1;
    public static final int LIST_TYPE = 2;
    public static final int CHECKBOX_TYPE = 3;
    protected List<Integer> selectionTypes = Arrays.asList(EDITTEXT_TYPE, LIST_TYPE, CHECKBOX_TYPE);

    protected String name;
    protected String value;
    protected int selectionType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSelectionType() {
        return selectionType;
    }

    public void setSelectionType(int selectionType) {
        if (selectionTypes.contains(selectionType)) {
            this.selectionType = selectionType;
        } else {
            throw new IllegalArgumentException("SelectionType is INVALID");
        }
    }

    public Criterion(String name, String value, int selectionType) {
        this.setName(name);
        this.setValue(value);
        this.setSelectionType(selectionType);
    }
}
