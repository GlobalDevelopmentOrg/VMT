package vehicle.maintenance.tracker;

import java.util.Date;

public class Part {
    // Constructor
    public Part() {
        ID = 0;
        // Creates the install_date to the current date
        Install_date = new Date();
        // Need to do some math to set a future date of when a part needs to be checked
        // Maintain_date = something
        Name = "none";
    }

    public Part(int id, Date install_date, Date maintain_date, String name) {
        ID = id;
        Install_date = install_date;
        Maintain_date = maintain_date;
        Name = name;
    }

    // Basic data types for a part
    private int ID;
    private Date Install_date;
    private Date Maintain_date;
    private String Name;

    // Getters and setters
    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }

    public Date getInstall_date() {
        return Install_date;
    }

    public void setInstall_date(Date date) {
        Install_date = date;
    }

    public Date getMaintain_date() {
        return Maintain_date;
    }

    public void setMaintain_date(Date date) {
        Maintain_date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
