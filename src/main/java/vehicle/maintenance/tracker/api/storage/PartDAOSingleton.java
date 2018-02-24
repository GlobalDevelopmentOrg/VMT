package vehicle.maintenance.tracker.api.storage;

import vehicle.maintenance.tracker.api.entity.PartEntity;
import vehicle.maintenance.tracker.api.exceptions.DAOInitException;
import vehicle.maintenance.tracker.api.exceptions.StorageCommunicationException;
import vehicle.maintenance.tracker.api.exceptions.TableInitException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>PartDAOSingleton</code>
 * Is the PartEntity Database Access Object responsible
 * for inserting, updating and deleting parts
 * in the database.
 *
 * @author Daile Alimo
 * @since 0.2-SNAPSHOT
 */
@SuppressWarnings("unchecked")
public final class PartDAOSingleton extends DAO<PartEntity> {

    private static final String TABLE_NAME = "parts";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id VARCHAR(36) PRIMARY KEY, vehicleId VARCHAR(36) NOT NULL, name VARCHAR(100) NOT NULL, installationDate VARCHAR(9) NOT NULL, UNIQUE(id))";
    private static final String INSERT_PART = "INSERT INTO " + TABLE_NAME + " (id, vehicleId, name, installationDate) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_VEHICLE_ID = "SELECT * FROM " + TABLE_NAME + " WHERE vehicleId=?";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET vehicleId=?,name=?,installationDate=? WHERE id=?";
    private static final String SELECT = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";

    private static volatile PartDAOSingleton INSTANCE;

    private PartDAOSingleton() throws DAOInitException {
        super(TABLE_NAME);
    }

    public static PartDAOSingleton getInstance() throws DAOInitException {
        if (PartDAOSingleton.INSTANCE == null) {
            synchronized (PartDAOSingleton.class) {
                if(PartDAOSingleton.INSTANCE == null){ // double checked
                    PartDAOSingleton.INSTANCE = new PartDAOSingleton();
                }
            }
        }
        return PartDAOSingleton.INSTANCE;
    }

    @Override
    protected final PartDAOSingleton clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public final void createTable() throws TableInitException {
        try{
            this.connector.openSession(connection -> {
                PreparedStatement statement = connection.prepareStatement(CREATE_TABLE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.execute();
                return null;
            });
        }catch(SQLException e){
            throw new TableInitException(e);
        }
    }

    @Override
    public final void insert(PartEntity entity) throws StorageCommunicationException {
        try {
            this.connector.openSession(connection -> {
                PreparedStatement statement = connection.prepareStatement(INSERT_PART, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.setString(1, entity.getId());
                statement.setString(2, entity.getVehicleId());
                statement.setString(3, entity.getName());
                statement.setString(4, entity.getInstallationDate());
                statement.execute();
                return null;
            });
        } catch (SQLException e) {
            throw new StorageCommunicationException(e);
        }
    }

    @Override
    public final List<PartEntity> findAll() throws StorageCommunicationException {
        try {
            return (List<PartEntity>) this.connector.openSession(connection -> {
                List<PartEntity> collection = new ArrayList<>();
                PreparedStatement statement = connection.prepareStatement(SELECT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    collection.add(new PartEntity(
                            set.getString("id"),
                            set.getString("vehicleId"),
                            set.getString("name"),
                            set.getString("installationDate")
                    ));
                }
                return new SessionResult(collection);
            }).getResult();
        } catch (SQLException e) {
            throw new StorageCommunicationException(e);
        }
    }

    @Override
    public final PartEntity findById(String id) throws StorageCommunicationException {
        try {
            return (PartEntity) this.connector.openSession(connection -> {
                PartEntity found = null;
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.setString(1, id);
                ResultSet set = statement.executeQuery();
                if (set.first()) {
                    found = new PartEntity(
                            set.getString("id"),
                            set.getString("vehicleId"),
                            set.getString("name"),
                            set.getString("installationDate")
                    );
                }
                return new SessionResult(found);
            }).getResult();
        } catch (SQLException e) {
            throw new StorageCommunicationException(e);
        }
    }

    public final List<PartEntity> findByParentId(String vehicleId) throws StorageCommunicationException {
        try {
            return (List<PartEntity>) this.connector.openSession(connection -> {
                List<PartEntity> collection = new ArrayList<>();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_VEHICLE_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.setString(1, vehicleId);
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    collection.add(new PartEntity(
                            set.getString("id"),
                            set.getString("vehicleId"),
                            set.getString("name"),
                            set.getString("installationDate")
                    ));
                }
                return new SessionResult(collection);
            }).getResult();
        } catch (SQLException e) {
            throw new StorageCommunicationException(e);
        }
    }

    @Override
    public final void update(PartEntity entity) throws StorageCommunicationException {
        try {
            this.connector.openSession(connection -> {
                PreparedStatement statement = connection.prepareStatement(UPDATE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.setString(1, entity.getVehicleId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getInstallationDate());
                statement.setString(4, entity.getId());
                statement.executeUpdate();
                return null;
            });
        } catch (SQLException e) {
            throw new StorageCommunicationException(e);
        }
    }

}