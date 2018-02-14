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
    private String name;
    private String registration;

    public VehicleEntity(int id, String name, int mileage, String registration){
        super(id);
        this.name = name;
        this.mileage = mileage;
        this.registration = registration;
    }

    public VehicleEntity(int mileage, String name, String registration){
        super();
        this.name = name;
        this.mileage = mileage;
        this.registration = registration;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public final String getIdForTasks(){
        return "vehicle_" + super.getId();
    }

    @Override
    public final String toString(){
        return "Vehicle[:id] :name[:registration] for :mileage miles"
                .replace(":id", String.valueOf(this.getId()))
                .replace(":name", this.getName())
                .replace(":registration", this.getRegistration())
                .replace(":mileage", String.valueOf(this.getMileage()));
    }

}
