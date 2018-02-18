package vehicle.maintenance.tracker.api.entity;

/**
 * VehicleEntity represents a tracked entity within the database
 * we can use to schedule maintenance.
 *
 * @author Joe
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public final class VehicleEntity extends Entity {

    private int mileage;
    private String registration;

    public VehicleEntity(String name, String registration, int mileage){
        super(name);
        this.mileage = mileage;
        this.registration = registration;
    }

    public VehicleEntity(String id, String name, String registration, int mileage){
        super(id, name);
        this.mileage = mileage;
        this.registration = registration;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    @Override
    public final String toString(){
        return "Vehicle[:id] :name[:registration] for :mileage miles"
                .replace(":id", String.valueOf(this.getId()))
                .replace(":name", super.getName())
                .replace(":registration", this.getRegistration())
                .replace(":mileage", String.valueOf(this.getMileage()));
    }

}
