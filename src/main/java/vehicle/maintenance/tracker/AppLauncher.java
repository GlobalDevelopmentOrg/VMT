package vehicle.maintenance.tracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import vehicle.maintenance.tracker.api.exceptions.InvalidDateException;
import vehicle.maintenance.tracker.gui.DateComponent;

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
        VBox layout = null;
        try {
            layout = new VBox(new DateComponent("28/12/91"));
        } catch (InvalidDateException e) {
            e.printStackTrace();
        }

        stage.setScene(new Scene(layout));
        stage.show();
    }

    public static void main(String[] args) {
        new AppLauncher().launch(args);
    }

}
