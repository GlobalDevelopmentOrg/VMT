package vehicle.maintenance.tracker;

import java.util.Date;

/**
 * Part represents a detachable piece of machinery 
 * that can be installed to a vehicle and have seperate 
 * maintenance schedules. 
 *
 * @author add your name
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public class Part {

    private int id;
    private vehicleId; // id of vehicle this part belongs too
    private Date installDate;
    private Date scheduledMaintenanceDate;
    private String name;

    // we can use this when we have data from database.
    public Part(int id, int vehicleId, Date installDate, Date scheduledMaintenanceDate, String name) {
        this.id = id;
	this.vehicleId = vehicleId;
        this.installDate = installDate;
        this.scheduledMaintenanceDate = scheduledMaintenanceDate;
        this.name = name;
    }

    // we use this constructor id will be auto generated when we submit it to database.
    public Part(int vehicleId, Date installDate, Date scheduledMaintenanceDate, String name){
	this.vehicleId = vehicleId;
	this.installDate = installDate;
	this.scheduledMaintenanceDate = scheduledMaintenanceDate;
	this.name = name; 
    }

    public int getid() {
        return this.id;
    }

    public Date getinstallDate() {
        return this.installDate;
    }

    public Date getScheduledMaintenanceDate() {
        return this.scheduledMaintenanceDate;
    }

    public String getname() {
        return this.name;
    }

}
