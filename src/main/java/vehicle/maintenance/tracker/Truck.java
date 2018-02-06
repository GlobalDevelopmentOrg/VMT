package vehicle.maintenance.tracker;

import java.util.List;

public class Truck {
    // Data types
    private int mileage;
    private int id;
    private String registration;
    private List parts;

    // Getters and setters
    public int getMileage() {
        return this.mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getRegistration() {
        return this.registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public List getParts() {
        return this.parts;
    }

    // If we are only adding 1 part
    public void addParts(Part part) {
        this.parts.add(part);
    }

    // Add if we are adding a list of parts in
    public void addParts(List parts) {
        this.parts.addAll(parts);
    }

    // If we are starting with the list of parts
    public void setParts(List parts) {

    }

    public boolean hasParts() {
        if (this.parts.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


}