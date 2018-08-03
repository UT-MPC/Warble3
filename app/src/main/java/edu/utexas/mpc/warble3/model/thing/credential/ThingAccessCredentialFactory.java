package edu.utexas.mpc.warble3.model.thing.credential;

public class ThingAccessCredentialFactory {
    public ThingAccessCredential createThingAccessCredential(String thingAccessCredentialClassName) {
        if (thingAccessCredentialClassName == null) {
            return null;
        }
        else {
            ThingAccessCredential thingAccessCredential = null;

            String[] thingAccessCredentialClassNameList = thingAccessCredentialClassName.split("\\.");
            String thingAccessCredentialClassSimpleName = thingAccessCredentialClassNameList[thingAccessCredentialClassNameList.length-1];

            switch (thingAccessCredentialClassSimpleName) {
                case UsernamePasswordCredential.TAG:
                    thingAccessCredential = new UsernamePasswordCredential();
                    break;
            }
            return thingAccessCredential;
        }
    }
}
