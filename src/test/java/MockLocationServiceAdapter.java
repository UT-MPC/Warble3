import context.Location;
import service.BaseLocationServiceAdapter;

public class MockLocationServiceAdapter extends BaseLocationServiceAdapter {
    @Override
    public Location getLocation() {
        return null;
    }
}
