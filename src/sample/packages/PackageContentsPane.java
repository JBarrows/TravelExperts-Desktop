package sample.packages;

import TraveExDB.ProductSupplier;
import TraveExDB.ProductSupplierDB;
import TraveExDB.TravelPackage;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PackageContentsPane extends TitledPane {

    private ObservableList<ProductSupplier> psList ;
    private TableView<ProductSupplier> tableView;
    private TravelPackage travelPackage;

    public PackageContentsPane() {
        super("Contents", new VBox(10));
        VBox vbox = (VBox) this.getContent();

        //Add tableview
        tableView = buildTable();
        vbox.getChildren().add(tableView);

        //Add buttons
        HBox hbox = buildButtons();
        //vbox.getChildren().add(hbox);
    }

    private HBox buildButtons() {
        Button btnAdd = new Button("Add");
        Button btnRemove = new Button("Remove");
        return new HBox(5, btnAdd, btnRemove);
    }

    private TableView buildTable() {
        TableView<ProductSupplier> tv = new TableView<>();

        //Create columns
        TableColumn<ProductSupplier, String> colProducts = new TableColumn<>("Product");
        TableColumn<ProductSupplier, String> colSuppliers = new TableColumn<>("Supplier");

        //Set table cell factories
        colProducts.setCellValueFactory(new PropertyValueFactory<>("ProdName"));
        colSuppliers.setCellValueFactory(new PropertyValueFactory<>("SupName"));

        //Add columns
        tv.getColumns().add(colProducts);
        tv.getColumns().add(colSuppliers);

        psList = FXCollections.observableArrayList();
        psList.addListener((ListChangeListener<ProductSupplier>) c -> fillTable());

        return tv;
    }

    private void fillTable() {
        tableView.setItems(psList);
    }

    public void setTravelPackage(TravelPackage travelPackage) {
        this.travelPackage = travelPackage;
        psList.setAll(ProductSupplierDB.getProductsSuppliers(travelPackage));
    }


}
