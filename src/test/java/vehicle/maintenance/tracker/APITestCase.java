package vehicle.maintenance.tracker;

import org.junit.Assert;
import org.junit.Test;
import vehicle.maintenance.tracker.api.VMTApi;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;

public class APITestCase {

    @Test
    public void testAPI(){
        VMTApi api = new VMTApi();
        VehicleEntity loadedInstance;
        VehicleEntity vehicleEntity = new VehicleEntity("unnamed", "01KF39FJ3", 59122);

        // change unnamed vehicle entity name to Toyota
        vehicleEntity.setName("Toyota");

        // commit changes
        api.commitVehicle(vehicleEntity);

        // assert changes made
        Assert.assertEquals("Toyota", vehicleEntity.getName());
        Assert.assertEquals("01KF39FJ3", vehicleEntity.getRegistration());
        Assert.assertEquals(59122, vehicleEntity.getMileage());

        // assert saved
        loadedInstance = api.getVehicle(vehicleEntity.getId());
        Assert.assertEquals(vehicleEntity.getName(), loadedInstance.getName());
        Assert.assertEquals(vehicleEntity.getRegistration(), loadedInstance.getRegistration());
        Assert.assertEquals(vehicleEntity.getMileage(), loadedInstance.getMileage());

        // change mileage
        vehicleEntity.setMileage(59999);

        // commit changes
        api.commitVehicle(vehicleEntity);

        // assert updated
        Assert.assertEquals(59999, vehicleEntity.getMileage());

        // assert updated
        loadedInstance = api.getVehicle(vehicleEntity.getId());
        Assert.assertEquals(vehicleEntity.getMileage(), loadedInstance.getMileage());

        // clean up
        api.deleteVehicle(vehicleEntity);

        // assert removed
        Assert.assertNull(api.getVehicle(vehicleEntity.getId()));

    }

}
