package vehicle.maintenance.tracker.api.development;

import vehicle.maintenance.tracker.api.VehicleAPI;
import vehicle.maintenance.tracker.model.VehicleInformation;
import vehicle.maintenance.tracker.model.VehiclePart;

import java.util.List;

/**
 * Test class only for helping with development
 * This class has 1 static method that will compile a String
 * from a given <code>VehicleAPI</code>.
 *
 * @Author Daile Alimo
 * @Since 0.1-SNAPSHOT
 */
public class DatabaseDump {

    public static String makeDataString(List<VehicleInformation> vehicleInformation, VehicleAPI vehicleAPI){
        StringBuilder builder = new StringBuilder();
        vehicleInformation.forEach(v -> {
            builder.append(v.getName() + " " + v.getRegistration() + " : " + v.getMileage() + "\n");
            vehicleAPI.getVehiclePartIds(v.getId()).forEach(l -> {
                VehiclePart part = vehicleAPI.getVehiclePart(l);
                builder.append("--> " + part.getName() + " " + part.getMileage() + "\n");
            });
        });
        builder.append("-- " + vehicleInformation.size() + " of " + vehicleAPI.countAllVehicles() + " --\n");
        return builder.toString();
    }

}
