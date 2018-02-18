package vehicle.maintenance.tracker;

import org.junit.Assert;
import org.junit.Test;
import vehicle.maintenance.tracker.api.VMTApi;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;

public class APITestCase {

    @Test
    public void testAPI(){
        VMTApi api = new VMTApi();
        VehicleEntity vehicleEntity = new VehicleEntity("unnamed", "01KF39FJ3", 59122);

        // both insert and update!
        api.update(vehicleEntity, update -> {
            update.setName("Toyota");
        });

        Assert.assertEquals("Toyota", vehicleEntity.getName());
        Assert.assertEquals("01KF39FJ3", vehicleEntity.getRegistration());
        Assert.assertEquals(59122, vehicleEntity.getMileage());

        // update mileage
        api.update(vehicleEntity, update -> {
            update.setMileage(59999);
        });

        // assert updated
        Assert.assertEquals(59999, vehicleEntity.getMileage());

        // clean up
        api.deleteVehicle(vehicleEntity);

        // assert removed
        Assert.assertNull(api.getVehicle(vehicleEntity.getId()));

    }

}
