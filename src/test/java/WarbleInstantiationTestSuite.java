import org.junit.Test;
import service.SERVICE_ADAPTER_TYPE_INPUT;
import service.SERVICE_ADAPTER_TYPE_OUTPUT;

import static org.junit.Assert.assertEquals;

public class WarbleInstantiationTestSuite {
    @Test
    public void WarbleInstantiation() {
        Warble warble = new Warble();
    }

    @Test
    public void WarbleServiceAdapterInitialization() {
        Warble warble = new Warble();

        MockDatabaseServiceAdapter mockDatabaseServiceAdapter = new MockDatabaseServiceAdapter();
        MockLocationServiceAdapter mockLocationServiceAdapter = new MockLocationServiceAdapter();
        MockHttpServiceAdapter mockHttpServiceAdapter = new MockHttpServiceAdapter();

        warble.setServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.DATABASE, mockDatabaseServiceAdapter);
        warble.setServiceAdapter(SERVICE_ADAPTER_TYPE_INPUT.LOCATION, mockLocationServiceAdapter);
        warble.setServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.HTTP, mockHttpServiceAdapter);

        assertEquals(mockDatabaseServiceAdapter, warble.getServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.DATABASE));
        assertEquals(mockLocationServiceAdapter, warble.getServiceAdapter(SERVICE_ADAPTER_TYPE_INPUT.LOCATION));
        assertEquals(mockHttpServiceAdapter, warble.getServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.HTTP));
    }
}