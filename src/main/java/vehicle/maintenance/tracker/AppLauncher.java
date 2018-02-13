package vehicle.maintenance.tracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vehicle.maintenance.tracker.api.PartEntity;
import vehicle.maintenance.tracker.api.VMTApi;
import vehicle.maintenance.tracker.api.VehicleEntity;
import vehicle.maintenance.tracker.api.exceptions.EntityDoesNotExistException;
import vehicle.maintenance.tracker.api.models.PartInfoModel;
import vehicle.maintenance.tracker.api.models.VehicleInfoModel;
import vehicle.maintenance.tracker.gui.VehicleEntityView;

/*
        Add yourself to author to all files you edit,update or create.
        Don't forget to comment, keep track of your changes so you can explain
        why you changed them and the problem these changes addressed.
*/

/**
 * Entry point for this application
 *
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public class AppLauncher extends Application {

    // think of stage and the main app window
    public void start(Stage stage) {
        VMTApi api = new VMTApi();
        VehicleInfoModel model = api.createNewVehicleInfoModel(new VehicleEntity(1231, "H39GJ93ID9"));
        model.addTaskEntity("Clean", "13/01/92");
        model.addTaskEntity("Maintain", "11/02/92");
        PartInfoModel part = api.createNewPartInfoModel(new PartEntity(model.getVehicleEntity().getId(), "Wheel", "22/12/91"));

        try{
            api.commit(model);
        }catch(EntityDoesNotExistException e){
            e.printStackTrace();
        }
        model = api.getVehicleInfoModel(model.getVehicleEntity().getId());

        stage.setScene(new Scene(new VehicleEntityView(model.getVehicleEntity())));
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        new AppLauncher().launch(args);
    }

}
