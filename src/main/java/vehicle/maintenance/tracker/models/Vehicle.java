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

    /*
            We have 2 constructors, the first for loading from the database
            the second for creating a new entry to put into the database.
            This class is immutable we do not wish to change data here once
            instantiated. Instead we will update the database with any
            changes made.
     */
    public Vehicle(int id, int mileage, String registration){
        this.id = id;
        this.mileage = mileage;
        this.registration = registration;
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

}
