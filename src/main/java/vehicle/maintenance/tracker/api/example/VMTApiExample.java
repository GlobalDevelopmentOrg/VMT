package vehicle.maintenance.tracker.api.example;

import vehicle.maintenance.tracker.api.VMTApi;
import vehicle.maintenance.tracker.api.entity.PartEntity;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;
import vehicle.maintenance.tracker.api.exceptions.EntityDoesNotExistException;
import vehicle.maintenance.tracker.api.models.PartInfoModel;
import vehicle.maintenance.tracker.api.models.VehicleInfoModel;

public class VMTApiExample {

    public static void main(String[] args){
        VMTApi api = new VMTApi();

        // Here we create a vehicle info model (which is also saved into the database)
        // this model will allow us to modify and add task
        VehicleInfoModel vehicleInfoModel = api.createNewVehicleInfoModel(new VehicleEntity(10, "Toyota", "EFS84FOA01"));

        // lets add a task
        vehicleInfoModel.addTaskEntity("Checkup", "Basic checkup\n-refill water\n-oil\n-wheel pressure", "22/03/18");

        // we will need to commit these changes to the database
        try{
            api.commit(vehicleInfoModel);
        }catch(EntityDoesNotExistException e){
            System.err.println("Use createNewVehicleInfoModel method in the VMTApi to create a vehicle before you try update it!");
            e.printStackTrace();
        }

        // lets add a new part to this vehicle
        PartInfoModel partInfoModel = api.createNewPartInfoModel(new PartEntity(vehicleInfoModel.getVehicleEntity().getId(), "Radiator", "16/02/18"));
        partInfoModel.addTaskEntity("Check status", "Ensure radiator is working properly", "22/03/18");

        // we will need to commit these changes to the database
        try{
            api.commit(partInfoModel);
        }catch(EntityDoesNotExistException e){
            System.err.println("Use createNewVehicleInfoModel method in the VMTApi to create a vehicle before you try update it!");
            e.printStackTrace();
        }

        // in order to load any new parts we need to reload the
        // vehicle info model
        vehicleInfoModel = api.getVehicleInfoModel(vehicleInfoModel.getVehicleEntity().getId());

        System.out.println(vehicleInfoModel);

        api.clearAll();
    }

}
