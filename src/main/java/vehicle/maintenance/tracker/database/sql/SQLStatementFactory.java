package vehicle.maintenance.tracker.database.sql;

import vehicle.maintenance.tracker.database.sql.statements.SQLCommand;
import vehicle.maintenance.tracker.database.sql.statements.SQLCrudCommand;
import vehicle.maintenance.tracker.database.sql.statements.SQLStatement;

public class SQLStatementFactory {

    public static SQLCommand statement(SQLStatement statement){
        switch (statement){

            case SELECT:
            case UPDATE:
            case DELETE:
            case INSERT:
            case CREATE:
                return new SQLCrudCommand(statement.name());

            default:
                System.err.println("No implementation for " + statement.name());
                break;

        }
        return null;
    }

}
