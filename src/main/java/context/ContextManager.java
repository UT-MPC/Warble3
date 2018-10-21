package context;

import service.ServiceAdapterManager;
import service.ServiceAdapterUser;

public final class ContextManager implements ServiceAdapterUser {
    private ServiceAdapterManager serviceAdapterManager;

    public Context getContext() {
        return null;
    }

    @Override
    public void setServiceAdapter(ServiceAdapterManager serviceAdapterManager) {
        this.serviceAdapterManager = serviceAdapterManager;
    }
}
