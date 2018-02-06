package vehicle.maintenance.tracker.database.testing;

import org.h2.tools.DeleteDbFiles;
import vehicle.maintenance.tracker.database.H2DatabaseConnector;
import vehicle.maintenance.tracker.database.sql.SQLStatementFactory;
import vehicle.maintenance.tracker.database.sql.statements.SQLDataType;
import vehicle.maintenance.tracker.database.sql.statements.SQLStatement;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLHelperExample {

    public static void main(String[] args){
        H2DatabaseConnector connector = new H2DatabaseConnector("./test", "", "");

        String createTable = SQLStatementFactory.statement(SQLStatement.CREATE)
                .table()
                .ifNotExists()
                .branchAndParameterizeArguments("vehicles")
                .addArgument(
                        SQLStatementFactory
                                .dataType("id", SQLDataType.INT, 10)
                                .autoIncrement()
                                .unique()
                                .build()
                )
                .addArgument(
                        SQLStatementFactory
                                .dataType("registration", SQLDataType.VARCHAR, 10)
                                .notNull()
                                .build()
                )
                .build();

        String insert = SQLStatementFactory.statement(SQLStatement.INSERT)
                .into()
                .branchAndParameterizeArguments("vehicles")
                .addArgument("registration")
                .values()
                .addArgument("asd23423")
                .build();

        String select = SQLStatementFactory.statement(SQLStatement.SELECT)
                .columnsFrom("vehicles", "id", "registration")
                .where()
                .addArgument("id=1")
                .build();

        System.out.println("------------------------------------------------------------------------------");
        System.out.println("---------------------------Running-SQL-Commands--------------------------------");

        try{
            Connection connection = connector.openConnection();
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

        System.out.println("------------------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------------------");

        DeleteDbFiles.execute(".", "test", false);
    }

}
