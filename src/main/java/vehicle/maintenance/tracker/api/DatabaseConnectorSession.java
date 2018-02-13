package vehicle.maintenance.tracker.api;

import java.sql.Connection;

/**
 * This interface should be passed to a connector openSession
 * method to access its connection.
 *
 * @author Daile ALimo
 * @since 0.1-SNAPSHOT
 */
public interface DatabaseConnectorSession {

    SessionResult use(Connection connection);

}
