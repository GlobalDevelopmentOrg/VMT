package vehicle.maintenance.tracker;

import java.util.Date;

public class Part {
    // Constructor
    public Part() {
        id = 0;
        // Creates the installDate to the current date
        installDate = new Date();
        // Need to do some math to set a future date of when a part needs to be checked
        // maintainDate = something
        name = "none";
    }

    public Part(int id, Date installDate, Date maintainDate, String name) {
        id = this.id;
        installDate = this.installDate;
        maintainDate = this.maintainDate;
        name = this.name;
    }

    // Basic data types for a part
    private int id;
    private Date installDate;
    private Date maintainDate;
    private String name;

    // Getters and setters
    public int getid() {
        return id;
    }

    public void setid(int id) {
        id = this.id;
    }

    public Date getinstallDate() {
        return installDate;
    }

    public void setinstallDate(Date date) {
        installDate = date;
    }

    public Date getmaintainDate() {
        return maintainDate;
    }

    public void setmaintainDate(Date date) {
        maintainDate = date;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        name = this.name;
    }

}
