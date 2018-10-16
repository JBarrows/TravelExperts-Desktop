package sample.packages;

import TraveExDB.TravelPackage;
import javafx.scene.control.TitledPane;

import java.util.ArrayList;
import java.util.EventListener;

public class PackageProperties extends ArrayList<TitledPane> {

    private final PackageDetailPane pnDetails;
    private final PackageContentsPane pnContents;
    private final PackageTravellersPane pnTravellers;

    public PackageProperties() {
        super();

        // Add Details pane
        pnDetails = new PackageDetailPane();
        this.add(pnDetails);

        // Add contents pane
        pnContents = new PackageContentsPane();
        this.add(pnContents);

        // Add Purchases pane
        pnTravellers = new PackageTravellersPane();
        this.add(pnTravellers);

    }

    public void setPackage(TravelPackage travelPackage) {
        // Set panes
        pnDetails.setTravelPackage(travelPackage);
        pnContents.setTravelPackage(travelPackage);
        pnTravellers.setTravelPackage(travelPackage);
    }

    public void addUpdateListener(UpdateListener listener) {
        pnDetails.addUpdateListener(listener);
    }

    public interface UpdateListener extends EventListener {
        void onUpdate();
    }
}
