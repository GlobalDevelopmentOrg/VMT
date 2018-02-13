package vehicle.maintenance.tracker.api;

public final class TaskEntity {

    private int id;
    private String parentId;
    private String comment;
    private String deadLineDate;

    public TaskEntity(int id, String parentId, String comment, String deadLineDate){
        this.id = id;
        this.parentId = parentId;
        this.comment = comment;
        this.deadLineDate = deadLineDate;
    }

    public TaskEntity(String parentId, String comment, String deadLineDate){
        this.parentId = parentId;
        this.comment = comment;
        this.deadLineDate = deadLineDate;
    }

    public final int getId() {
        return this.id;
    }

    public final String getParentId() {
        return this.parentId;
    }

    public final String getComment() {
        return this.comment;
    }

    public final void setComment(String comment){
        this.comment = comment;
    }

    public final String getDeadlineDate() {
        return this.deadLineDate;
    }

    public final void setDeadLineDate(String deadLineDate){
        this.deadLineDate = deadLineDate;
    }

    @Override
    public final String toString(){
        return this.getId() + " " + this.getParentId() + " " + this.getComment() + " " + this.getDeadlineDate() + "\n";
    }

}
