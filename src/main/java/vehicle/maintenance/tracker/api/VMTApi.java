package vehicle.maintenance.tracker.api;

import vehicle.maintenance.tracker.api.storage.PartDAOSingleton;
import vehicle.maintenance.tracker.api.storage.TaskDAOSingleton;
import vehicle.maintenance.tracker.api.storage.VehicleDAOSingleton;
import vehicle.maintenance.tracker.api.entity.Entity;
import vehicle.maintenance.tracker.api.entity.PartEntity;
import vehicle.maintenance.tracker.api.entity.TaskEntity;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;
import vehicle.maintenance.tracker.api.exceptions.APIInitException;
import vehicle.maintenance.tracker.api.exceptions.DAOInitException;

import java.util.List;

/**
 * <code>VTMApi</code>
 * Is the up most level of the VMT framework that allows you to create,edit and delete
 * entities stored in the database.
 *
 * @author Daile Alimo
 * @since 0.2-SNAPSHOT
 */
@SuppressWarnings("unused")
public final class VMTApi {

    private static VehicleDAOSingleton vehicleDAOSingleton;
    private static PartDAOSingleton partDAOSingleton;
    private static TaskDAOSingleton taskDAOSingleton;

    public VMTApi() throws APIInitException {
        try {
            VMTApi.vehicleDAOSingleton = VehicleDAOSingleton.getInstance();
            VMTApi.partDAOSingleton = PartDAOSingleton.getInstance();
            VMTApi.taskDAOSingleton = TaskDAOSingleton.getInstance();
        } catch (DAOInitException e) {
            throw new APIInitException(e);
        }
    }

    // get vehicle
    public List<VehicleEntity> getAllVehicles(){
        return VMTApi.vehicleDAOSingleton.findAll();
    }

    public VehicleEntity getVehicle(String id){
        return VMTApi.vehicleDAOSingleton.findById(id);
    }

    public VMTApi deleteVehicle(VehicleEntity entity){
        VMTApi.vehicleDAOSingleton.delete(entity);
        return this;
    }

    // add vehicle
    public VMTApi commitVehicle(VehicleEntity entity) {
        if(!VMTApi.vehicleDAOSingleton.exists(entity)){
            VMTApi.vehicleDAOSingleton.insert(entity);
        }else{
            VMTApi.vehicleDAOSingleton.update(entity);
        }
        return this;
    }

    // get parts
    public List<PartEntity> getAllParts(){
        return VMTApi.partDAOSingleton.findAll();
    }

    public List<PartEntity> getParts(String vehicleId){
        return VMTApi.partDAOSingleton.findByParentId(vehicleId);
    }

    public PartEntity getPart(String id){
        return VMTApi.partDAOSingleton.findById(id);
    }

    public VMTApi deletePart(PartEntity entity){
        VMTApi.partDAOSingleton.delete(entity);
        return this;
    }

    // add part
    public VMTApi commitPart(PartEntity entity) {
        if(!VMTApi.partDAOSingleton.exists(entity)){
            VMTApi.partDAOSingleton.insert(entity);
        }else{
            VMTApi.partDAOSingleton.update(entity);
        }
        return this;
    }

    // get task for parentId(vehicle or part)
    public List<TaskEntity> getAllTasks(){
        return VMTApi.taskDAOSingleton.findAll();
    }

    public List<TaskEntity> getTasks(Entity entity){
        return VMTApi.taskDAOSingleton.findByParentId(entity.getId());
    }

    public List<TaskEntity> getTasks(String parentId){
        return VMTApi.taskDAOSingleton.findByParentId(parentId);
    }

    public TaskEntity getTask(String id){
        return VMTApi.taskDAOSingleton.findById(id);
    }

    public VMTApi deleteTask(TaskEntity entity){
        VMTApi.taskDAOSingleton.delete(entity);
        return this;
    }

    // add task
    public VMTApi commitTask(TaskEntity entity) {
        if(!VMTApi.taskDAOSingleton.exists(entity)){
            VMTApi.taskDAOSingleton.insert(entity);
        }else{
            VMTApi.taskDAOSingleton.update(entity);
        }
        return this;
    }

    public final VMTApi clearAll() {
        VMTApi.vehicleDAOSingleton.truncate();
        VMTApi.partDAOSingleton.truncate();
        VMTApi.taskDAOSingleton.truncate();
        return this;
    }


}
