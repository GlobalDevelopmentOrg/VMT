package vehicle.maintenance.tracker.api.models;

import vehicle.maintenance.tracker.api.PartEntity;
import vehicle.maintenance.tracker.api.TaskEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>PartInfoModel</code>
 * should only be created from the API
 *
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public class PartInfoModel {

    private PartEntity partEntity;
    private List<TaskEntity> taskEntities;

    public PartInfoModel(PartEntity partEntity){
        this.partEntity = partEntity;
        this.taskEntities = new ArrayList<>();
    }

    public PartEntity getPartEntity() {
        return this.partEntity;
    }

    public List<TaskEntity> getTaskEntities(){
        return this.taskEntities;
    }

    public void setTaskEntities(List<TaskEntity> taskEntities) {
        this.taskEntities = taskEntities;
    }

    public void addTaskEntity(String comment, String deadlineDate){
        this.taskEntities.add(new TaskEntity(this.partEntity.getIdForTasks(), comment, deadlineDate));
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.partEntity.toString());
        builder.append("----Tasks for part----\n");
        this.getTaskEntities().forEach(builder::append);
        builder.append("----END OF PART INFO MODEL----\n");
        return builder.toString();
    }


}
