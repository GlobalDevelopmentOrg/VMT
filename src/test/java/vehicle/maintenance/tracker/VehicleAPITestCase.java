package vehicle.maintenance.tracker;

import org.h2.tools.DeleteDbFiles;
import org.junit.*;
import vehicle.maintenance.tracker.api.VehicleApi;
import vehicle.maintenance.tracker.api.database.H2DatabaseConnector;
import vehicle.maintenance.tracker.models.Vehicle;

import java.util.List;

/**
 * Unit tests ensure we run tests before making a pull request.
 * All tests should run.
 * <p>
 * Write tests for new classes you create,
 * this is ensure other developers don't break your code.
 */
public class VehicleAPITestCase {

    private static final VehicleApi vehicleApi = new VehicleApi(new H2DatabaseConnector("./test", "", ""));
    private static final Vehicle toyota = new Vehicle(5032, "07AXB903E");
    private static final Vehicle bmw = new Vehicle(9032, "00BG4LS3S");

    @BeforeClass
    public static void initVehicles(){
        vehicleApi.insert(toyota);
        vehicleApi.insert(bmw);
    }

    @Test
    public void testFindAll(){
        List<Vehicle> found = vehicleApi.findAll();
        Assert.assertEquals(toyota.getRegistration(), found.get(0).getRegistration());
        Assert.assertEquals(bmw.getRegistration(), found.get(1).getRegistration());
    }

    @Test
    public void testUpdate(){
        String taskCsv = "0,10,1003";
        String newRegistration = "CHANGED";
        // we know the first entry in the database will have id 1, next 2, ect
        Vehicle update = new Vehicle(1, toyota.getMileage(), newRegistration, taskCsv);
        vehicleApi.update(update);

        // check
        Assert.assertEquals(newRegistration, vehicleApi.findById(1).getRegistration());

        Vehicle toyota = vehicleApi.findById(1);
        Assert.assertNotNull(toyota);
        Assert.assertEquals(0, toyota.getScheduledTaskIds()[0]);
        Assert.assertEquals(10, toyota.getScheduledTaskIds()[1]);
        Assert.assertEquals(1003, toyota.getScheduledTaskIds()[2]);
    }

    @AfterClass
    public static void deleteDatabase(){
        // remove test database from root directory
        DeleteDbFiles.execute(".", "test", false);
    }

}
