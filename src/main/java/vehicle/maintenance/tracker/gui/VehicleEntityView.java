package vehicle.maintenance.tracker.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import vehicle.maintenance.tracker.api.VehicleEntity;

/**
 * A Vehicle entity view
 * displays only the registration and mileage data
 * for a vehicle entity.
 *
 * |--------------------------------------|
 * | Vehicle AE30SD12 current mileage 120 |
 * |--------------------------------------|
 *
 * @author Daile Alimo
 * @since 0.2-SNAPSHOT
 */
public class VehicleEntityView extends HBox {

    private static final String TITLE = "Vehicle :registration with :mileage miles";

    public VehicleEntityView(VehicleEntity vehicleEntity){
        Text text = new Text(VehicleEntityView.TITLE.replace(":registration", vehicleEntity.getRegistration()).replace(":mileage", String.valueOf(vehicleEntity.getMileage())));
        text.setFont(new Font(32));
        this.getChildren().add(text);
        this.setAlignment(Pos.CENTER);
        this.setBorder(new Border(
                new BorderStroke(
                        new Color(0, 0, 0, .5),
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(.1),
                        BorderStroke.THIN
                        )
                )
        );
    }

}
