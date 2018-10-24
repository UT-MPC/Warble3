package thing.component;

public interface TextStoreable extends Storeable {
    String toStoreableText();
    void fromStoreableText(String storeableText);
}
