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

        // original created instances NOT SAVED
        System.out.println("Showing created entities, NOT SAVED");
        System.out.println("unsaved " + vehicleEntity);
        System.out.println("unsaved " + taskEntity1);
        System.out.println("unsaved " + taskEntity2);
        System.out.println();

        // The update method both updates the entity and commits the changes.
        // since the commit method either updates an entity or inserts it if it does
        // not exist, we can both update and save an entity.
        //
        // The entity we pass will be effected by the changes we make
        // inside the update method.
        //
        // this is the same for all variations of the update method.
        //
        // lets update and commit the vehicle defined above
        // we will update its mileage
        api.update(vehicleEntity, entity -> {
            entity.setMileage(650);
        });

        System.out.println("our vehicle entity without explicit reload\n" + vehicleEntity);

        // some api methods can be chained
        //
        // lets commit the first task unchanged
        // then we will update the second task and
        // change its deadline date.
        //
        // then using chaining we will print out
        // all tasks for the vehicle entity defined above
        api.commitTask(taskEntity1).update(taskEntity2, entity -> {
            entity.setDueDate("22/12/18");
        }).getTasks(vehicleEntity).forEach(System.out::println);

    }

}
