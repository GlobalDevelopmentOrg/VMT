package vehicle.maintenance.tracker.api.database;

import java.sql.*;

/**
 * <code>H2DatabaseConnector</code>
 * A class to create connections to a H2 Embedded database
 *
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public class H2DatabaseConnector {

    private String url;
    private String user;
    private String password;

    public static final String DRIVER = "org.h2.Driver";

    public H2DatabaseConnector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
        try {
            Class.forName(H2DatabaseConnector.DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SessionResult openSession(DatabaseConnectorSession session) {
        SessionResult result = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:" + this.url, this.user, this.password);
            result = session.use(connection);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}