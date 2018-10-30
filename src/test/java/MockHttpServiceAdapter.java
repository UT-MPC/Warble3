import service.BaseHttpServiceAdapter;

import java.util.HashMap;

public class MockHttpServiceAdapter extends BaseHttpServiceAdapter {
    @Override
    public <T> HttpResponse<T> sendSynchronous(String baseUrl, String apiPath, METHOD method, String[] headers, String body, HashMap<String, Object> parts) {
        System.out.println(String.format("==== MOCK: SYNC-HTTP-%s: %s/%s", method, baseUrl, apiPath));
        System.out.print(String.format("      Headers: %s\n", String.join(",", headers)));
        System.out.println(body);
        System.out.println(parts.toString());
        System.out.println("==== END MOCK ====");

        return null;
    }

    @Override
    public void sendAsynchronous(Callback callback, String baseUrl, String apiPath, METHOD method, String[] headers, String body, HashMap<String, Object> parts) {
        System.out.println(String.format("==== MOCK: ASYNC-HTTP-%s: %s/%s", method, baseUrl, apiPath));
        System.out.print(String.format("      Headers: %s\n", String.join(",", headers)));
        System.out.println(body);
        System.out.println(parts.toString());
        System.out.println("==== END MOCK ====");
    }

    @Override
    public void onInitialize() {

    }

    @Override
    public void onTerminate() {

    }
}
