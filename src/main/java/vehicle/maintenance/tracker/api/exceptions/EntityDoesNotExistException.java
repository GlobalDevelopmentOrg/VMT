package vehicle.maintenance.tracker.api.exceptions;

/**
 * This exception should be thrown if an entity that does not exist
 * is committed to the database.
 */
public class EntityDoesNotExistException extends Exception {

    private static final String MESSAGE = "Entity (:entity) doesn't exist in the database";

    public EntityDoesNotExistException(String entity){
        super(EntityDoesNotExistException.MESSAGE.replace(":entity", entity));
    }

}
