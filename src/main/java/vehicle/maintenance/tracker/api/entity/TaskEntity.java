package vehicle.maintenance.tracker.api.entity;

/*
 *    We may be mixing concerns here by managing and validating the dueDate string
 *    passed into this class.
 *    Maybe a better approach would be to have a separate class to do this when this
 *    class is to be used in the api.
 *
 */
/**
 *
 * @author Daile Alimo
 * @since 0.2-SNAPSHOT
 */
public final class TaskEntity extends Entity {

    private String parentId;
    private String comment;
    private String dueDate;

    public TaskEntity(String parentId, String name, String comment, String dueDate) {
        super(name);
        this.parentId = parentId;
        this.comment = comment;
        this.dueDate = dueDate;
    }

    public TaskEntity(String id, String parentId, String name, String comment, String dueDate) {
        super(id, name);
        this.parentId = parentId;
        this.comment = comment;
        this.dueDate = dueDate;
    }

    public String getParentId(){
        return this.parentId;
    }

    public void setParentId(String parentId){
        this.parentId = parentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
