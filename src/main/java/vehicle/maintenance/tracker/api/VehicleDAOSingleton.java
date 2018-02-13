package vehicle.maintenance.tracker.api;

import vehicle.maintenance.tracker.api.exceptions.UnsupportedTableNameException;

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

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INT(10) PRIMARY KEY AUTO_INCREMENT, registration VARCHAR(255) NOT NULL, mileage INT(10) NOT NULL)";
    private static final String INSERT_VEHICLE = "INSERT INTO " + TABLE_NAME + " (registration, mileage) VALUES (?, ?)";
    private static final String SELECT = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET registration=?,mileage=? WHERE id=?";
    private static final String GET_LAST_ENTRY = "SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC LIMIT 1";


    private static VehicleDAOSingleton INSTANCE;

    private VehicleDAOSingleton() throws UnsupportedTableNameException {
        super(TABLE_NAME);
    }

    protected static VehicleDAOSingleton getInstance(){
        if(VehicleDAOSingleton.INSTANCE == null){
            synchronized (VehicleDAOSingleton.class){
                try{
                    VehicleDAOSingleton.INSTANCE = new VehicleDAOSingleton();
                }catch(UnsupportedTableNameException e){
                    // Hardcoded Table name so this error will never occur
                    // just to be sure we will print stack trace
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
        return VehicleDAOSingleton.INSTANCE;
    }
    @Override
    protected final VehicleDAOSingleton clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException();
    }

    @Override
    protected final void createTable(){
        this.connector.openSession(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(CREATE_TABLE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                statement.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    protected final void insert(VehicleEntity entity) {
        this.connector.openSession(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(INSERT_VEHICLE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                statement.setString(1, entity.getRegistration());
                statement.setInt(2, entity.getMileage());
                statement.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    protected final List<VehicleEntity> findAll() {
        return (List<VehicleEntity>) this.connector.openSession(connection -> {
            List<VehicleEntity> collection = new ArrayList<>();
            try(PreparedStatement statement = connection.prepareStatement(SELECT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                ResultSet set = statement.executeQuery();
                while(set.next()){
                    collection.add(new VehicleEntity(
                            set.getInt("id"),
                            set.getInt("mileage"),
                            set.getString("registration")
                    ));
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(collection);
        }).getResult();
    }

    @Override
    protected final VehicleEntity findById(int id) {
        return (VehicleEntity) this.connector.openSession(connection -> {
            VehicleEntity found = null;
            try(PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                statement.setInt(1, id);
                ResultSet set = statement.executeQuery();
                if(set.first()){
                    found = new VehicleEntity(set.getInt("id"), set.getInt("mileage"), set.getString("registration"));
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(found);
        }).getResult();
    }

    @Override
    protected final void update(VehicleEntity entity) {
        this.connector.openSession(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(UPDATE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                statement.setString(1, entity.getRegistration());
                statement.setInt(2, entity.getMileage());
                statement.setInt(3, entity.getId());
                statement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    protected final VehicleEntity getLastEntry(){
        return (VehicleEntity) this.connector.openSession(connection -> {
            VehicleEntity found = null;
            try(PreparedStatement statement = connection.prepareStatement(GET_LAST_ENTRY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                ResultSet set = statement.executeQuery();
                if(set.first()){
                    found = new VehicleEntity(set.getInt("id"), set.getInt("mileage"), set.getString("registration"));
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(found);
        }).getResult();
    }

}