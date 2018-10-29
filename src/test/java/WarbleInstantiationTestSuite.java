import org.junit.Test;
import service.SERVICE_ADAPTER_TYPE_INPUT;
import service.SERVICE_ADAPTER_TYPE_OUTPUT;

public class WarbleInstantiationTestSuite {
    @Test
    public void WarbleInstantiation() {
        Warble warble = new Warble();
    }

    @Test
    public void WarbleServiceAdapterInitialization() {
        Warble warble = new Warble();

        warble.setServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.DATABASE, new MockDatabaseServiceAdapter());
        warble.setServiceAdapter(SERVICE_ADAPTER_TYPE_INPUT.LOCATION, new MockLocationServiceAdapter());
        warble.setServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.HTTP, new MockHttpServiceAdapter());
    }
}