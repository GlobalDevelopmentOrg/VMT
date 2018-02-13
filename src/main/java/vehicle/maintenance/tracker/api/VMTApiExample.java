package vehicle.maintenance.tracker.api;

import vehicle.maintenance.tracker.api.exceptions.EntityDoesNotExistException;
import vehicle.maintenance.tracker.api.models.PartInfoModel;
import vehicle.maintenance.tracker.api.models.VehicleInfoModel;

public class VMTApiExample {

    public static void main(String[] args){
        VMTApi api = new VMTApi();

        // Here we create a vehicle info model (which is also saved into the database)
        // this model will allow us to modify and add task
        VehicleInfoModel vehicleInfoModel = api.createNewVehicleInfoModel(new VehicleEntity(10, "EFS84FOA01"));

        // lets add a task
        vehicleInfoModel.addTaskEntity("Check brakes", "28/19/19");

        // we will need to commit these changes to the database
        try{
            api.commit(vehicleInfoModel);
        }catch(EntityDoesNotExistException e){
            // this will only happen if your vehicle doesn't exist in the database
            // if you use createNewVehicleInfoModel this will ensure it does.
            System.err.println("Use createNewVehicleInfoModel method in the VMTApi to create a vehicle before you try update it!");
            e.printStackTrace();
        }
        // now the updates are added to the database.
        // lets have a look
        System.out.println("showing data in the database\n " +api.getVehicleInfoModel(vehicleInfoModel.getVehicleEntity().getId()));

        // add a new part
        // we do the same as for the vehicle info model
        PartInfoModel partInfoModel = api.createNewPartInfoModel(new PartEntity(vehicleInfoModel.getVehicleEntity().getId(),"Steering Wheel", "22/12/91"));
        partInfoModel.addTaskEntity("Checkup", "22/03/18");

        // and now we commit these changes
        try{
            api.commit(partInfoModel);
        }catch(EntityDoesNotExistException e){
            // this will only happen if your vehicle doesn't exist in the database
            // if you use createNewVehicleInfoModel this will ensure it does.
            System.err.println("Use createNewVehicleInfoModel method in the VMTApi to create a vehicle before you try update it!");
            e.printStackTrace();
        }

        // we can see the changes in the database here
        System.out.println("showing data in the database\n " +api.getVehicleInfoModel(vehicleInfoModel.getVehicleEntity().getId()));

        // why can't we see them in our current instance of the vehicle info?
        System.out.println("showing current unsaved instance\n " + vehicleInfoModel);

        // we need to update the database or commit changes once we have made them!

        System.out.println("clearing data");

        api.clearAll();
    }

}
