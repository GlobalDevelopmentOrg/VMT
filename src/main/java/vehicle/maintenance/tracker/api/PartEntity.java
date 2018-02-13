package vehicle.maintenance.tracker.api;

/**
 * PartEntity represents a detachable piece of machinery
 * that can be installed to a vehicle and have separate
 * maintenance schedules. 
 *
 * @author Joe
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public final class PartEntity {

    private int id;
    private int vehicleId;
    private String installationDate;
    private String name;

    public PartEntity(int id, int vehicleId, String name, String installationDate) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.name = name;
        this.installationDate = installationDate;
    }

    public PartEntity(int vehicleId, String name, String installationDate){
        this.vehicleId = vehicleId;
        this.name = name;
        this.installationDate = installationDate;
    }

    public final int getId() {
        return this.id;
    }

    public final int getVehicleId(){
        return this.vehicleId;
    }

    public final void setVehicleId(int vehicleId){
        this.vehicleId = vehicleId;
    }

    public final String getInstallationDate() {
        return this.installationDate;
    }

    public final void setInstallationDate(String installationDate){
        this.installationDate = installationDate;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String name){
        this.name = name;
    }

    public final String getIdForTasks(){
        return "part_" + this.id;
    }

    @Override
    public final String toString(){
        return this.getId() + " " + this.getVehicleId() + " " + this.getName() + " " + this.getInstallationDate() + " " + this.getIdForTasks() + "\n";
    }

}
