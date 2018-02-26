package vehicle.maintenance.tracker.api.entity;

/**
 * PartEntity represents a detachable piece of machinery
 * that can be installed to a vehicle and have separate
 * maintenance schedules. 
 *
 * @author Joe
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public final class PartEntity extends Entity {

    private String vehicleId;
    private String installationDate;

    public PartEntity(){
        super();
    }

    public PartEntity(String vehicleId, String name, String notes, String installationDate){
        super(name, notes);
        this.vehicleId = vehicleId;
        this.installationDate = installationDate;
    }

    public PartEntity(String id, String vehicleId, String name, String notes, String installationDate){
        super(id, name, notes);
        this.vehicleId = vehicleId;
        this.installationDate = installationDate;
    }

    public final String getVehicleId(){
        return this.vehicleId;
    }

    public final void setVehicleId(String vehicleId){
        this.vehicleId = vehicleId;
    }

    public final String getInstallationDate() {
        return this.installationDate;
    }

    public final void setInstallationDate(String installationDate){
        this.installationDate = installationDate;
    }

    @Override
    public final String toString(){
        return "Part :name installed on :date"
                .replace(":name", super.getName())
                .replace(":date", this.getInstallationDate());
    }

}
