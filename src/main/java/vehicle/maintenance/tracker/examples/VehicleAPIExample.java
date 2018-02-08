package vehicle.maintenance.tracker.examples;

import org.h2.tools.DeleteDbFiles;
import vehicle.maintenance.tracker.api.VehicleApi;
import vehicle.maintenance.tracker.api.database.H2DatabaseConnector;
import vehicle.maintenance.tracker.models.Vehicle;

import java.util.List;

/**
 * <code>SQLHelperExample</code>
 * Example of the usage of the <code>SQLStatementFactory</code>
 *
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public class VehicleAPIExample {

    public static void main(String[] args){
        int searchId = 1;
        String searchRegistration = "OA04SJ4S1";
        List<Vehicle> vehicles;
        Vehicle found;

        VehicleApi vehicleApi = new VehicleApi(new H2DatabaseConnector("./test", "", ""));

        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------Running-Vehicle-API-----------------------------------");

        showAll(vehicleApi);

        // INSERT
        System.out.println("\nInserting vehicles\n");
        vehicleApi.insert(new Vehicle(10, "AK4MVJ4S1"));
        vehicleApi.insert(new Vehicle(23, searchRegistration));
        vehicleApi.insert(new Vehicle(16, "AK440F4S1"));

        showAll(vehicleApi);

        // FIND BY ID
        System.out.println("\nSearching for vehicle with id " + searchId);
        found = vehicleApi.findById(searchId);
        showVehicle(found);

        // FIND BY REGISTRATION
        System.out.println("\nFinding by registration " + searchRegistration);
        found = vehicleApi.findByRegistration(searchRegistration);
        showVehicle(found);

        // UPDATE A VEHICLE
        String updateIds = "0,1,2,3";
        System.out.println("\nUpdating " + found.getId() + " with ids " + updateIds);
        vehicleApi.update(new Vehicle(found.getId(), 0, found.getRegistration(), updateIds));
        showAll(vehicleApi);

        // FIND BY ID
        System.out.println("\nFinding updated vehicle by registration");
        found = vehicleApi.findByRegistration(searchRegistration);
        showVehicle(found);

        System.out.println("\nDeleting vehicle with id " + searchId);
        vehicleApi.delete(searchId);

        showAll(vehicleApi);

        // CHECK FOR DELETED VEHICLE
        System.out.println("\nFinding updated vehicle by id " + searchId);
        found = vehicleApi.findById(searchId);
        showVehicle(found);

        // DELETE ALL
        System.out.println("\nDeleting all");
        vehicleApi.truncate();
        showAll(vehicleApi);

        System.out.println("\n------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");

        // remove test database from root directory
        DeleteDbFiles.execute(".", "test", false);
    }

    private static void showAll(VehicleApi api){
        System.out.println("--Showing all results--");
        List<Vehicle> vehicles = api.findAll();
        // no need to check for null we will only get a list (empty) or a list with vehicles.
        if(vehicles.size() < 1){
            System.out.println("-empty-");
        }
        vehicles.forEach(v -> {
            showVehicle(v);
        });
    }

    private static void showVehicle(Vehicle vehicle){
        System.out.println((vehicle == null)? "-not found-":
                vehicle.getId() + " " + vehicle.getMileage() + " " + vehicle.getRegistration() + " " + toCsv(vehicle.getScheduledTaskIds()));
    }

    private static String toCsv(int...values){
        StringBuilder csv = new StringBuilder();
        if(values != null && values.length > 0){
            for(int i = 0; i < values.length; i++){
                csv.append(values[i]);
                if(i < values.length - 1){
                    csv.append(",");
                }
            }
        }else{
            return "null";
        }
        return csv.toString();
    }

}
