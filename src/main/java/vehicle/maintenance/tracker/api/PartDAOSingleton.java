package vehicle.maintenance.tracker.api;

import vehicle.maintenance.tracker.api.exceptions.UnsupportedTableNameException;

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

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INT(10) PRIMARY KEY AUTO_INCREMENT, vehicleId INT(10) NOT NULL, name VARCHAR(100) NOT NULL, installationDate VARCHAR(9) NOT NULL)";
    private static final String INSERT_VEHICLE = "INSERT INTO " + TABLE_NAME + " (vehicleId, name, installationDate) VALUES (?, ?, ?)";
    private static final String SELECT = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
    private static final String SELECT_BY_VEHICLE_ID = "SELECT * FROM " + TABLE_NAME + " WHERE vehicleId=?";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET vehicleId=?,name=?,installationDate=? WHERE id=?";
    private static final String GET_LAST_ENTRY = "SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC LIMIT 1";

    private static PartDAOSingleton INSTANCE;

    private PartDAOSingleton() throws UnsupportedTableNameException {
        super(TABLE_NAME);
    }

    protected static PartDAOSingleton getInstance(){
        if(PartDAOSingleton.INSTANCE == null){
            synchronized (PartDAOSingleton.class){
                try{
                    PartDAOSingleton.INSTANCE = new PartDAOSingleton();
                }catch(UnsupportedTableNameException e){
                    // Hardcoded Table name so this error will never occur
                    // just to be sure we will print stack trace
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
        return PartDAOSingleton.INSTANCE;
    }

    @Override
    protected final PartDAOSingleton clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException();
    }

    @Override
    protected final void createTable(){
        this.connector.openSession(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(CREATE_TABLE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                statement.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    protected final void insert(PartEntity entity) {
        this.connector.openSession(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(INSERT_VEHICLE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                statement.setInt(1, entity.getVehicleId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getInstallationDate());
                statement.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    protected final List<PartEntity> findAll() {
        return (List<PartEntity>) this.connector.openSession(connection -> {
            List<PartEntity> collection = new ArrayList<>();
            try(PreparedStatement statement = connection.prepareStatement(SELECT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                ResultSet set = statement.executeQuery();
                while(set.next()){
                    collection.add(new PartEntity(
                            set.getInt("id"),
                            set.getInt("vehicleId"),
                            set.getString("name"),
                            set.getString("installationDate")
                    ));
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(collection);
        }).getResult();
    }

    @Override
    protected final PartEntity findById(int id) {
        return (PartEntity) this.connector.openSession(connection -> {
            PartEntity found = null;
            try(PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                statement.setInt(1, id);
                ResultSet set = statement.executeQuery();
                if(set.first()){
                    found = new PartEntity(
                            set.getInt("id"),
                            set.getInt("vehicleId"),
                            set.getString("name"),
                            set.getString("installationDate")
                    );
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(found);
        }).getResult();
    }

    protected final List<PartEntity> findByParentId(int vehicleId){
        return (List<PartEntity>) this.connector.openSession(connection -> {
            List<PartEntity> collection = new ArrayList<>();
            try(PreparedStatement statement = connection.prepareStatement(SELECT_BY_VEHICLE_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                statement.setInt(1, vehicleId);
                ResultSet set = statement.executeQuery();
                while(set.next()){
                    collection.add(new PartEntity(
                            set.getInt("id"),
                            set.getInt("vehicleId"),
                            set.getString("name"),
                            set.getString("installationDate")
                    ));
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(collection);
        }).getResult();
    }

    @Override
    protected final void update(PartEntity entity) {
        this.connector.openSession(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(UPDATE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                statement.setInt(1, entity.getVehicleId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getInstallationDate());
                statement.setInt(4, entity.getId());
                statement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    protected final PartEntity getLastEntry(){
        return (PartEntity) this.connector.openSession(connection -> {
            PartEntity found = null;
            try(PreparedStatement statement = connection.prepareStatement(GET_LAST_ENTRY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                ResultSet set = statement.executeQuery();
                if(set.first()){
                    found = new PartEntity(
                            set.getInt("id"),
                            set.getInt("vehicleId"),
                            set.getString("name"),
                            set.getString("installationDate")
                    );
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(found);
        }).getResult();
    }

}