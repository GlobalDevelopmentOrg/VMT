package vehicle.maintenance.tracker;

import java.util.List;

public class Truck {

    // Data types
    private int Mileage;
    private int ID;
    private String Registration;
    private List Parts;

    // Getters and setters

    public int getMileage() {
        return Mileage;
    }

    public void setMileage(int mileage) {
        Mileage = mileage;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        ID = id;
    }

    public String getRegistration() {
        return Registration;
    }

    public void setRegistration(String registration) {
        Registration = registration;
    }

    public List getParts() {
        return Parts;
    }

    // If we are only adding 1 part
    public void addParts(Part part) {
        Parts.add(part);
    }

    // Add if we are adding a list of parts in
    public void addParts(List parts) {
        Parts.addAll(parts);
    }

    // If we are starting with the list of parts
    public void setParts(List parts) {

    }

    public boolean hasParts() {
        if (Parts.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


}