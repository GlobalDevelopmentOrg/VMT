package vehicle.maintenance.tracker.api.exceptions;

public class EntityDoesNotExistException extends Exception {

    private static final String MESSAGE = "Entity (:entity) doesn't exist in the database";

    public EntityDoesNotExistException(String entity){
        super(EntityDoesNotExistException.MESSAGE.replace(":entity", entity));
    }

}
