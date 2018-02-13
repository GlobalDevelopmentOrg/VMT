package vehicle.maintenance.tracker.api.models;

import vehicle.maintenance.tracker.api.TaskEntity;
import vehicle.maintenance.tracker.api.VehicleEntity;

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

    public void addTaskEntity(String comment, String deadlineDate){
        this.taskEntities.add(new TaskEntity(this.vehicleEntity.getIdForTasks(), comment, deadlineDate));
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.getVehicleEntity().toString());
        builder.append("----Tasks for vehicle----\n");
        this.getTaskEntities().forEach(builder::append);
        builder.append("----Parts for vehicle----\n");
        this.getPartInfoModels().forEach(builder::append);
        builder.append("----END OF VEHICLE INFO MODEL----\n\n\n");
        return builder.toString();
    }

}
