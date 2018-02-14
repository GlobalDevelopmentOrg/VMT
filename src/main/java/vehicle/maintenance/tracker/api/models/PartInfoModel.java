package vehicle.maintenance.tracker.api.models;

import vehicle.maintenance.tracker.api.entity.PartEntity;
import vehicle.maintenance.tracker.api.entity.TaskEntity;

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

    public void addTaskEntity(String name, String comment, String deadlineDate){
        this.taskEntities.add(new TaskEntity(this.partEntity.getIdForTasks(), name, comment, deadlineDate));
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("---> " + this.partEntity.toString());
        this.getTaskEntities().forEach(task -> {
            builder.append("\n------> " + task);
            builder.append("\n" + task.getComment() + "\n");
        });
        return builder.toString();
    }


}
