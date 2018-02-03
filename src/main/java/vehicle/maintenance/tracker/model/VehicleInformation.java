package vehicle.maintenance.tracker.model;

/**
 * A <code>VehicleInformation</code> represents a vehicle database entry
 *
 * @Author Daile Alimo
 * @Since 0.1-SNAPSHOT
 */
public class VehicleInformation {

    private Long id;
    private String name;
    private String registration;
    private int mileage;

    public VehicleInformation(Long id, String name, String registration, int mileage){
        this.id = id;
        this.name = name;
        this.registration = registration;
        this.mileage = mileage;
    }

    public VehicleInformation(String name, String registration, int mileage){
        this.name = name;
        this.registration = registration;
        this.mileage = mileage;
    }

    public Long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getRegistration(){
        return this.registration;
    }

    public int getMileage(){
        return this.mileage;
    }

}
