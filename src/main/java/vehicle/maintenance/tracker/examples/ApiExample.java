package vehicle.maintenance.tracker.examples;

import vehicle.maintenance.tracker.api.VehicleAPI;
import vehicle.maintenance.tracker.api.development.DatabaseDump;

/**
 * Demo <code>ApiExample</code>
 * This class is only for demo purposes
 *
 * @Author Daile Alimo
 * @Since 0.1-SNAPSHOT
 */
public class ApiExample {

    public static void main(String[] args){
        VehicleAPI api = new VehicleAPI();
        // we currently have several useful methods in the api

        System.out.println("count all vehicles in database " + api.countAllVehicles());
        System.out.println("count all parts in database " + api.countAllParts());

        // use DatabaseDump helper class to dump data
        System.out.println(DatabaseDump.makeDataString(api.getAllVehicleInformation(), api));
    }

}
