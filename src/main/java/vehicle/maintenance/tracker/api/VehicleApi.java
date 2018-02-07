package vehicle.maintenance.tracker.api;

import org.h2.tools.Csv;
import vehicle.maintenance.tracker.api.database.H2DatabaseConnector;
import vehicle.maintenance.tracker.api.database.SessionResult;
import vehicle.maintenance.tracker.api.database.sql.SQLStatementFactory;
import vehicle.maintenance.tracker.api.database.sql.statements.SQLDataType;
import vehicle.maintenance.tracker.api.database.sql.statements.SQLStatement;
import vehicle.maintenance.tracker.models.Vehicle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * VehicleAPI for now should consist only of
 * vehicle specific methods.
 */

/**
 * <code>VehicleAPI</code>
 * Find, Insert, Update, Delete vehicle data in the
 * database
 *
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public class VehicleApi {

    public static final String VEHICLE_TABLE_NAME = "vehicle";
    private H2DatabaseConnector connector;

    public VehicleApi(H2DatabaseConnector connector){
        this.connector = connector;
        /*
                As vehicle api will use the connection given to create and manage its own
                table in the database we have to make sure we create one if it doesn't exit.

                Here we create the SQL statement and using a session create
                the table in the database
         */
        final String createTable = SQLStatementFactory.statement(SQLStatement.CREATE)
                .table()
                .ifNotExists()
                .branchAndParameterizeArguments(VehicleApi.VEHICLE_TABLE_NAME)
                .addArguments(
                        SQLStatementFactory
                                .dataType("id", SQLDataType.INT, 10)
                                .autoIncrement()
                                .unique()
                                .notNull()
                                .build(),
                        SQLStatementFactory
                                .dataType("registration", SQLDataType.VARCHAR, 10)
                                .unique()
                                .notNull()
                                .build(),
                        SQLStatementFactory
                                .dataType("mileage", SQLDataType.INT, 5)
                                .notNull()
                                .build(),
                        SQLStatementFactory
                                .dataType("scheduledTaskIds", SQLDataType.VARCHAR, 1000)
                                .build()
                )
                .build();

        // opening the session and creating the table
        this.connector.openSession(connection -> {
            try{
                connection.createStatement().execute(createTable);
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    public void insert(Vehicle vehicle){
        int taskSize = vehicle.countScheduledTasks();
        StringBuilder csv = new StringBuilder();
        if(taskSize > 0){
            for(int i = 0; i < taskSize; i++){
                csv.append(vehicle.getScheduledTask(i));
                if(i < taskSize - 1){
                    csv.append(",");
                }
            }
        }
        final String vehicleSQL = SQLStatementFactory.statement(SQLStatement.INSERT)
                .into()
                .branchAndParameterizeArguments(VehicleApi.VEHICLE_TABLE_NAME)
                .addArguments("registration", "mileage", "scheduledTaskIds")
                .values()
                .addArguments(vehicle.getRegistration(), String.valueOf(vehicle.getMileage()), csv.toString())
                .build();
        this.connector.openSession(connection -> {
            try{
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                statement.execute(vehicleSQL);
            }catch(SQLException e){
                e.printStackTrace();
            }
            return null;
        });
    }

    @SuppressWarnings("unchecked")
    public List<Vehicle> findAll(){
        final String vehicleSQL = SQLStatementFactory.statement(SQLStatement.SELECT)
                .allFrom(VehicleApi.VEHICLE_TABLE_NAME)
                .build();
        /*
                Here may be the first time you notice that we can return a
                SessionResult from a session.
                This is so we can get *results* from the database, do what we like
                with the information and return an object to be cast into the required
                type.

                We will have to watch out for errors when using this class as we are
                using generic types.
         */
        return (List<Vehicle>) this.connector.openSession(connection -> {
            List<Vehicle> vehicles = new ArrayList<>();
            try{
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet result = statement.executeQuery(vehicleSQL);
                while(result.next()){
                    vehicles.add(new Vehicle(result.getInt("id"), result.getInt("mileage"), result.getString("registration"), result.getString("scheduledTaskIds")));
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return new SessionResult(vehicles);
        }).getResult();
    }

    public Vehicle findById(int id){
        final String vehicleSQL = SQLStatementFactory.statement(SQLStatement.SELECT)
                .allFrom(VehicleApi.VEHICLE_TABLE_NAME)
                .where()
                .addArgument("id=" + id)
                .build();
        return (Vehicle) this.connector.openSession(connection -> {
            try{
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet result = statement.executeQuery(vehicleSQL);
                if(result.first()){
                    return new SessionResult<>(
                            new Vehicle(result.getInt("id"), result.getInt("mileage"), result.getString("registration"), result.getString("scheduledTaskIds"))
                    );
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            // return a sessionResult so we don't throw a null pointer when we call getResult
            // the above instantiation of vehicle will now be null.
            // when using this method we either receive a vehicle or null
            // this will have to be checked.
            return new SessionResult(null);
        }).getResult();
    }

    public Vehicle findByRegistration(String registration){
        final String vehicleSQL = SQLStatementFactory.statement(SQLStatement.SELECT)
                .allFrom(VehicleApi.VEHICLE_TABLE_NAME)
                .where()
                .addArgument("registration='" + registration + "'")
                .build();
        return (Vehicle) this.connector.openSession(connection -> {
            try{
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet result = statement.executeQuery(vehicleSQL);
                if(result.first()){
                    return new SessionResult<>(
                            new Vehicle(result.getInt("id"), result.getInt("mileage"), result.getString("registration"), result.getString("scheduledTaskIds"))
                    );
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            // return a sessionResult so we don't throw a null pointer when we call getResult
            // the above instantiation of vehicle will now be null.
            // when using this method we either receive a vehicle or null
            // this will have to be checked.
            return new SessionResult(null);
        }).getResult();
    }

}
