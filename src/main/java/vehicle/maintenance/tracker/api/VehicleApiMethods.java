package vehicle.maintenance.tracker.api;

import vehicle.maintenance.tracker.model.VehicleInformation;
import vehicle.maintenance.tracker.model.VehiclePart;

import java.util.List;

/**
 * API method interface
 *
 * @Author Daile Alimo
 * @Since 0.1-SNAPSHOT
 */
public interface VehicleApiMethods {

    List<VehicleInformation> getAllVehicleInformation();
    List<VehicleInformation> getVehicleInformation(int max, int offset);
    List<Long> getVehiclePartIds(long vehicleId);

    VehicleInformation getVehicleInformation(long index);
    VehiclePart getVehiclePart(long id);

    boolean saveVehicleParts(List<VehiclePart> vehicleParts);

    int saveVehicleInformation(VehicleInformation vehicleInformation);
    int saveVehiclePart(VehiclePart vehiclePart);

    long countAllVehicles();
    long countAllParts();

}
