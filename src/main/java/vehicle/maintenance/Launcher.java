package vehicle.maintenance;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vehicle.maintenance.tracker.api.dao.HibernateDAO;
import vehicle.maintenance.tracker.api.utils.HibernateUtil;

import java.io.IOException;
import java.net.URL;

public class Launcher extends Application {

    public void start(Stage stage){
        Parent mainView;
        try {
            URL url = new URL("File:views/main.fxml");
            mainView = FXMLLoader.load(url);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(mainView != null){
            Scene scene = new Scene(mainView);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
            stage.onCloseRequestProperty().set(e -> {
                // currently we need to close the hibernate session ourselves!
                HibernateUtil.close();
            });
        }else{
            System.err.println("unable to start application");
        }
    }

    public static void main(String[] args){
        new Launcher().launch(args);
    }

}
