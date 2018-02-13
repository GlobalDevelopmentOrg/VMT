package vehicle.maintenance.tracker.api;

import vehicle.maintenance.tracker.api.exceptions.EntityDoesNotExistException;
import vehicle.maintenance.tracker.api.models.PartInfoModel;
import vehicle.maintenance.tracker.api.models.VehicleInfoModel;

import java.util.ArrayList;
import java.util.List;

public final class VMTApi {

    private static VehicleDAOSingleton vehicleDAOSingleton;
    private static PartDAOSingleton partDAOSingleton;
    private static TaskDAOSingleton taskDAOSingleton;

    public VMTApi(){
        this.vehicleDAOSingleton = VehicleDAOSingleton.getInstance();
        this.partDAOSingleton = PartDAOSingleton.getInstance();
        this.taskDAOSingleton = TaskDAOSingleton.getInstance();
    }

    public final VehicleInfoModel createNewVehicleInfoModel(VehicleEntity vehicleEntity){
        this.vehicleDAOSingleton.insert(vehicleEntity);
        VehicleEntity loaded = this.vehicleDAOSingleton.getLastEntry();
        // pass an empty array as a newly created VehicleInfoModel will have no parts currently
        return new VehicleInfoModel(loaded, new ArrayList<>());
    }

    public final PartInfoModel createNewPartInfoModel(PartEntity partEntity){
        this.partDAOSingleton.insert(partEntity);
        return new PartInfoModel(this.partDAOSingleton.getLastEntry());
    }

    public final VehicleInfoModel getVehicleInfoModel(int id){
        VehicleInfoModel vehicleInfoModel = new VehicleInfoModel(this.vehicleDAOSingleton.findById(id), this.getPartInfoModels(id));
        vehicleInfoModel.setTaskEntities(this.getTaskEntities(vehicleInfoModel.getVehicleEntity().getIdForTasks()));
        return vehicleInfoModel;
    }

    public final List<PartInfoModel> getPartInfoModels(int vehicleId){
        List<PartInfoModel> partInfoModels = new ArrayList<>();
        List<PartEntity> parts = this.partDAOSingleton.findByParentId(vehicleId);
        parts.forEach(part -> {
            PartInfoModel partInfoModel = new PartInfoModel(part);
            partInfoModel.setTaskEntities(getTaskEntities(part.getIdForTasks()));
            partInfoModels.add(partInfoModel);
        });
        return partInfoModels;
    }

    public final List<TaskEntity> getTaskEntities(String parentId){
        return this.taskDAOSingleton.findByParentId(parentId);
    }

    // we don't care about TaskEntities ID it will be linked to part,vehicle by another id instead
    public final void commit(TaskEntity taskEntity){
        if(taskEntity.getId() == 0){
            this.taskDAOSingleton.insert(taskEntity);
        }else{
            this.taskDAOSingleton.update(taskEntity);
        }
    }

    public final void commit(PartInfoModel partInfoModel) throws EntityDoesNotExistException{
        if(partInfoModel.getPartEntity().getId() != 0) {
            this.partDAOSingleton.update(partInfoModel.getPartEntity());
            partInfoModel.getTaskEntities().forEach(this::commit);
        }else{
            throw new EntityDoesNotExistException(partInfoModel.getPartEntity().toString());
        }
    }

    public final void commit(VehicleInfoModel vehicleInfoModel) throws EntityDoesNotExistException {
        if(vehicleInfoModel.getVehicleEntity().getId() != 0) {
            this.vehicleDAOSingleton.update(vehicleInfoModel.getVehicleEntity());
            vehicleInfoModel.getPartInfoModels().forEach(entity -> {
                try{
                    commit(entity);
                }catch(EntityDoesNotExistException e){
                    System.out.println("Some parts in vehicle " + vehicleInfoModel.getVehicleEntity().getId() + " are not in the database");
                    e.printStackTrace();
                }
            });
            vehicleInfoModel.getTaskEntities().forEach(this::commit);
        }else{
            throw new EntityDoesNotExistException(vehicleInfoModel.getVehicleEntity().toString());
        }
    }

    public final void deleteTask(String parentId){
        this.taskDAOSingleton.delete(parentId);
    }

    public final void clearAll(){
        this.vehicleDAOSingleton.truncate();
        this.partDAOSingleton.truncate();
        this.taskDAOSingleton.truncate();
    }


}
