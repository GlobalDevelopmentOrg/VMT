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
import vehicle.maintenance.tracker.api.dao.HibernateDAO;
import vehicle.maintenance.tracker.api.utils.HibernateMockDataGenerator;
import vehicle.maintenance.tracker.api.entity.PartEntity;
import vehicle.maintenance.tracker.api.entity.TaskEntity;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
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
        HibernateDAO dao = new HibernateDAO();

        // generate 100 new vehicles (slow)
        new HibernateMockDataGenerator(dao).add(100, 5, 5, 5);

        details.setText(
                "Vehicles: " + dao.count(VehicleEntity.class)
                + "\nParts: " + dao.count(PartEntity.class)
                + "\nTasks: " + dao.count(TaskEntity.class)
        );

        // Get all vehicles from api and add them to the vehicle list
        //
        vehicleList.setItems(FXCollections.observableList(
                dao.getAll(VehicleEntity.class)
                        .stream()
                        .map(vehicle -> new VehicleInfoCell((VehicleEntity) vehicle))
                        .collect(Collectors.toList())
        ));

        // On click of vehicle list item
        //
        // get selected item and check for null
        // get the entity for the specific item
        // update header, mileage, details
        // load parts for vehicle
        // load tasks for vehicle
        // TODO implement comments for vehicle ?
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
                        dao.getAssociatedWith(PartEntity.class, "vehicleId", vehicleId)
                                .stream()
                                .map(part -> new PartInfoCell((PartEntity) part))
                                .collect(Collectors.toList())
                );
                ObservableList<TaskInfoCell> vehicleTasks = FXCollections.observableList(
                        dao.getAssociatedWith(TaskEntity.class,"parentId", vehicleId)
                                .stream()
                                .map(task -> new TaskInfoCell((TaskEntity) task))
                                .collect(Collectors.toList())
                );

                partList.setItems(parts);
                taskList.setItems(vehicleTasks);
                notes.clear();
                notes.setDisable(true);
            }
        });

        // on click of part list item
        //
        // get selected item and check for null
        // get the entity for the specific item
        // update details
        // load tasks for part
        // clear and disable notes as they are not implemented
        // TODO implement comments for parts ?
        partList.onMouseClickedProperty().set(pe -> {
            PartInfoCell selectedPartInfo = (PartInfoCell) partList.getSelectionModel().getSelectedItem();
            if(selectedPartInfo != null){
                PartEntity selectedPart = selectedPartInfo.getEntity();
                details.setText("Name: " + selectedPart.getName()
                        + "\nInstallation-Date: " + selectedPart.getInstallationDate());

                String partId = selectedPartInfo.getEntity().getId();
                ObservableList<TaskInfoCell> partTasks = FXCollections.observableList(
                        dao.getAssociatedWith(TaskEntity.class, "parentId", partId)
                                .stream()
                                .map(task -> new TaskInfoCell((TaskEntity) task))
                                .collect(Collectors.toList())
                );
                taskList.setItems(partTasks);
            }
            notes.clear();
            notes.setDisable(true);
        });

        // on click of task list item
        //
        // get selected item and check for null
        // get the entity for the specific item
        // update details
        // set notes for task
        // enable notes (input editable)
        taskList.onMouseClickedProperty().set(te -> {
            TaskInfoCell selected = (TaskInfoCell) taskList.getSelectionModel().getSelectedItem();
            if(selected != null){
                TaskEntity task = selected.getEntity();
                details.setText("Due: " + task.getDueDate());
                notes.setText(task.getNotes());
                notes.setDisable(false);
            }
        });
    }

    private class VehicleInfoCell extends EntityInfoCell<VehicleEntity> {

        VehicleInfoCell(VehicleEntity entity){
            super(entity);
            this.getChildren().addAll(
                    new Text(entity.getName()),
                    new Label(entity.getRegistration())
            );
        }

    }

    private class PartInfoCell extends EntityInfoCell<PartEntity> {

        PartInfoCell(PartEntity entity){
            super(entity);
            this.getChildren().addAll(
                    new Text(entity.getName()),
                    new Label("Installed on " + entity.getInstallationDate())

            );
        }

    }

    private class TaskInfoCell extends EntityInfoCell<TaskEntity> {

        TaskInfoCell(TaskEntity entity){
            super(entity);
            this.getChildren().addAll(
                    new Text(entity.getName()),
                    new Label("Due by " + entity.getDueDate())
            );
        }

    }

    private class EntityInfoCell<E> extends VBox {

        private E entity;

        EntityInfoCell(E entity){
            this.entity = entity;
        }

        E getEntity(){
            return this.entity;
        }

    }

}
