package vehicle.maintenance.tracker.api;

import vehicle.maintenance.tracker.api.dao.PartDAOSingleton;
import vehicle.maintenance.tracker.api.dao.TaskDAOSingleton;
import vehicle.maintenance.tracker.api.dao.VehicleDAOSingleton;
import vehicle.maintenance.tracker.api.entity.PartEntity;
import vehicle.maintenance.tracker.api.entity.TaskEntity;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;
import vehicle.maintenance.tracker.api.exceptions.EntityDoesNotExistException;
import vehicle.maintenance.tracker.api.models.PartInfoModel;
import vehicle.maintenance.tracker.api.models.VehicleInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>VTMApi</code>
 * Is the up most level of the VMT framework that allows you to create,edit and delete
 * entities stored in the database.
 *
 * @author Daile Alimo
 * @since 0.2-SNAPSHOT
 */
public final class VMTApi {

    private static VehicleDAOSingleton vehicleDAOSingleton;
    private static PartDAOSingleton partDAOSingleton;
    private static TaskDAOSingleton taskDAOSingleton;

    public VMTApi(){
        VMTApi.vehicleDAOSingleton = VehicleDAOSingleton.getInstance();
        VMTApi.partDAOSingleton = PartDAOSingleton.getInstance();
        VMTApi.taskDAOSingleton = TaskDAOSingleton.getInstance();
    }

    /**
     * Creates a <code>VehicleInfoModel</code> in the database,
     * returns one that can be updated and committed to the database.
     *
     * @param vehicleEntity a new VehicleEntity or template to create one
     * @return a VehicleInfoModel with all information collected from the database
     */
    public final VehicleInfoModel createNewVehicleInfoModel(VehicleEntity vehicleEntity){
        VMTApi.vehicleDAOSingleton.insert(vehicleEntity);
        VehicleEntity loaded = VMTApi.vehicleDAOSingleton.getLastEntry();
        return new VehicleInfoModel(loaded, new ArrayList<>());
    }

    /**
     * Creates a <code>PartInfoModel</code> in the database,
     * returns one that can be updated and committed to the database.
     *
     * @param partEntity a new PartEntity or template to create one
     * @return a PartInfoModel with all information collected from the database
     */
    public final PartInfoModel createNewPartInfoModel(PartEntity partEntity){
        VMTApi.partDAOSingleton.insert(partEntity);
        return new PartInfoModel(VMTApi.partDAOSingleton.getLastEntry());
    }

    /**
     * Gets a <code>VehicleInfoModel</code> with the given id
     * from the database if one exists. Null if not.
     *
     * @param id the id for the VehicleEntity to create the VehicleInfoModel from
     * @return a VehicleInfoModel or Null if a VehicleEntity
     * with the given id does not exist.
     */
    public final VehicleInfoModel getVehicleInfoModel(int id){
        VehicleEntity loadedEntity = VMTApi.vehicleDAOSingleton.findById(id);
        VehicleInfoModel vehicleInfoModel = null;
        // we have to make sure loadedEntity exists and is not null
        if(loadedEntity != null){
            vehicleInfoModel = new VehicleInfoModel(loadedEntity, this.getPartInfoModels(id));
            vehicleInfoModel.setTaskEntities(this.getTaskEntities(vehicleInfoModel.getVehicleEntity().getIdForTasks()));
        }
        return vehicleInfoModel;
    }

    /**
     * Gets a <code>List</code> of <code>PartInfoModel</code>(s) with the given vehicleId
     * from the database if one exists. An empty list if not.
     *
     * @param vehicleId the id for the VehicleEntity the part to find belong too.
     * @return a List of PartInfoModel(s) or an empty List if PartInfoModel(s)
     * with the given vehicleId do not exist.
     */
    public final List<PartInfoModel> getPartInfoModels(int vehicleId){
        List<PartInfoModel> partInfoModels = new ArrayList<>();
        List<PartEntity> parts = VMTApi.partDAOSingleton.findByParentId(vehicleId);
        parts.forEach(part -> {
            PartInfoModel partInfoModel = new PartInfoModel(part);
            partInfoModel.setTaskEntities(getTaskEntities(part.getIdForTasks()));
            partInfoModels.add(partInfoModel);
        });
        return partInfoModels;
    }

    /**
     * Gets a <code>List</code> of <code>TaskEntity</code> for the given parentId
     * @param parentId id of the parent the task belongs too
     * @return a list of TaskEntity or an empty List
     */
    public final List<TaskEntity> getTaskEntities(String parentId){
        return VMTApi.taskDAOSingleton.findByParentId(parentId);
    }

    /**
     * Commit a <code>TaskEntity</code> to the database.
     * Will create a new <code>TaskEntity</code> in the database if this
     * taskEntity was not previously loaded from the database or
     * will update the entity it was loaded from.
     *
     * @param taskEntity a TaskEntity with a valid parentId to create or update
     */
    public final void commit(TaskEntity taskEntity){
        if(taskEntity.hasIdentity()){
            VMTApi.taskDAOSingleton.update(taskEntity);
        }else{
            VMTApi.taskDAOSingleton.insert(taskEntity);
        }
    }

    /**
     * Commit a <code>PartInfoModel</code> to the database
     * will update the entity in the database is they exist
     *
     * A PartInfoModel must have a loaded PartEntity if we wish to commit
     * changes to the database.
     *
     * @param partInfoModel PartInfoModel for a PartEntity in the database
     * @throws EntityDoesNotExistException if the given PartInfoModel does not have a database loaded PartEntity
     */
    public final void commit(PartInfoModel partInfoModel) throws EntityDoesNotExistException{
        if(partInfoModel.getPartEntity().hasIdentity()) {
            VMTApi.partDAOSingleton.update(partInfoModel.getPartEntity());
            partInfoModel.getTaskEntities().forEach(this::commit);
        }else{
            throw new EntityDoesNotExistException(partInfoModel.getPartEntity().toString());
        }
    }

    /**
     * Commit a <code>VehicleInfoModel</code> to the database
     * will update the entities if they exist
     *
     * A VehicleInfoModel must have a loaded VehicleEntity if we wish to commit
     * changes to the database.
     *
     * @param vehicleInfoModel a VehicleInfoModel for a VehicleEntity in the database
     * @throws EntityDoesNotExistException if the given VehicleInfoModel does not have a database loaded VehicleEntity
     */
    public final void commit(VehicleInfoModel vehicleInfoModel) throws EntityDoesNotExistException {
        if(vehicleInfoModel.getVehicleEntity().hasIdentity()) {
            VMTApi.vehicleDAOSingleton.update(vehicleInfoModel.getVehicleEntity());
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

    public final void deleteTasksFor(String parentId){
        VMTApi.taskDAOSingleton.delete(parentId);
    }

    public final void clearAll(){
        VMTApi.vehicleDAOSingleton.truncate();
        VMTApi.partDAOSingleton.truncate();
        VMTApi.taskDAOSingleton.truncate();
    }


}
