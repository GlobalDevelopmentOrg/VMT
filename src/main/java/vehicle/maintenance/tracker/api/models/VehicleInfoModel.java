package vehicle.maintenance.tracker.api.models;

import vehicle.maintenance.tracker.api.entity.PartEntity;
import vehicle.maintenance.tracker.api.entity.TaskEntity;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;
import vehicle.maintenance.tracker.api.exceptions.InvalidDateException;

import java.util.ArrayList;
import java.util.List;

public class VehicleInfoModel {

    private VehicleEntity vehicleEntity;
    private List<PartInfoModel> partInfoModels;
    private List<TaskEntity> taskEntities;

    public VehicleInfoModel(VehicleEntity vehicleEntity, List<PartInfoModel> partInfoModels, List<TaskEntity> taskEntities){
        this.vehicleEntity = vehicleEntity;
        this.partInfoModels = partInfoModels;
        this.taskEntities = taskEntities;
    }

    public VehicleEntity getVehicleEntity() {
        return this.vehicleEntity;
    }

    public List<PartInfoModel> getPartInfoModels(){
        return this.partInfoModels;
    }

    public void addPartEntity(String name, String installationDate){
        this.partInfoModels.add(new PartInfoModel(new PartEntity(this.getVehicleEntity().getId(), name, installationDate), new ArrayList<>()));
    }

    public List<TaskEntity> getTaskEntities(){
        return this.taskEntities;
    }

    public void addTaskEntity(String name, String comment, String dueDate) throws InvalidDateException {
        this.taskEntities.add(new TaskEntity(this.vehicleEntity.getId(), name, comment, dueDate));
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.getVehicleEntity().toString() + "{" + this.getVehicleEntity().getId() + "]");
        this.getTaskEntities().forEach(task -> {
            builder.append("\n--> " + task + " [" + task.getId() + "]" + " [" + task.getParentId() + "]");
        });
        this.getPartInfoModels().forEach(part -> {
            builder.append("\n----> " + part);
        });
        return builder.toString();
    }

}
