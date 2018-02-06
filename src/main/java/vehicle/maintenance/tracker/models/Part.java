package vehicle.maintenance.tracker.models;

import java.util.Date;

/**
 * Part represents a detachable piece of machinery 
 * that can be installed to a vehicle and have seperate 
 * maintenance schedules. 
 *
 * @author Joe
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public class Part {

    private int id;
    private int vehicleId; // id of vehicle this part belongs too
    private Date installationDate;
    private Date scheduledMaintenanceDate;
    private String name;

    /*
            We have 2 constructors, the first for loading from the database
            the second for creating a new entry to put into the database.
            This class is immutable we do not wish to change data here once
            instantiated. Instead we will update the database with any
            changes made.
     */
    public Part(int id, int vehicleId, Date installationDate, Date scheduledMaintenanceDate, String name) {
         this.id = id;
         this.vehicleId = vehicleId;
         this.installationDate = installationDate;
         this.scheduledMaintenanceDate = scheduledMaintenanceDate;
         this.name = name;
    }

    public Part(int vehicleId, Date installationDate, Date scheduledMaintenanceDate, String name){
        this.vehicleId = vehicleId;
        this.installationDate = installationDate;
        this.scheduledMaintenanceDate = scheduledMaintenanceDate;
        this.name = name;
    }

    public int getid() {
        return this.id;
    }

    public int getVehicleId(){
        return this.vehicleId;
    }

    public Date getinstallationDate() {
        return this.installationDate;
    }

    public Date getScheduledMaintenanceDate() {
        return this.scheduledMaintenanceDate;
    }

    public String getname() {
        return this.name;
    }

}
