package vehicle.maintenance.tracker;

import java.util.Date;

public class Part {
    // Basic data types for a part
    private int id;
    private Date installDate;
    private Date maintainDate;
    private String name;

    // Constructor
    public Part() {
        this.id = 0;
        // Creates the installDate to the current date
        this.installDate = new Date();
        // Need to do some math to set a future date of when a part needs to be checked
        // maintainDate = something
        this.name = "none";
    }

    public Part(int id, Date installDate, Date maintainDate, String name) {
        this.id = id;
        this.installDate = installDate;
        this.maintainDate = maintainDate;
        this.name = name;
    }

    // Getters and setters
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
