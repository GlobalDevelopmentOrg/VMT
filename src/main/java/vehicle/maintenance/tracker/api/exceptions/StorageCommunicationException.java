package vehicle.maintenance.tracker.api.exceptions;

@SuppressWarnings("unused")
public class StorageCommunicationException extends RuntimeException {

    public StorageCommunicationException() {}

    public StorageCommunicationException(String message) {
        super(message);
    }

    public StorageCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageCommunicationException(Throwable cause) {
        super(cause);
    }

    public StorageCommunicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
