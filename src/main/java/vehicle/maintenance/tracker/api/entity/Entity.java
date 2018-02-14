package vehicle.maintenance.tracker.api.entity;

public class Entity {

    private int id;

    public Entity(){
        this.id = -1;
    }

    public Entity(int id){
        this.id = id;
    }

    public boolean hasIdentity(){
        return this.id > 0;
    }

    public int getId() {
        return id;
    }
}
