package vehicle.maintainence.tracker.dbh;

import java.sql.*;

/**
 * Database connector class for the h2 Embedded dbh.
 * This class is used to create a connection to a h2 embedded
 * you can use the given class methods to do CRUD operations.
 *
 * Be sure to close connection when finished.
 *
 * @Author Daile Alimo
 * @Since 0.1-SNAPSHOT
 */
public class H2DatabaseConnector {

    private static final String DB_DRIVER = "org.h2.Driver";
    private final Connection connection;

    /*
     " H2DatabaseConnector constructor is private
     " you must use the static method 'using' to create an instance of this class.
     */
    private H2DatabaseConnector(Connection connection) {
        this.connection = connection;
        try{
            this.connection.setAutoCommit(true);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Create an instance of H2DatabaseConnector using
     * a connection with the given arguments.
     *
     * @param database name of the dbh
     * @param user authorized user for the dbh
     * @param password password for dbh
     *
     * @return an instance of <code>H2DatabaseConnector</code> with a
     *         newly created (open) connection to the dbh.
     */
    public static final H2DatabaseConnector using(String database, String user, String password) {
        try {
            Class.forName(H2DatabaseConnector.DB_DRIVER);
            return new H2DatabaseConnector(DriverManager.getConnection("jdbc:h2:./dbh/" + database, user, password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Executes the given SQL statement, which returns a single
     * <code>ResultSet</code> object.
     *
     * @param sql an SQL statement to be sent to the dbh, typically a
     *        static SQL <code>SELECT</code> statement
     * @return a <code>ResultSet</code> object that contains the data produced
     *         by the given query; never <code>null</code>
     */
    public ResultSet executeQuery(String sql){
        try{
            Statement statement = this.connection.createStatement();
            return statement.executeQuery(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Executes the given SQL statement, which may be an <code>INSERT</code>,
     * <code>UPDATE</code>, or <code>DELETE</code> statement or an
     * SQL statement that returns nothing, such as an SQL DDL statement.
     *
     * @param sql an SQL Data Manipulation Language (DML) statement, such as <code>INSERT</code>, <code>UPDATE</code> or
     * <code>DELETE</code>; or an SQL statement that returns nothing,
     * such as a DDL statement.
     * @return (1) the row count for SQL Data Manipulation Language (DML) statements
     *         (2) 0 for SQL statements that return nothing.
     *         (3) -1 if an error occurred
     */
    public int executeUpdate(String sql){
        try{
            Statement statement = this.connection.createStatement();
            return statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public boolean isOpen(){
        try{
            return !this.connection.isClosed();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void close(){
        try{
            this.connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}