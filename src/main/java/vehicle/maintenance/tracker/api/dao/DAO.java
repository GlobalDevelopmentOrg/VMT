package vehicle.maintenance.tracker.api.dao;

import vehicle.maintenance.tracker.api.exceptions.UnsupportedTableNameException;

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
public abstract class DAO<E> {

    H2DatabaseConnector connector;

    private static final String DATABASE = "database";
    private static final String USER = "admin@root_user";
    private static final String PASSWORD = "pass@root_user/admin";

    private String DELETE;
    private String TRUNCATE;
    private String COUNT;
    private String EXISTS;


    DAO(String tableName) throws UnsupportedTableNameException {
        /*
         * Here we force the use of only line word alphabetical naming for tables
         * This is due to the need below to inject this name into an sql query.
         * If we do not prevent whitespace or special characters a user could
         * mount a sql injection attack. This step ensures this attack would not
         * be possible here.
         */
        if(!tableName.matches("[a-zA-Z]+")){
            throw new UnsupportedTableNameException(tableName);
        }
        this.connector = new H2DatabaseConnector("./" + DAO.DATABASE, DAO.USER, DAO.PASSWORD);
        this.DELETE = "DELETE FROM :tableName WHERE id=?".replace(":tableName", tableName);
        this.TRUNCATE = "TRUNCATE TABLE :tableName".replace(":tableName", tableName);
        this.COUNT = "SELECT COUNT(1) FROM :tableName".replace(":tableName", tableName);
        this.EXISTS = "SELECT id FROM :tableName WHERE id=?".replace(":tableName", tableName);
        this.createTable();
    }

    protected abstract void createTable();
    protected abstract void insert(E entity);
    protected abstract List<E> findAll();
    protected abstract E findById(int id);
    protected abstract void update(E entity);
    protected abstract E getLastEntry();

    public final void delete(int id) {
        this.connector.openSession(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(DELETE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                statement.setInt(1, id);
                statement.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    public final void truncate() {
        this.connector.openSession(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(TRUNCATE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                statement.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    public final int count(){
        return (Integer) this.connector.openSession(connection -> {
            int result = 0;
            try(PreparedStatement statement = connection.prepareStatement(COUNT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                ResultSet set = statement.executeQuery();
                if(set.first()){
                    result = set.getInt(1);
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(result);
        }).getResult();
    }

    public final boolean exists(){
        return (Boolean) this.connector.openSession(connection -> {
            int result = 0;
            try(PreparedStatement statement = connection.prepareStatement(EXISTS, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                ResultSet set = statement.executeQuery();
                if(set.first()){
                    result = set.getInt(1);
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(result == 1);
        }).getResult();
    }

}
