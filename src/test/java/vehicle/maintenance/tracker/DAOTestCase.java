package vehicle.maintenance.tracker;

import org.junit.Assert;
import org.junit.Test;
import vehicle.maintenance.tracker.replaced.storage.PartDAOSingleton;
import vehicle.maintenance.tracker.replaced.storage.TaskDAOSingleton;
import vehicle.maintenance.tracker.replaced.storage.VehicleDAOSingleton;
import vehicle.maintenance.tracker.api.entity.PartEntity;
import vehicle.maintenance.tracker.api.entity.TaskEntity;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;
import vehicle.maintenance.tracker.replaced.exceptions.DAOInitException;

public class DAOTestCase {

    @Test
    public void testVehicleDAO(){
        VehicleEntity entity = new VehicleEntity("Toyota", "", "01GI50RJ1", 537);
        VehicleDAOSingleton vehicleDAOSingleton = null;
        try{
            vehicleDAOSingleton = VehicleDAOSingleton.getInstance();
        }catch(DAOInitException e){
            Assert.assertNull(e);
        }
        if(vehicleDAOSingleton == null){
            vehicleDAOSingleton.insert(entity);
            // do an update
            entity.setName("Toyota v2");
            vehicleDAOSingleton.update(entity);
            // override old instance with instance from the database
            entity = vehicleDAOSingleton.findById(entity.getId());
            // test changes apply
            Assert.assertEquals("Toyota 2", entity.getName());
            // clean up
            vehicleDAOSingleton.delete(entity);
            // checked removed
            Assert.assertNull(vehicleDAOSingleton.findById(entity.getId()));
        }
    }

    @Test
    public void testPartDAO(){
        PartEntity entity = new PartEntity("", "", "Wheel", "22/12/91");
        PartDAOSingleton partDAOSingleton = null;
        try{
            partDAOSingleton = PartDAOSingleton.getInstance();
        }catch(DAOInitException e){
            Assert.assertNull(e);
        }
        if(partDAOSingleton == null){
            partDAOSingleton.insert(entity);
            // do an update
            entity.setName("Wheels");
            partDAOSingleton.update(entity);
            // override old instance with instance from the database
            entity = partDAOSingleton.findById(entity.getId());
            // test changes apply
            Assert.assertEquals("Wheels", entity.getName());
            // clean up
            partDAOSingleton.delete(entity);
            // checked removed
            Assert.assertNull(partDAOSingleton.findById(entity.getId()));
        }
    }

    @Test
    public void testTaskDAO(){
        TaskEntity entity = null;

        entity = new TaskEntity("", "name", "comment", "22/12/14");

        TaskDAOSingleton taskDAOSingleton = null;
        try{
            taskDAOSingleton = TaskDAOSingleton.getInstance();
        }catch(DAOInitException e){
            Assert.assertNull(e);
        }
        if(taskDAOSingleton == null){
            taskDAOSingleton.insert(entity);
            // do an update
            entity.setName("changed");
            taskDAOSingleton.update(entity);
            // override old instance with instance from the database
            entity = taskDAOSingleton.findById(entity.getId());
            // test changes apply
            Assert.assertEquals("changed", entity.getName());
            // clean up
            taskDAOSingleton.delete(entity);
            // checked removed
            Assert.assertNull(taskDAOSingleton.findById(entity.getId()));
        }
    }

}
