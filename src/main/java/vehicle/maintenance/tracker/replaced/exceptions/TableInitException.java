package vehicle.maintenance.tracker.replaced.exceptions;

@SuppressWarnings("unused")
public class TableInitException extends Exception {

    public TableInitException() {

    }

    public TableInitException(String message) {
        super(message);
    }

    public TableInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableInitException(Throwable cause) {
        super(cause);
    }

    public TableInitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
