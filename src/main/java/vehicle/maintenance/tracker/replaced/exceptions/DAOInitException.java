package vehicle.maintenance.tracker.replaced.exceptions;

@SuppressWarnings("unused")
public class DAOInitException extends Exception {

    public DAOInitException() {
    }

    public DAOInitException(Exception throwable){
        super(throwable);
    }

    public DAOInitException(String message){
        super(message);
    }

    public DAOInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOInitException(Throwable cause) {
        super(cause);
    }

    public DAOInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
