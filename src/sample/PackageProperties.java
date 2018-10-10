package sample;

import TraveExDB.TravelPackage;
import javafx.scene.control.TitledPane;

import java.util.ArrayList;
import java.util.EventListener;

public class PackageProperties extends ArrayList<TitledPane> {

    TravelPackage travelPackage;
    //TODO: use custom panes
    private final PackageDetailPane pnDetails;
    private final PackageContentsPane pnContents;
    private final PackageTravellersPane pnTravellers;

    PackageProperties() {
        super();

        //TODO: Build panes
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

    void setPackage(TravelPackage travelPackage) {
        this.travelPackage = travelPackage;

        // Set panes
        pnDetails.setTravelPackage(travelPackage);
        pnContents.setTravelPackage(travelPackage);
        pnTravellers.setTravelPackage(travelPackage);
    }

    void addUpdateListener(UpdateListener listener) {
        pnDetails.addUpdateListener(listener);
    }

    interface UpdateListener extends EventListener {
        void onUpdate();
    }
}
