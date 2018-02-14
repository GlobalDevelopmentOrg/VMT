package vehicle.maintenance.tracker.api.dao;

import java.sql.*;

/**
 * <code>H2DatabaseConnector</code>
 * A class to create connections to a H2 Embedded database
 *
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public final class H2DatabaseConnector {

    private String url;
    private String user;
    private String password;

    public static final String DRIVER = "org.h2.Driver";

    protected H2DatabaseConnector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        try {
            Class.forName(H2DatabaseConnector.DRIVER);
        } catch (Exception e) {
            System.err.println("unable to instantiate H2DatabaseConnector\n" + H2DatabaseConnector.DRIVER + " not found on class path!\n" + e.getMessage());
            System.exit(0);
        }
    }

    // by doing this we can make sure the connection gets closed, when we have finished with it.
    protected final SessionResult openSession(DatabaseConnectorSession session) {
        SessionResult result = null;
        try(Connection connection = DriverManager.getConnection("jdbc:h2:" + this.url, this.user, this.password)){
            result = session.use(connection);
            connection.commit();
            connection.close();
        }catch(SQLException e){
            System.err.println("unable to create connection");
            e.printStackTrace();
        }
        return result;
    }


}