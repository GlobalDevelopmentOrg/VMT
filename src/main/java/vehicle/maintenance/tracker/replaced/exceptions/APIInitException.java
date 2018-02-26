package vehicle.maintenance.tracker.replaced.exceptions;

@SuppressWarnings("unused")
public class APIInitException extends RuntimeException {

    public APIInitException() {
    }

    public APIInitException(Exception throwable){
        super(throwable);
    }

    public APIInitException(String message){
        super(message);
    }

    public APIInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public APIInitException(Throwable cause) {
        super(cause);
    }

    public APIInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
