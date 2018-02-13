package vehicle.maintenance.tracker.api;

/**
 * <code>SessionResult</code> holds a castable result object that can
 * be passed back from a <code>H2DatabaseConnector</code> session.
 *
 * @param <T> return type for result
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public final class SessionResult <T> {

    private final T result;

    protected SessionResult(T result){
        this.result = result;
    }

    protected final T getResult(){
        return this.result;
    }

}
