package vehicle.maintenance.tracker.api.database.sql;

/**
 * <code>SQLStatementFactory</code>
 * A factory for building basic SQLStatement and SQLDataType Strings.
 *
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public class SQLStatementFactory {

    public static SQLStatementBuilder statement(SQLStatement statement){
        switch (statement){

            case SELECT:
            case UPDATE:
            case DELETE:
            case INSERT:
            case CREATE:
            case TRUNCATE:
                return new SQLStatementBuilder(statement.name());

            default:
                System.err.println("No implementation for " + statement.name());
                break;

        }
        return null;
    }

    public static SQLDataTypeBuilder dataType(String name, SQLDataType dataType, int size){
        switch (dataType){

            case INT:
            case VARCHAR:
                return new SQLDataTypeBuilder(name, dataType.name(), size);

            default:
                System.err.println("No implementation for " + dataType.name());
                break;

        }
        return null;
    }

}
