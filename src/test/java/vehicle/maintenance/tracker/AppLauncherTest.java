package vehicle.maintenance.tracker;

import org.junit.*;
import vehicle.maintenance.tracker.database.H2DatabaseConnector;
import vehicle.maintenance.tracker.database.sql.SQLStatementFactory;
import vehicle.maintenance.tracker.database.sql.statements.SQLCrudCommand;
import vehicle.maintenance.tracker.database.sql.statements.SQLStatement;

import java.sql.SQLException;

/**
 * Unit tests ensure we run tests before making a pull request.
 * All tests should run.
 * <p>
 * Write tests for new classes you create,
 * this is ensure other developers don't break your code.
 */
public class AppLauncherTest {

    private static final String DATABASE_URL = "./test";

    @Test
    public void testDatabase(){
        H2DatabaseConnector connector = new H2DatabaseConnector("./test", "", "");

        String createTable = SQLStatementFactory.statement(SQLStatement.CREATE)
                .table()
                .ifNotExists()
                .branchAndParameterizeArguments("vehicles")
                .addArgument("id INT(10) AUTO_INCREMENT UNIQUE")
                .addArgument("name VARCHAR(255) NOT NULL")
                .build();

        String insert = SQLStatementFactory.statement(SQLStatement.INSERT)
                .into()
                .table("vehicles")
                .branch("(name)")
                .values()
                .addArgument("\"testing\"")
                .build();

        System.out.println("------------------------------------------------------------------------------");
        System.out.println("---------------------------Running-SQL-Commands--------------------------------");

        try{
            System.out.println(createTable + " [" + connector.openConnection().createStatement().executeUpdate(createTable) + "]");
            System.out.println(insert + " [" + connector.openConnection().createStatement().executeUpdate(insert) + "]");
        }catch(SQLException e){
            e.printStackTrace();
        }

        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");
    }

}
