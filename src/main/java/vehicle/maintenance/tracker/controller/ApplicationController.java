package vehicle.maintenance.tracker.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import vehicle.maintenance.tracker.api.VMTApi;
import vehicle.maintenance.tracker.api.entity.PartEntity;
import vehicle.maintenance.tracker.api.entity.TaskEntity;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;
import vehicle.maintenance.tracker.api.utils.MockData;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ApplicationController implements Initializable {

    @FXML
    private Text header;
    @FXML
    private TextField mileage;
    @FXML
    private ListView vehicleList;
    @FXML
    private ListView partList;
    @FXML
    private ListView taskList;
    @FXML
    private TextArea notes;
    @FXML
    private Text details;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VMTApi api = new VMTApi();

        // generate 100 new vehicles (slow)
        // new MockData(api).add(100, 5, 5, 5);

        details.setText("Vehicles: " + api.countVehicles() + "\nParts: " + api.countParts() + "\nTasks: " + api.countTasks());

        ObservableList<VehicleInfoCell> vehicles = FXCollections.observableList(
                api.getAllVehicles().stream().map(v -> new VehicleInfoCell(v)).collect(Collectors.toList())
        );

        vehicleList.setItems(vehicles);

        vehicleList.onMouseClickedProperty().set(ve -> {
            VehicleInfoCell selectedVehicleInfo = (VehicleInfoCell) vehicleList.getSelectionModel().getSelectedItem();
            if(selectedVehicleInfo != null){
                VehicleEntity selectedVehicle = selectedVehicleInfo.getEntity();
                String vehicleId = selectedVehicle.getId();
                header.setText(selectedVehicle.getName());
                mileage.setText(String.valueOf(selectedVehicle.getMileage()));
                details.setText("Name: " + selectedVehicle.getName()
                        + "\nRegistration: " + selectedVehicle.getRegistration()
                        + "\nMileage: " + selectedVehicle.getMileage());

                ObservableList<PartInfoCell> parts = FXCollections.observableList(
                        api.getParts(vehicleId).stream().map(p -> new PartInfoCell(p)).collect(Collectors.toList())
                );
                ObservableList<TaskInfoCell> vehicleTasks = FXCollections.observableList(
                        api.getTasks(vehicleId).stream().map(p -> new TaskInfoCell(p)).collect(Collectors.toList())
                );

                partList.setItems(parts);
                partList.onMouseClickedProperty().set(pe -> {
                    PartInfoCell selectedPartInfo = (PartInfoCell) partList.getSelectionModel().getSelectedItem();
                    if(selectedPartInfo != null){
                        PartEntity selectedPart = selectedPartInfo.getEntity();
                        details.setText("Name: " + selectedPart.getName()
                        + "\nInstallation-Date: " + selectedPart.getInstallationDate());

                        String partId = selectedPartInfo.getEntity().getId();
                        ObservableList<TaskInfoCell> partTasks = FXCollections.observableList(
                                api.getTasks(partId).stream().map(p -> new TaskInfoCell(p)).collect(Collectors.toList())
                        );
                        taskList.setItems(partTasks);
                    }
                    notes.clear();
                    notes.setDisable(true);
                });

                taskList.setItems(vehicleTasks);
                taskList.onMouseClickedProperty().set(te -> {
                    TaskInfoCell selected = (TaskInfoCell) taskList.getSelectionModel().getSelectedItem();
                    if(selected != null){
                        TaskEntity task = selected.getEntity();
                        details.setText("Due: " + task.getDueDate());
                        notes.setText(task.getComment());
                        notes.setDisable(false);
                    }
                });
                notes.clear();
                notes.setDisable(true);
            }
        });

    }

    private class VehicleInfoCell extends EntityInfoCell<VehicleEntity> {

        public VehicleInfoCell(VehicleEntity entity){
            super(entity);
            this.getChildren().addAll(
                    new Text(entity.getName()),
                    new Label(entity.getRegistration())
            );
        }

    }

    private class PartInfoCell extends EntityInfoCell<PartEntity> {

        public PartInfoCell(PartEntity entity){
            super(entity);
            this.getChildren().addAll(
                    new Text(entity.getName()),
                    new Label("Installed on " + entity.getInstallationDate())

            );
        }

    }

    private class TaskInfoCell extends EntityInfoCell<TaskEntity> {

        public TaskInfoCell(TaskEntity entity){
            super(entity);
            this.getChildren().addAll(
                    new Text(entity.getName()),
                    new Label("Due by " + entity.getDueDate())
            );
        }

    }

    private class EntityInfoCell<E> extends VBox {

        private E entity;

        public EntityInfoCell(E entity){
            this.entity = entity;
        }

        public E getEntity(){
            return this.entity;
        }

    }

}
