package vehicle.maintenance.tracker.replaced.storage;

import vehicle.maintenance.tracker.api.dao.SessionResult;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;
import vehicle.maintenance.tracker.replaced.exceptions.DAOInitException;
import vehicle.maintenance.tracker.replaced.exceptions.StorageCommunicationException;
import vehicle.maintenance.tracker.replaced.exceptions.TableInitException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>VehicleDAOSingleton</code>
 * Is the vehicle Database Access Object responsible
 * for inserting, updating and deleting vehicles
 * in the database.
 *
 * @author Daile Alimo
 * @since 0.2-SNAPSHOT
 */
@SuppressWarnings("unchecked")
public final class VehicleDAOSingleton extends DAO<VehicleEntity> {

    private static final String TABLE_NAME = "vehicles";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id VARCHAR(36) PRIMARY KEY, name VARCHAR(50) NOT NULL, notes VARCHAR(500), registration VARCHAR(255) NOT NULL, mileage INT(10) NOT NULL, UNIQUE(id))";
    private static final String INSERT_VEHICLE = "INSERT INTO " + TABLE_NAME + " (id, name, notes, registration, mileage) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET name=?,notes=?,registration=?,mileage=? WHERE id=?";
    private static final String SELECT = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";

    private static volatile VehicleDAOSingleton INSTANCE;

    private VehicleDAOSingleton() throws DAOInitException {
        super(TABLE_NAME);
    }

    public static VehicleDAOSingleton getInstance() throws DAOInitException {
        if (VehicleDAOSingleton.INSTANCE == null) {
            synchronized (VehicleDAOSingleton.class) {
                if(VehicleDAOSingleton.INSTANCE == null){ // double checked
                    VehicleDAOSingleton.INSTANCE = new VehicleDAOSingleton();
                }
            }
        }
        return VehicleDAOSingleton.INSTANCE;
    }

    @Override
    public final VehicleDAOSingleton clone() throws CloneNotSupportedException {
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
    public final void insert(VehicleEntity entity) throws StorageCommunicationException {
        try{
            this.connector.openSession(connection -> {
                PreparedStatement statement = connection.prepareStatement(INSERT_VEHICLE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.setString(1, entity.getId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getNotes());
                statement.setString(4, entity.getRegistration());
                statement.setInt(5, entity.getMileage());
                statement.execute();
                return null;
            });
        }catch(SQLException e){
            throw new StorageCommunicationException(e);
        }
    }

    @Override
    public final List<VehicleEntity> findAll() throws StorageCommunicationException {
        try{
            return (List<VehicleEntity>) this.connector.openSession(connection -> {
                List<VehicleEntity> collection = new ArrayList<>();
                PreparedStatement statement = connection.prepareStatement(SELECT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    collection.add(new VehicleEntity(
                            set.getString("id"),
                            set.getString("name"),
                            set.getString("notes"),
                            set.getString("registration"),
                            set.getInt("mileage")
                    ));
                }
                return new SessionResult(collection);
            }).getResult();
        }catch(SQLException e){
            throw new StorageCommunicationException(e);
        }
    }

    @Override
    public final VehicleEntity findById(String id) throws StorageCommunicationException {
        try{
            return (VehicleEntity) this.connector.openSession(connection -> {
                VehicleEntity found = null;
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.setString(1, id);
                ResultSet set = statement.executeQuery();
                if (set.first()) {
                    found = new VehicleEntity(
                            set.getString("id"),
                            set.getString("name"),
                            set.getString("notes"),
                            set.getString("registration"),
                            set.getInt("mileage")
                    );
                }
                return new SessionResult(found);
            }).getResult();
        }catch(SQLException e){
            throw new StorageCommunicationException(e);
        }
    }

    @Override
    public final void update(VehicleEntity entity) throws StorageCommunicationException {
        try{
            this.connector.openSession(connection -> {
                PreparedStatement statement = connection.prepareStatement(UPDATE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.setString(1, entity.getName());
                statement.setString(2, entity.getNotes());
                statement.setString(3, entity.getRegistration());
                statement.setInt(4, entity.getMileage());
                statement.setString(5, entity.getId());
                statement.executeUpdate();
                return null;
            });
        }catch(SQLException e){
            throw new StorageCommunicationException(e);
        }
    }

}