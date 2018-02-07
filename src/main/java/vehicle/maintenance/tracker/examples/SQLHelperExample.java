package vehicle.maintenance.tracker.examples;

import org.h2.tools.DeleteDbFiles;
import vehicle.maintenance.tracker.api.database.H2DatabaseConnector;
import vehicle.maintenance.tracker.api.database.sql.SQLStatementFactory;
import vehicle.maintenance.tracker.api.database.sql.statements.SQLDataType;
import vehicle.maintenance.tracker.api.database.sql.statements.SQLStatement;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <code>SQLHelperExample</code>
 * Example of the usage of the <code>SQLStatementFactory</code>
 *
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public class SQLHelperExample {

    public static void main(String[] args){
        H2DatabaseConnector connector = new H2DatabaseConnector("./test", "", "");

        final String createTable = SQLStatementFactory.statement(SQLStatement.CREATE)
                .table()
                .ifNotExists()
                .branchAndParameterizeArguments("vehicles")
                .addArguments(
                        SQLStatementFactory
                                .dataType("id", SQLDataType.INT, 10)
                                .autoIncrement()
                                .unique()
                                .build(),
                        SQLStatementFactory
                                .dataType("registration", SQLDataType.VARCHAR, 10)
                                .notNull()
                                .build(),
                        SQLStatementFactory
                                .dataType("mileage", SQLDataType.INT, 5)
                                .notNull()
                                .build()
                )
                .build();

        final String insert = SQLStatementFactory.statement(SQLStatement.INSERT)
                .into()
                .branchAndParameterizeArguments("vehicles")
                .addArguments("registration", "mileage")
                .values()
                .addArguments("abc", "1043")
                .build();

        final String select = SQLStatementFactory.statement(SQLStatement.SELECT)
                .columnsFrom("vehicles", "id", "registration", "mileage")
                .where()
                .addArgument("id=1")
                .build();

        System.out.println("------------------------------------------------------------------------------");
        System.out.println("---------------------------Running-SQL-Commands--------------------------------");


        connector.openSession(connection -> {
            try{
                System.out.println(createTable);
                System.out.println("[" + connection.createStatement().executeUpdate(createTable) + "]");
                System.out.println(insert);
                System.out.println("[" + connection.createStatement().executeUpdate(insert) + "]");
                System.out.println(select);
                System.out.println("[" + connection.createStatement().executeQuery(select).first() + "]");
                connection.commit();
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });


        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");

        // remove test database from root directory
        DeleteDbFiles.execute(".", "test", false);
    }

}
