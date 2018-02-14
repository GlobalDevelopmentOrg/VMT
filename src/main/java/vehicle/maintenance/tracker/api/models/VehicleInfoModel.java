package vehicle.maintenance.tracker.api.models;

import vehicle.maintenance.tracker.api.entity.TaskEntity;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;

import java.util.ArrayList;
import java.util.List;

public class VehicleInfoModel {

    private VehicleEntity vehicleEntity;
    private List<PartInfoModel> partInfoModels;
    private List<TaskEntity> taskEntities;

    public VehicleInfoModel(VehicleEntity vehicleEntity, List<PartInfoModel> partInfoModels){
        this.vehicleEntity = vehicleEntity;
        this.partInfoModels = partInfoModels;
        this.taskEntities = new ArrayList<>();
    }

    public VehicleEntity getVehicleEntity() {
        return this.vehicleEntity;
    }

    public List<PartInfoModel> getPartInfoModels(){
        return this.partInfoModels;
    }

    public List<TaskEntity> getTaskEntities(){
        return this.taskEntities;
    }

    public void setTaskEntities(List<TaskEntity> taskEntities) {
        this.taskEntities = taskEntities;
    }

    public void addTaskEntity(String name, String comment, String deadlineDate){
        this.taskEntities.add(new TaskEntity(this.vehicleEntity.getIdForTasks(), name, comment, deadlineDate));
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.getVehicleEntity().toString());
        this.getTaskEntities().forEach(task -> {
            builder.append("\n--> " + task);
            builder.append("\n" + task.getComment() + "\n");
        });
        this.getPartInfoModels().forEach(part -> {
            builder.append("\n" + part);
        });
        return builder.toString();
    }

}
