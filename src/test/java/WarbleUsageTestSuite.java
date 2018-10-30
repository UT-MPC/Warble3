import org.junit.Before;
import org.junit.Test;
import selector.AllThingSelector;
import selector.Selector;
import service.SERVICE_ADAPTER_TYPE_INPUT;
import service.SERVICE_ADAPTER_TYPE_OUTPUT;

import java.util.ArrayList;
import java.util.List;

public class WarbleUsageTestSuite {
    private Warble warble;

    @Before
    public void init() {
        warble = new Warble();

        warble.setServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.DATABASE, new MockDatabaseServiceAdapter());
        warble.setServiceAdapter(SERVICE_ADAPTER_TYPE_INPUT.LOCATION, new MockLocationServiceAdapter());
        warble.setServiceAdapter(SERVICE_ADAPTER_TYPE_OUTPUT.HTTP, new MockHttpServiceAdapter());
    }

    @Test
    public void fetchAllThing() {
        List<Selector> template = new ArrayList<>();
        template.add(new AllThingSelector());

        warble.fetch(template);
    }
}
