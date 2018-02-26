package vehicle.maintenance.tracker.api.entity;

import java.util.UUID;

public class Entity {

    private String id;
    private String name;
    private String notes;

    public Entity(){}

    public Entity(String name, String notes){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.notes = notes;
    }

    public Entity(String id, String name, String notes){
        this.id = id;
        this.name = name;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getNotes(){
        return this.notes;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

}
