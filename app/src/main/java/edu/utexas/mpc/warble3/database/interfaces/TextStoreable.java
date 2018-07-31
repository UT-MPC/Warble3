package edu.utexas.mpc.warble3.database.interfaces;

public interface TextStoreable {
    String toStoreableText();
    void fromStoreableText(String storeableText);
}
