package vehicle.maintenance.tracker.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import vehicle.maintenance.tracker.api.VMTApi;
import vehicle.maintenance.tracker.api.entity.Entity;
import vehicle.maintenance.tracker.api.entity.TaskEntity;
import vehicle.maintenance.tracker.api.utils.MockData;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ApplicationController implements Initializable {

    @FXML
    private ListView vehicleList;
    @FXML
    private ListView partList;
    @FXML
    private ListView taskList;
    @FXML
    private TextArea notes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VMTApi api = new VMTApi();

        // generate 100 new vehicles (slow)
        // new MockData(api).add(100, 5, 5, 5);

        ObservableList<EntityInfoCell> vehicles = FXCollections.observableList(
                api.getAllVehicles().stream().map(v -> new EntityInfoCell(v)).collect(Collectors.toList())
        );

        vehicleList.setItems(vehicles);

        vehicleList.onMouseClickedProperty().set(ve -> {
            EntityInfoCell selectedVehicleInfo = (EntityInfoCell) vehicleList.getSelectionModel().getSelectedItem();
            if(selectedVehicleInfo != null){
                String vehicleId = selectedVehicleInfo.getEntity().getId();
                ObservableList<EntityInfoCell> parts = FXCollections.observableList(
                        api.getParts(vehicleId).stream().map(p -> new EntityInfoCell(p)).collect(Collectors.toList())
                );
                ObservableList<EntityInfoCell> vehicleTasks = FXCollections.observableList(
                        api.getTasks(vehicleId).stream().map(p -> new EntityInfoCell(p)).collect(Collectors.toList())
                );

                partList.setItems(parts);
                partList.onMouseClickedProperty().set(pe -> {
                    EntityInfoCell selected = (EntityInfoCell) partList.getSelectionModel().getSelectedItem();
                    if(selected != null){
                        String partId = selected.getEntity().getId();
                        ObservableList<EntityInfoCell> partTasks = FXCollections.observableList(
                                api.getTasks(partId).stream().map(p -> new EntityInfoCell(p)).collect(Collectors.toList())
                        );
                        taskList.setItems(partTasks);
                    }
                    notes.clear();
                    notes.setDisable(true);
                });

                taskList.setItems(vehicleTasks);
                taskList.onMouseClickedProperty().set(te -> {
                    EntityInfoCell selected = (EntityInfoCell) taskList.getSelectionModel().getSelectedItem();
                    if(selected != null){
                        TaskEntity task = (TaskEntity) selected.getEntity();
                        notes.setText(task.getComment());
                        notes.setDisable(false);
                    }
                });
                notes.clear();
                notes.setDisable(true);
            }
        });

    }

    private class EntityInfoCell extends VBox {

        private Entity entity;

        public EntityInfoCell(Entity entity){
            this.entity = entity;
            this.getChildren().addAll(
                    new Text(entity.getName())
            );
        }

        public Entity getEntity(){
            return this.entity;
        }

    }

}
