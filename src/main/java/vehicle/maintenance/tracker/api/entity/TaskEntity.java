package vehicle.maintenance.tracker.api.entity;

/**
 * @author Daile Alimo
 * @since 0.2-SNAPSHOT
 */
public final class TaskEntity extends Entity {

    private String parentId;
    private String dueDate;

    public TaskEntity(){
        super();
    }

    public TaskEntity(String parentId, String name, String notes, String dueDate) {
        super(name, notes);
        this.parentId = parentId;
        this.dueDate = dueDate;
    }

    public TaskEntity(String id, String parentId, String name, String notes, String dueDate) {
        super(id, name, notes);
        this.parentId = parentId;
        this.dueDate = dueDate;
    }

    public String getParentId(){
        return this.parentId;
    }

    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public final String toString(){
        return "Task ':name' scheduled for :dueDate"
                .replace(":name", super.getName())
                .replace(":dueDate", this.getDueDate());
    }

}
