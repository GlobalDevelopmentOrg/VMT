package vehicle.maintenance.tracker.api.dao;

import vehicle.maintenance.tracker.api.entity.TaskEntity;
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
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INT(10) PRIMARY KEY AUTO_INCREMENT, parentId VARCHAR(101), name VARCHAR(50) NOT NULL, comment VARCHAR(1000), deadline VARCHAR(9) NOT NULL)";
    private static final String INSERT_TASK = "INSERT INTO " + TABLE_NAME + " (parentId, name, comment, deadline) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_PARENT_ID = "SELECT * FROM " + TABLE_NAME + " WHERE parentId=?";
    private static final String UPDATE = "UPDATE " + TABLE_NAME + " SET parentId=?,name=?,comment=?,deadline=? WHERE id=?";
    private static final String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE parentId=?";
    private static final String SELECT = "SELECT * FROM " + TABLE_NAME;
    private static final String SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
    private static final String GET_LAST_ENTRY = "SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC LIMIT 1";

    private static TaskDAOSingleton INSTANCE;

    private TaskDAOSingleton() throws UnsupportedTableNameException {
        super(TABLE_NAME);
    }

    public static TaskDAOSingleton getInstance(){
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
    public final TaskDAOSingleton clone() throws CloneNotSupportedException{
        throw new CloneNotSupportedException();
    }

    @Override
    public final void createTable(){
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
    public final void insert(TaskEntity entity) {
        this.connector.openSession(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(INSERT_TASK, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                statement.setString(1, entity.getParentId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getComment());
                statement.setString(4, entity.getDeadLineDate());
                statement.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public final List<TaskEntity> findAll() {
        return (List<TaskEntity>) this.connector.openSession(connection -> {
            List<TaskEntity> collection = new ArrayList<>();
            try(PreparedStatement statement = connection.prepareStatement(SELECT, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                ResultSet set = statement.executeQuery();
                while(set.next()){
                    collection.add(new TaskEntity(
                            set.getInt("id"),
                            set.getString("parentId"),
                            set.getString("name"),
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
    public final TaskEntity findById(int id) {
        return (TaskEntity) this.connector.openSession(connection -> {
            TaskEntity found = null;
            try(PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                statement.setInt(1, id);
                ResultSet set = statement.executeQuery();
                if(set.first()){
                    found = new TaskEntity(
                            set.getInt("id"),
                            set.getString("parentId"),
                            set.getString("name"),
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

    public final List<TaskEntity> findByParentId(String parentId){
        return (List<TaskEntity>) this.connector.openSession(connection -> {
            List<TaskEntity> collection = new ArrayList<>();
            try(PreparedStatement statement = connection.prepareStatement(SELECT_BY_PARENT_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                statement.setString(1, parentId);
                ResultSet set = statement.executeQuery();
                while(set.next()){
                    collection.add(new TaskEntity(
                            set.getInt("id"),
                            set.getString("parentId"),
                            set.getString("name"),
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
    public final void update(TaskEntity entity) {
        this.connector.openSession(connection -> {
            try(PreparedStatement statement = connection.prepareStatement(UPDATE, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                statement.setString(1, entity.getParentId());
                statement.setString(2, entity.getName());
                statement.setString(3, entity.getComment());
                statement.setString(4, entity.getDeadLineDate());
                statement.setInt(5, entity.getId());
                statement.executeUpdate();
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    public final void delete(String parentId) {
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
    public final TaskEntity getLastEntry(){
        return (TaskEntity) this.connector.openSession(connection -> {
            TaskEntity found = null;
            try(PreparedStatement statement = connection.prepareStatement(GET_LAST_ENTRY, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
                ResultSet set = statement.executeQuery();
                if(set.first()){
                    found = new TaskEntity(
                            set.getInt("id"),
                            set.getString("parentId"),
                            set.getString("name"),
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