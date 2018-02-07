package vehicle.maintenance.tracker.models;

import java.util.List;

/**
 * Vehicle represents a tracked entity within the database 
 * we can use to schedule maintenance.
 *
 * @author Joe
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public class Vehicle {

    private int id;
    private int mileage;
    private String registration;
    private int[] scheduledTaskIds;

    /*
            We have 2 constructors, the first for loading from the database
            the second for creating a new entry to put into the database.
            This class is immutable we do not wish to change data here once
            instantiated. Instead we will update the database with any
            changes made.
     */
    public Vehicle(int id, int mileage, String registration, String scheduledTaskIdsCSV){
        this.id = id;
        this.mileage = mileage;
        this.registration = registration;

        // we get given a csv (comma separated value) string from the database
        // we have to test and parse that.
        // due to immutability we can only ever load a vehicles schedule from the database
        // because of this we will have to refresh the model when a vehicle is updated
        if(scheduledTaskIdsCSV != null && scheduledTaskIdsCSV.length() > 0 && scheduledTaskIdsCSV.contains(",")){
            String values[] = scheduledTaskIdsCSV.split(",");
            this.scheduledTaskIds = new int[values.length];
            for(int i = 0; i < this.scheduledTaskIds.length; i++){
                this.scheduledTaskIds[i] = Integer.parseInt(values[i]);
            }
        }
    }

    public Vehicle(int mileage, String registration){
        this.mileage = mileage;
        this.registration = registration;
    }

    public int getMileage() {
        return this.mileage;
    }

    public int getId() {
        return this.id;
    }

    public String getRegistration() {
        return this.registration;
    }

    public int countScheduledTasks(){
        return this.scheduledTaskIds != null ? this.scheduledTaskIds.length : 0;
    }

    public int getScheduledTask(int index){
        return this.scheduledTaskIds[index];
    }

}
