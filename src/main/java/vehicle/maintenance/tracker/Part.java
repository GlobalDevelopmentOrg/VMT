package vehicle.maintenance.tracker;

import java.util.Date;

public class Part {
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
