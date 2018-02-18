package vehicle.maintenance.tracker.api.storage;

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

    protected H2DatabaseConnector(String url, String user, String password) throws ClassNotFoundException {
        this.url = url;
        this.user = user;
        this.password = password;
        Class.forName(H2DatabaseConnector.DRIVER);
    }

    protected final SessionResult openSession(DatabaseConnectorSession session) throws SQLException {
        SessionResult result;

        Connection connection = DriverManager.getConnection("jdbc:h2:" + this.url, this.user, this.password);
        result = session.use(connection);
        connection.commit();
        connection.close();
        return result;
    }


}