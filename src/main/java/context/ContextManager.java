package context;

import service.BaseLocationServiceAdapter;
import service.SERVICE_ADAPTER_TYPE_INPUT;
import service.ServiceAdapterManager;
import service.ServiceAdapterUser;

public final class ContextManager implements ServiceAdapterUser {
    private ServiceAdapterManager serviceAdapterManager;

    public Context getContext() {
        Context context = new GenericContext();

        ((GenericContext) context).setLocation(
                ((BaseLocationServiceAdapter) (serviceAdapterManager.getServiceAdapter(SERVICE_ADAPTER_TYPE_INPUT.LOCATION))).getLocation()
        );

        return context;
    }

    @Override
    public void setServiceAdapter(ServiceAdapterManager serviceAdapterManager) {
        this.serviceAdapterManager = serviceAdapterManager;
    }
}
