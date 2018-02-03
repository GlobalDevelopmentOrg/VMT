package vehicle.maintenance.tracker.model;

/**
 * A <code>VehiclePart</code> represents a part of a vehicle that can be installed
 * and removed. Have its own maintenance requirements
 *
 * @Author Daile Alimo
 * @Since 0.1-SNAPSHOT
 */
public class VehiclePart {

    private long id;
    private long vehicleId; // id this part belongs to
    private String name;
    private int mileage;

    public VehiclePart(long id, long vehicleId, String name, int mileage){
        this.id = id;
        this.vehicleId = vehicleId;
        this.name = name;
        this.mileage = mileage;
    }

    public VehiclePart(long vehicleId, String name, int mileage){
        this.vehicleId = vehicleId;
        this.name = name;
        this.mileage = mileage;
    }

    public long getId(){
        return this.id;
    }

    public long getVehicleId(){
        return this.vehicleId;
    }

    public String getName(){
        return this.name;
    }

    public int getMileage(){
        return this.mileage;
    }

}
