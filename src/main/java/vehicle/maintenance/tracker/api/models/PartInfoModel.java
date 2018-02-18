package vehicle.maintenance.tracker.api.models;

import javafx.concurrent.Task;
import vehicle.maintenance.tracker.api.entity.PartEntity;
import vehicle.maintenance.tracker.api.entity.TaskEntity;
import vehicle.maintenance.tracker.api.exceptions.InvalidDateException;

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

    public PartInfoModel(PartEntity partEntity, List<TaskEntity> taskEntities){
        this.partEntity = partEntity;
        this.taskEntities = taskEntities;
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

    public void addTaskEntity(String name, String comment, String dueDate) throws InvalidDateException {
        this.taskEntities.add(new TaskEntity(this.partEntity.getId(), name, comment, dueDate));
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.partEntity.toString() + " [" + this.partEntity.getId() + "] " + " [" + this.partEntity.getVehicleId() + "]");
        this.getTaskEntities().forEach(task -> {
            builder.append("\n------> " + task + " [" + task.getId() + "]" + " [" + task.getParentId() + "]");
        });
        return builder.toString();
    }


}
