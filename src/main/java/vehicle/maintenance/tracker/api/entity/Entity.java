package vehicle.maintenance.tracker.api.entity;

import java.util.UUID;

public class Entity {

    private String id;
    private String name;

    public Entity(String name){
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }

    public Entity(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

}
