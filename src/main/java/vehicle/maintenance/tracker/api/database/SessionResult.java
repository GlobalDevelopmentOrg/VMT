package vehicle.maintenance.tracker.api.database;

/**
 * <code>SessionResult</code> holds a castable result object that can
 * be passed back from a <code>H2DatabaseConnector</code> session.
 *
 * @param <T> return type for result
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public class SessionResult <T> {

    private T result;

    public SessionResult(T result){
        this.result = result;
    }

    public T getResult(){
        return this.result;
    }

}
