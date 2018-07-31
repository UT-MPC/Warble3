package edu.utexas.mpc.warble3.model.thing.credential;

public class ThingAccessCredentialFactory {
    public ThingAccessCredential createThingAccessCredential(String thingAccessCredentialClassName) {
        if (thingAccessCredentialClassName == null) {
            return null;
        }
        else {
            ThingAccessCredential thingAccessCredential = null;
            switch (thingAccessCredentialClassName) {
                case UsernamePasswordCredential.TAG:
                    thingAccessCredential = new UsernamePasswordCredential();
                    break;
            }
            return thingAccessCredential;
        }
    }
}
