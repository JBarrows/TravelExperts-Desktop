package sample;

import TraveExDB.PackageDB;
import TraveExDB.TravelPackage;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class PackageTravellersPane extends TitledPane {

    VBox vbox;
    Label lblHeadCount;
    TreeView treeView;
    private TravelPackage travelPackage;

    public PackageTravellersPane() {
        super("Travellers", new VBox(5));

        vbox = (VBox) this.getContent();
        treeView = new TreeView();
        lblHeadCount = new Label("Head count: ");
        vbox.getChildren().setAll(treeView, lblHeadCount, createButtons());
    }

    private HBox createButtons() {
        //TODO: Add button functions
        //Build Add and Remove buttons
        Button btnAdd = new Button("Add");
        Button btnRemove = new Button("Remove");
        //TODO: Add Email button
        return new HBox(5, btnAdd, btnRemove);
    }

    void setTravelPackage(TravelPackage travelPackage) {
        this.travelPackage = travelPackage;

        createTree(travelPackage);
    }

    private TreeView createTree(TravelPackage travelPackage) {
        //TODO: Build tree view
        // Build root from package
        TreeItem<String> root = new TreeItem<>(travelPackage.getPkgName());

        // Add Customers as leaves
        // eg. [European Vacation]
        //     [   James Reed +1 ]
        //     [   Lula Oates    ]
        int travellerCount = 0;
        ArrayList<Traveller> travellers = PackageDB.getTravellers(travelPackage);
        for (Traveller traveller:travellers) {
            root.getChildren().add(new TreeItem<>(traveller.toString()));
            travellerCount += traveller.getHeadCount();
        }

        // Add toot to tree
        treeView.setRoot(root);
        root.setExpanded(true);

        //Display Traveller Count
        lblHeadCount.setText("Head count: " + travellerCount);

        return treeView;
    }


}

