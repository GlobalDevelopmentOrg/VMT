package vehicle.maintenance.tracker.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class H2NoSQL implements Statement {

    public static final String DATABASE = "database";
    public static final String TABLE = "table";

    private static final String SELECT_ALL = "SELECT :column FROM :table WHERE :condition";

    public ResultSet select(String column, String table, String condition){
        try{
            return this.executeQuery(SELECT_ALL
                    .replace(":column", column)
                    .replace(":table", table)
                    .replace(":condition", condition));
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet select(String column, String table){
        try{
            return this.executeQuery(SELECT_ALL
                    .replace(":column", column)
                    .replace(":table", table)
                    .replace(":condition", "1=1")); // always true condition
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
