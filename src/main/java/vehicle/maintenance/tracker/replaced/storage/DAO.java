package vehicle.maintenance.tracker.replaced.storage;

import vehicle.maintenance.tracker.api.dao.SessionResult;
import vehicle.maintenance.tracker.api.entity.Entity;
import vehicle.maintenance.tracker.replaced.exceptions.DAOInitException;
import vehicle.maintenance.tracker.replaced.exceptions.StorageCommunicationException;
import vehicle.maintenance.tracker.replaced.exceptions.TableInitException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * <code>DAO</code>
 * The Database Access Object is a
 * class for building custom database management
 * classes to perform CRUD operations for a set table.
 *
 * @author Daile Alimo
 * @since 0.2-SNAPSHOT
 */
@SuppressWarnings("unchecked")
public abstract class DAO<E extends Entity> {

    H2DatabaseConnector connector;

    private static final String DATABASE = "database";
    private static final String USER = "admin@root_user";
    private static final String PASSWORD = "pass@root_user/admin";

    private final String DELETE;
    private final String TRUNCATE;
    private final String COUNT;
    private final String EXISTS;


    DAO(String tableName) throws DAOInitException {
        /*
         * Here we force the use of only line word alphabetical naming for tables
         * This is due to the need below to inject this name into an sql query.
         * If we do not prevent whitespace or special characters a user could
         * mount a sql injection attack. This step ensures this attack would not
         * be possible here.
         */
        if(!tableName.matches("[a-zA-Z]+")){
            throw new DAOInitException("bad table name");
        }
        try{
            this.connector = new H2DatabaseConnector("./" + DAO.DATABASE + "/" + tableName, DAO.USER, DAO.PASSWORD);
            this.createTable();
        }catch(ClassNotFoundException | TableInitException e){
            throw new DAOInitException(e);
        }
        this.DELETE = "DELETE FROM :tableName WHERE id=?".replace(":tableName", tableName);
        this.TRUNCATE = "TRUNCATE TABLE :tableName".replace(":tableName", tableName);
        this.COUNT = "SELECT COUNT(1) FROM :tableName".replace(":tableName", tableName);
        this.EXISTS = "SELECT id FROM :tableName WHERE id=?".replace(":tableName", tableName);
    }

    protected abstract void createTable() throws TableInitException;
    protected abstract void insert(E entity) throws StorageCommunicationException;
    protected abstract List<E> findAll() throws StorageCommunicationException;
    protected abstract E findById(String id) throws StorageCommunicationException;
    protected abstract void update(E entity) throws StorageCommunicationException;

    public final void delete(E entity) throws StorageCommunicationException{
        try{
            this.connector.openSession(connection -> {
                PreparedStatement statement = connection.prepareStatement(DELETE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.setString(1, entity.getId());
                statement.execute();
                return null;
            });
        }catch(SQLException e){
            throw new StorageCommunicationException(e);
        }
    }

    public final void truncate() throws StorageCommunicationException{
        try{
            this.connector.openSession(connection -> {
                PreparedStatement statement = connection.prepareStatement(TRUNCATE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.execute();
                return null;
            });
        }catch(SQLException e){
            throw new StorageCommunicationException(e);
        }
    }

    public final int count() throws StorageCommunicationException{
        try{
            return (Integer) this.connector.openSession(connection -> {
                int result = 0;
                PreparedStatement statement = connection.prepareStatement(COUNT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet set = statement.executeQuery();
                if(set.first()){
                    result = set.getInt(1);
                }
                return new SessionResult(result);
            }).getResult();
        }catch(SQLException e){
            throw new StorageCommunicationException(e);
        }
    }

    public final boolean exists(E entity) throws StorageCommunicationException{
        try{
            return (Boolean) this.connector.openSession(connection -> {
                boolean result = false;
                PreparedStatement statement = connection.prepareStatement(EXISTS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.setString(1, entity.getId());
                ResultSet set = statement.executeQuery();
                if(set.first()){
                    result = true;
                }
                return new SessionResult(result);
            }).getResult();
        }catch(SQLException e){
            throw new StorageCommunicationException(e);
        }
    }

}
