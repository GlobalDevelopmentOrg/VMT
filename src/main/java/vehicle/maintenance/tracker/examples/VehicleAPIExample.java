package vehicle.maintenance.tracker.examples;

import org.h2.tools.DeleteDbFiles;
import vehicle.maintenance.tracker.api.VehicleApi;
import vehicle.maintenance.tracker.api.database.H2DatabaseConnector;
import vehicle.maintenance.tracker.api.database.sql.SQLStatementFactory;
import vehicle.maintenance.tracker.api.database.sql.statements.SQLDataType;
import vehicle.maintenance.tracker.api.database.sql.statements.SQLStatement;
import vehicle.maintenance.tracker.models.Vehicle;

import java.sql.SQLException;
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
        Vehicle found;

        VehicleApi vehicleApi = new VehicleApi(new H2DatabaseConnector("./test", "", ""));

        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------Running-Vehicle-API-----------------------------------");

        // INSERT
        System.out.println("\nInserting vehicles\n");
        vehicleApi.insert(new Vehicle(10, "AK4MVJ4S1"));
        vehicleApi.insert(new Vehicle(23, searchRegistration));
        vehicleApi.insert(new Vehicle(16, "AK440F4S1"));

        // FIND BY ID
        System.out.println("Searching for vehicle with id " + searchId);
        found = vehicleApi.findById(searchId);
        if(found != null){
            System.out.println("Found vehicle in database by id " + searchId);
            System.out.println(found.getId() + " " + found.getMileage() + " " + found.getRegistration());
        }else{
            System.out.println("Unable to find vehicle with id " + searchId);
        }

        // FIND ALL
        System.out.println("\nFinding all vehicles");
        List<Vehicle> vehicles = vehicleApi.findAll();
        // no need to check for null we will only get a list (empty) or a list with vehicles.
        vehicles.forEach(v -> {
            System.out.println(v.getId() + " " + v.getMileage() + " " + v.getRegistration());
        });

        // FIND BY REGISTRATION
        System.out.println("\nFinding by registration");
        found = vehicleApi.findByRegistration(searchRegistration);
        if(found != null){
            System.out.println("Found vehicle in database by registration " + searchRegistration);
            System.out.println(found.getId() + " " + found.getMileage() + " " + found.getRegistration());
        }else{
            System.out.println("Unable to find vehicle with registration " + searchRegistration);
        }

        System.out.println("\n------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");

        // remove test database from root directory
        DeleteDbFiles.execute(".", "test", false);
    }

}
