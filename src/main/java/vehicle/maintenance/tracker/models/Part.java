package vehicle.maintenance.tracker;

import java.util.Date;

/**
 * Part represents a detachable piece of machinery 
 * that can be installed to a vehicle and have seperate 
 * maintenance schedules. 
 *
 * @author add your name
 * @since 0.1-SNAPSHOT
 */
public class Part {

    private int id;
    private Date installDate;
    private Date scheduledMaintenanceDate;
    private String name;

    public Part() {
        this.id = 0;
        this.installDate = new Date();
	// TODO calculate or allow client to set a scheduled maintenants date
	// for now just instantiate it to prevent null pointer errors.
	this.scheduledMaintenanceDate = this.installDate;
        this.name = "none";
    }

    public Part(int id, Date installDate, Date maintainDate, String name) {
        this.id = id;
        this.installDate = installDate;
        this.scheduledMaintenanceDate = scheduledMaintenanceDate;
        this.name = name;
    }

    public int getid() {
        return this.id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public Date getinstallDate() {
        return this.installDate;
    }

    public void setinstallDate(Date date) {
        this.installDate = date;
    }

    public Date getmaintainDate() {
        return this.maintainDate;
    }

    public void setmaintainDate(Date date) {
        this.maintainDate = date;
    }

    public String getname() {
        return this.name;
    }

    public void setname(String name) {
        this.name = name;
    }

}
