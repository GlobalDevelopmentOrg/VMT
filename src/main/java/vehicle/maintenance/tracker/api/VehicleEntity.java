package vehicle.maintenance.tracker.api;

/**
 * VehicleEntity represents a tracked entity within the database
 * we can use to schedule maintenance.
 *
 * @author Joe
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public final class VehicleEntity {

    private int id;
    private int mileage;
    private String registration;

    public VehicleEntity(int id, int mileage, String registration){
        this.id = id;
        this.mileage = mileage;
        this.registration = registration;
    }

    public VehicleEntity(int mileage, String registration){
        this.mileage = mileage;
        this.registration = registration;
    }

    public final int getId() {
        return this.id;
    }

    public final int getMileage() {
        return this.mileage;
    }

    public final void setMileage(int mileage){
        this.mileage = mileage;
    }

    public final void incrementMileage(int increment){
        this.mileage += increment;
    }

    public final String getRegistration() {
        return this.registration;
    }

    public final void setRegistration(String registration){
        this.registration = registration;
    }

    public final String getIdForTasks(){
        return "vehicle_" + this.id;
    }

    @Override
    public final String toString(){
        return this.getId() + " " + this.getRegistration() + " " + this.getMileage() + " " + this.getIdForTasks() + "\n";
    }

}
