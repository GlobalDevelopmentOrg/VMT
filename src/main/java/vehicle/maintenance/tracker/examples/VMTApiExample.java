package vehicle.maintenance.tracker.examples;

import vehicle.maintenance.tracker.api.VMTApi;
import vehicle.maintenance.tracker.api.entity.TaskEntity;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;

public class VMTApiExample {

    public static void main(String[] args){
        VMTApi api = new VMTApi();

        VehicleEntity vehicleEntity = new VehicleEntity("Toyota", "FOE30DM310", 500);
        TaskEntity taskEntity1 = new TaskEntity(vehicleEntity.getId(), "Task 1", "Comments", "22/12/01");
        TaskEntity taskEntity2 = new TaskEntity(vehicleEntity.getId(), "Task 2", "Comments", "22/12/01");

        // commit to database (chained)
        api.commitVehicle(vehicleEntity)
                .commitTask(taskEntity1)
                .commitTask(taskEntity2);

        // update vehicle info
        vehicleEntity.setMileage(2451);

        // commit vehicle changes
        api.commitVehicle(vehicleEntity);

        // change tasks
        taskEntity1.setName("renamed task 1");
        taskEntity2.setName("renamed task 2");

        System.out.println("printing tasks for " + vehicleEntity);

        // Using chaining we will commit changes to the tasks
        // then print out each task for the vehicle entity defined above
        api.commitTask(taskEntity1)
                .commitTask(taskEntity2)
                .getTasks(vehicleEntity)
                .forEach(System.out::println);

    }

}
