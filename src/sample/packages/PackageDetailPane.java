package sample.packages;

import TraveExDB.PackageDB;
import TraveExDB.TravelPackage;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;

public class PackageDetailPane extends TitledPane {

    private final Button btnSave, btnCancel;
    private TextField txtPackageId, txtPkgName;
    private TextArea txtPkgDesc;
    private DatePicker dpPkgStartDate, dpPkgEndDate;
    private TextField txtPkgBasePrice, txtPkgAgencyCommission;
    private TravelPackage travelPackage;
    private ArrayList<PackageProperties.UpdateListener> updateListeners;
    private VBox vbox;

    public PackageDetailPane() {
        super("Details", new VBox(5));

        updateListeners = new ArrayList<>();

        vbox = (VBox)getContent();

        //Create form controls
        txtPackageId = CreateTextField("ID");   //ID
        txtPackageId.setDisable(true);
        txtPkgName = CreateTextField("Name");   //NAME
        txtPkgDesc = new TextArea();                //DESCRIPTION
        txtPkgDesc.setMaxHeight(50);
        txtPkgDesc.setWrapText(true);
        txtPkgDesc.textProperty().addListener((observable, oldValue, newValue) -> onDetailsChanged());
        vbox.getChildren().add(new Label("Description"));
        vbox.getChildren().add(txtPkgDesc);
        dpPkgStartDate = createDatePicker("Start Date");    //START DATE
        dpPkgEndDate = createDatePicker("End Date");        //END DATE
        txtPkgBasePrice = CreateTextField("Base Price");      //BASE PRICE
        txtPkgAgencyCommission = CreateTextField(("Commission")); //COMMISSION

        //Create save and cancel buttons
        btnSave = new Button("Save");
        btnSave.setOnAction(this::onBtnSaveClicked);
        btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> setTravelPackage(travelPackage));
        HBox buttonPane = new HBox(5);
        buttonPane.getChildren().setAll(btnSave, btnCancel);
        ((VBox)getContent()).getChildren().add(buttonPane);
    }

    private DatePicker createDatePicker(String label) {
        DatePicker datePicker = new DatePicker();
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> onDetailsChanged());

        // Add a button to clear the datepicker
        Button btnClearDate = new Button("X");
        btnClearDate.setOnAction(event -> datePicker.setValue(null));

        // Put the datepicker and clear button in an HBox
        HBox hBox = new HBox(5);
        hBox.getChildren().add(datePicker);
        hBox.getChildren().add(btnClearDate);

        // Put the label and hbox in the panel body
        VBox vbox = (VBox)getContent();
        vbox.getChildren().add(new VBox( new Label(label), hBox ));

        return datePicker;
    }

    void setTravelPackage(TravelPackage travelPackage) {
        this.travelPackage = travelPackage;

        txtPackageId.setText( String.valueOf(travelPackage.getPackageId()) );
        txtPkgName.setText( travelPackage.getPkgName() );
        txtPkgDesc.setText( travelPackage.getPkgDesc() );
        dpPkgStartDate.setValue( localFromDate(travelPackage.getPkgStartDate()) );
        dpPkgEndDate.setValue( localFromDate(travelPackage.getPkgEndDate()) );
        txtPkgBasePrice.setText( String.format("%.2f", travelPackage.getPkgBasePrice()) );
        txtPkgAgencyCommission.setText( travelPackage.getPkgAgencyCommission() == null ?
                                        "" :
                                        String.format("%.2f", travelPackage.getPkgAgencyCommission()) );

        disableButtons();
    }

    private LocalDate localFromDate(Date date) {
        if (date == null) return null;

        return date.toLocalDate();
    }

    private TextField CreateTextField(String lbl) {
        TextField txtF = new TextField();
        txtF.textProperty().addListener((observable, oldValue, newValue) -> onDetailsChanged());
        VBox vbox = (VBox)getContent();
        vbox.getChildren().add( new VBox(new Label(lbl), txtF ));

        return txtF;
    }

    private void onBtnSaveClicked(ActionEvent event) {
        // Validate Fields
        if (checkFields() != "") {
            return;
        }

        // Update database
        TravelPackage newPackage = newPackageFromDetails();
        try {
            int rowsAffected = PackageDB.update(travelPackage, newPackage);

            if (rowsAffected >= 1) {
                disableButtons();
                triggerUpdate();
                if (rowsAffected > 1)
                    (new Alert(Alert.AlertType.WARNING, "Multiple packages updated, please check database", ButtonType.NO)).show();
            } else {
                (new Alert(Alert.AlertType.ERROR, "No packages modified", ButtonType.OK)).show();
            }

        } catch (SQLException | ClassNotFoundException e) {

            Alert updateError = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            updateError.setTitle("Error updating package");
            updateError.show();
            e.printStackTrace();
        }

    }

    //broadcast to all Update Listeners
    private void triggerUpdate() {
        updateListeners.forEach(PackageProperties.UpdateListener::onUpdate);
    }

    private void disableButtons() {
        //disable buttons
        btnSave.setDisable(true);
        btnCancel.setDisable(true);
    }

    private TravelPackage newPackageFromDetails() {

        TravelPackage p = new TravelPackage();

        p.setPackageId(Integer.parseInt(txtPackageId.getText()));
        p.setPkgName(txtPkgName.getText());
        p.setPkgDesc(txtPkgDesc.getText());
        p.setPkgStartDate(dateFromLocal(dpPkgStartDate.getValue()));
        p.setPkgEndDate(dateFromLocal(dpPkgEndDate.getValue()));
        p.setPkgBasePrice( Float.parseFloat(txtPkgBasePrice.getText()) );
        p.setPkgAgencyCommission(txtPkgAgencyCommission.getText().equals("") ?
                                null :
                                Float.parseFloat(txtPkgAgencyCommission.getText()) );

        return p;
    }

    private Date dateFromLocal(LocalDate localDate) {
        if (localDate == null) return null;

        return Date.valueOf(localDate);
    }

    // TODO: Test validation
    private String checkFields() {
        //TODO: Validate fields
        // Check that description is not too long

        // Check that End date is not before Start date

        // Check that price and commission are numbers

        return "";
    }

    private void onDetailsChanged() {
        btnSave.setDisable(false);
        btnCancel.setDisable(false);
    }

    void addUpdateListener(PackageProperties.UpdateListener listener) {
        updateListeners.add(listener);
    }
}
