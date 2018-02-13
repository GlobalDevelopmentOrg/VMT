package vehicle.maintenance.tracker.api;

import vehicle.maintenance.tracker.api.exceptions.UnsupportedTableNameException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>TaskDAOSingleton</code>
 * Is the TaskEntity Database Access Object responsible
 * for inserting, updating and deleting tasks
 * in the database.
 *
 * @author Daile Alimo
 * @since 0.2-SNAPSHOT
 */
@SuppressWarnings("unchecked")
public final class TaskDAOSingleton extends DAO<TaskEntity> {

    private static final String TABLE_NAME = "tasks";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INT(10) PRIMARY KEY AUTO_INCREMENT, parentId VARCHAR(101), comment VARCHAR(1000), deadline VARCHAR(9) NOT NULL)";
    private static final String INSERT_VEHICLE = "INSERT INTO " + TABLE_NAME + " (parentId, comment, deadline) VALUES (?, ?, ?)";
    private static final String SELECT = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
    private static final String SELECT_BY_PARENT_ID = "SELECT * FROM " + TABLE_NAME + " WHERE parentId=?";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET parentId=?,comment=?,deadline=? WHERE id=?";
    private static String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE parentId=?";
    private static final String GET_LAST_ENTRY = "SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC LIMIT 1";

    private static TaskDAOSingleton INSTANCE;

    private TaskDAOSingleton() throws UnsupportedTableNameException {
        super(TABLE_NAME);
    }

    protected static TaskDAOSingleton getInstance(){
        if(TaskDAOSingleton.INSTANCE == null){
            synchronized (PartDAOSingleton.class){
                try{
                    TaskDAOSingleton.INSTANCE = new TaskDAOSingleton();
                }catch(UnsupportedTableNameException e){
                    // Hardcoded Table name so this error will never occur
                    // just to be sure we will print stack trace
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
        return TaskDAOSingleton.INSTANCE;
    }

    @Override
    protected final TaskDAOSingleton clone() throws CloneNotSupportedException{
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
    protected final void insert(TaskEntity entity) {
        this.connector.openSession(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(INSERT_VEHICLE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                statement.setString(1, entity.getParentId());
                statement.setString(2, entity.getComment());
                statement.setString(3, entity.getDeadlineDate());
                statement.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    protected final List<TaskEntity> findAll() {
        return (List<TaskEntity>) this.connector.openSession(connection -> {
            List<TaskEntity> collection = new ArrayList<>();
            try(PreparedStatement statement = connection.prepareStatement(SELECT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                ResultSet set = statement.executeQuery();
                while(set.next()){
                    collection.add(new TaskEntity(
                            set.getInt("id"),
                            set.getString("parentId"),
                            set.getString("comment"),
                            set.getString("deadline")
                    ));
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(collection);
        }).getResult();
    }

    @Override
    protected final TaskEntity findById(int id) {
        return (TaskEntity) this.connector.openSession(connection -> {
            TaskEntity found = null;
            try(PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                statement.setInt(1, id);
                ResultSet set = statement.executeQuery();
                if(set.first()){
                    found = new TaskEntity(
                            set.getInt("id"),
                            set.getString("parentId"),
                            set.getString("comment"),
                            set.getString("deadline")
                    );
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(found);
        }).getResult();
    }

    protected final List<TaskEntity> findByParentId(String parentId){
        return (List<TaskEntity>) this.connector.openSession(connection -> {
            List<TaskEntity> collection = new ArrayList<>();
            try(PreparedStatement statement = connection.prepareStatement(SELECT_BY_PARENT_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                statement.setString(1, parentId);
                ResultSet set = statement.executeQuery();
                while(set.next()){
                    collection.add(new TaskEntity(
                            set.getInt("id"),
                            set.getString("parentId"),
                            set.getString("comment"),
                            set.getString("deadline")
                    ));
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(collection);
        }).getResult();
    }


    @Override
    protected final void update(TaskEntity entity) {
        this.connector.openSession(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(UPDATE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                statement.setString(1, entity.getParentId());
                statement.setString(2, entity.getComment());
                statement.setString(3, entity.getDeadlineDate());
                statement.setInt(4, entity.getId());
                statement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    protected final void delete(String parentId) {
        this.connector.openSession(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(DELETE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                statement.setString(1, parentId);
                statement.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    protected final TaskEntity getLastEntry(){
        return (TaskEntity) this.connector.openSession(connection -> {
            TaskEntity found = null;
            try(PreparedStatement statement = connection.prepareStatement(GET_LAST_ENTRY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                ResultSet set = statement.executeQuery();
                if(set.first()){
                    found = new TaskEntity(
                            set.getInt("id"),
                            set.getString("parentId"),
                            set.getString("comment"),
                            set.getString("deadline")
                    );
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(found);
        }).getResult();
    }

}