package vehicle.maintenance.tracker.api.database.sql;

import vehicle.maintenance.tracker.api.database.sql.statements.SQLDataType;
import vehicle.maintenance.tracker.api.database.sql.statements.SQLDataTypeBuilder;
import vehicle.maintenance.tracker.api.database.sql.statements.SQLStatementBuilder;
import vehicle.maintenance.tracker.api.database.sql.statements.SQLStatement;

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
