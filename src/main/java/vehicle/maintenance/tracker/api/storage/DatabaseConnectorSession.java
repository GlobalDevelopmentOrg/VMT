package vehicle.maintenance.tracker.api.storage;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This interface should be passed to a connector openSession
 * method to access its connection.
 *
 * @author Daile ALimo
 * @since 0.1-SNAPSHOT
 */
public interface DatabaseConnectorSession {

    SessionResult use(Connection connection) throws SQLException;

}
