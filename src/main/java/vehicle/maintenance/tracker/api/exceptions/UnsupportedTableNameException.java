package vehicle.maintenance.tracker.api.exceptions;

public final class UnsupportedTableNameException extends Exception {

    private static final String errorMessage = "Input table :tableName can only contain alphabetical characters a-z A-Z";

    public UnsupportedTableNameException(String tableName) {
        super(errorMessage.replace(":tableName", tableName));
    }

}