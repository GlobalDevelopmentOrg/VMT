package vehicle.maintenance.tracker.api.entity;

public final class TaskEntity extends Entity {

    private String parentId;
    private String name;
    private String comment;
    private String deadLineDate;

    public TaskEntity(int id, String parentId, String name, String comment, String deadLineDate){
        super(id);
        this.parentId = parentId;
        this.name = name;
        this.comment = comment;
        this.deadLineDate = deadLineDate;
    }

    public TaskEntity(String parentId, String name, String comment, String deadLineDate){
        super();
        this.parentId = parentId;
        this.name = name;
        this.comment = comment;
        this.deadLineDate = deadLineDate;
    }

    public String getParentId(){
        return this.parentId;
    }

    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDeadLineDate() {
        return deadLineDate;
    }

    public void setDeadLineDate(String deadLineDate) {
        this.deadLineDate = deadLineDate;
    }

    @Override
    public final String toString(){
        return "Task ':name' scheduled for :deadline".replace(":name", this.getName()).replace(":deadline", this.getDeadLineDate());
    }

}
