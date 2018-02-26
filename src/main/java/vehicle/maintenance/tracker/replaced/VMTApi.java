package vehicle.maintenance.tracker.replaced;

import vehicle.maintenance.tracker.replaced.storage.PartDAOSingleton;
import vehicle.maintenance.tracker.replaced.storage.TaskDAOSingleton;
import vehicle.maintenance.tracker.replaced.storage.VehicleDAOSingleton;
import vehicle.maintenance.tracker.api.entity.Entity;
import vehicle.maintenance.tracker.api.entity.PartEntity;
import vehicle.maintenance.tracker.api.entity.TaskEntity;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;
import vehicle.maintenance.tracker.replaced.exceptions.APIInitException;
import vehicle.maintenance.tracker.replaced.exceptions.DAOInitException;

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

    private static VehicleDAOSingleton vehicleDAO;
    private static PartDAOSingleton partDAOS;
    private static TaskDAOSingleton taskDAO;

    public VMTApi() throws APIInitException {
        try {
            VMTApi.vehicleDAO = VehicleDAOSingleton.getInstance();
            VMTApi.partDAOS = PartDAOSingleton.getInstance();
            VMTApi.taskDAO = TaskDAOSingleton.getInstance();
        } catch (DAOInitException e) {
            throw new APIInitException(e);
        }
    }

    // get vehicle
    public List<VehicleEntity> getAllVehicles(){
        return VMTApi.vehicleDAO.findAll();
    }

    public VehicleEntity getVehicle(String id){
        return VMTApi.vehicleDAO.findById(id);
    }

    public VMTApi deleteVehicle(VehicleEntity entity){
        VMTApi.vehicleDAO.delete(entity);
        return this;
    }

    // add vehicle
    public VMTApi commitVehicle(VehicleEntity entity) {
        if(!VMTApi.vehicleDAO.exists(entity)){
            VMTApi.vehicleDAO.insert(entity);
        }else{
            VMTApi.vehicleDAO.update(entity);
        }
        return this;
    }

    // get parts
    public List<PartEntity> getAllParts(){
        return VMTApi.partDAOS.findAll();
    }

    public List<PartEntity> getParts(String vehicleId){
        return VMTApi.partDAOS.findByParentId(vehicleId);
    }

    public PartEntity getPart(String id){
        return VMTApi.partDAOS.findById(id);
    }

    public VMTApi deletePart(PartEntity entity){
        VMTApi.partDAOS.delete(entity);
        return this;
    }

    // add part
    public VMTApi commitPart(PartEntity entity) {
        if(!VMTApi.partDAOS.exists(entity)){
            VMTApi.partDAOS.insert(entity);
        }else{
            VMTApi.partDAOS.update(entity);
        }
        return this;
    }

    // get task for parentId(vehicle or part)
    public List<TaskEntity> getAllTasks(){
        return VMTApi.taskDAO.findAll();
    }

    public List<TaskEntity> getTasks(Entity entity){
        return VMTApi.taskDAO.findByParentId(entity.getId());
    }

    public List<TaskEntity> getTasks(String parentId){
        return VMTApi.taskDAO.findByParentId(parentId);
    }

    public TaskEntity getTask(String id){
        return VMTApi.taskDAO.findById(id);
    }

    public VMTApi deleteTask(TaskEntity entity){
        VMTApi.taskDAO.delete(entity);
        return this;
    }

    // add task
    public VMTApi commitTask(TaskEntity entity) {
        if(!VMTApi.taskDAO.exists(entity)){
            VMTApi.taskDAO.insert(entity);
        }else{
            VMTApi.taskDAO.update(entity);
        }
        return this;
    }

    public final VMTApi clearAll() {
        VMTApi.vehicleDAO.truncate();
        VMTApi.partDAOS.truncate();
        VMTApi.taskDAO.truncate();
        return this;
    }

    public int countVehicles(){
        return VMTApi.vehicleDAO.count();
    }

    public int countParts(){
        return VMTApi.partDAOS.count();
    }

    public int countTasks(){
        return VMTApi.taskDAO.count();
    }

}
