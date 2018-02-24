package vehicle.maintenance.tracker.api.storage;

import vehicle.maintenance.tracker.api.entity.TaskEntity;
import vehicle.maintenance.tracker.api.exceptions.*;

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
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id VARCHAR(36) PRIMARY KEY, parentId VARCHAR(36), name VARCHAR(50) NOT NULL, comment VARCHAR(1000), dueDate VARCHAR(9) NOT NULL, UNIQUE(id))";
    private static final String INSERT_TASK = "INSERT INTO " + TABLE_NAME + " (id, parentId, name, comment, dueDate) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_PARENT_ID = "SELECT * FROM " + TABLE_NAME + " WHERE parentId=?";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET parentId=?,name=?,comment=?,dueDate=? WHERE id=?";
    private static final String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE parentId=?";
    private static final String SELECT = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";

    private static volatile TaskDAOSingleton INSTANCE;

    private TaskDAOSingleton() throws DAOInitException {
        super(TABLE_NAME);
    }

    public static TaskDAOSingleton getInstance() throws DAOInitException {
        if(TaskDAOSingleton.INSTANCE == null){
            synchronized (TaskDAOSingleton.class){
                if(TaskDAOSingleton.INSTANCE == null){ // double checked
                    TaskDAOSingleton.INSTANCE = new TaskDAOSingleton();
                }
            }
        }
        return TaskDAOSingleton.INSTANCE;
    }

    @Override
    public final TaskDAOSingleton clone() throws CloneNotSupportedException{
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
    public final void insert(TaskEntity entity) throws StorageCommunicationException {
        try {
            this.connector.openSession(connection -> {
                PreparedStatement statement = connection.prepareStatement(INSERT_TASK, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.setString(1, entity.getId());
                statement.setString(2, entity.getParentId());
                statement.setString(3, entity.getName());
                statement.setString(4, entity.getComment());
                statement.setString(5, entity.getDueDate());
                statement.execute();
                return null;
            });
        } catch(SQLException e){
            throw new StorageCommunicationException(e);
        }
    }

    @Override
    public final List<TaskEntity> findAll() throws StorageCommunicationException {
        try {
            return (List<TaskEntity>) this.connector.openSession(connection -> {
                List<TaskEntity> collection = new ArrayList<>();
                PreparedStatement statement = connection.prepareStatement(SELECT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet set = statement.executeQuery();
                while(set.next()){
                    collection.add(new TaskEntity(
                            set.getString("id"),
                            set.getString("parentId"),
                            set.getString("name"),
                            set.getString("comment"),
                            set.getString("dueDate")
                    ));
                }
                return new SessionResult(collection);
            }).getResult();
        } catch (SQLException e) {
            throw new StorageCommunicationException(e);
        }
    }

    @Override
    public final TaskEntity findById(String id) {
        try {
            return (TaskEntity) this.connector.openSession(connection -> {
                TaskEntity found = null;
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.setString(1, id);
                ResultSet set = statement.executeQuery();
                if(set.first()){
                    found = new TaskEntity(
                            set.getString("id"),
                            set.getString("parentId"),
                            set.getString("name"),
                            set.getString("comment"),
                            set.getString("dueDate")
                    );
                }
                return new SessionResult(found);
            }).getResult();
        } catch (SQLException e) {
            throw new StorageCommunicationException(e);
        }
    }

    public final List<TaskEntity> findByParentId(String parentId) {
        try {
            return (List<TaskEntity>) this.connector.openSession(connection -> {
                List<TaskEntity> collection = new ArrayList<>();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_PARENT_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.setString(1, parentId);
                ResultSet set = statement.executeQuery();
                while(set.next()){
                    collection.add(new TaskEntity(
                            set.getString("id"),
                            set.getString("parentId"),
                            set.getString("name"),
                            set.getString("comment"),
                            set.getString("dueDate")
                    ));
                }
                return new SessionResult(collection);
            }).getResult();
        } catch (SQLException e) {
            throw new StorageCommunicationException(e);
        }
    }


    @Override
    public final void update(TaskEntity entity) {
        try {
            this.connector.openSession(connection -> {
                PreparedStatement statement = connection.prepareStatement(UPDATE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.setString(1, entity.getParentId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getComment());
                statement.setString(4, entity.getDueDate());
                statement.setString(5, entity.getId());
                statement.executeUpdate();
                return null;
            });
        } catch (SQLException e) {
            throw new StorageCommunicationException(e);
        }
    }

    public final void deleteAll(String parentId) {
        try {
            this.connector.openSession(connection -> {
                PreparedStatement statement = connection.prepareStatement(DELETE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                statement.setString(1, parentId);
                statement.execute();
                return null;
            });
        } catch (SQLException e) {
            throw new StorageCommunicationException(e);
        }
    }

}