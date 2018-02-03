package vehicle.maintenance.tracker.api;

import vehicle.maintenance.tracker.api.persistence.H2DatabaseConnector;
import vehicle.maintenance.tracker.model.VehicleInformation;
import vehicle.maintenance.tracker.model.VehiclePart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is the base API. It deals with interaction
 * between the database.
 *
 * @Author Daile Alimo
 * @Since 0.1-SNAPSHOT
 */
public class VehicleAPI implements VehicleApiMethods {

    // ./ is H2's way of selecting path for file, data is file name
    private static final String DATABASE = "./data";

    // table names
    private static final String VEHICLE_TABLE = "vehicle";
    private static final String VEHICLE_PART_TABLE = "part";

    // database auth not important
    private static final String DATABASE_USER = "admin";
    private static final String DATABASE_PASSWORD = "123845345178231";

    // SQL template strings
    private static final String SELECT_ALL_VEHICLES_SQL = "SELECT * FROM " + VEHICLE_TABLE;
    private static final String SELECT_VEHICLE_SQL = "SELECT * FROM " + VEHICLE_TABLE + " WHERE id=:id";
    private static final String SELECT_VEHICLES_SQL = "SELECT * FROM " + VEHICLE_TABLE + " LIMIT :limit OFFSET :offset"; // "SELECT * FROM " + VEHICLE_TABLE + " ORDER BY id LIMIT :offset,:limit";
    private static final String SELECT_PARTS_SQL = "SELECT * FROM " + VEHICLE_PART_TABLE + " WHERE id=:id";
    private static final String SELECT_ID_FROM_PARTS_FOR_SQL = "SELECT id FROM " + VEHICLE_PART_TABLE + " WHERE vehicleId=:vehicleId";

    private static final String INSERT_VEHICLE_SQL = "INSERT INTO " + VEHICLE_TABLE + " (name, registration, mileage) VALUES (':name', ':registration', ':mileage')";
    private static final String INSERT_PART_SQL = "INSERT INTO " + VEHICLE_PART_TABLE + " (vehicleId, name, mileage) VALUES (':vehicleId', ':name', ':mileage')";

    public VehicleAPI(){
        H2DatabaseConnector h2DatabaseConnector = H2DatabaseConnector.using(VehicleAPI.DATABASE, VehicleAPI.DATABASE_USER, VehicleAPI.DATABASE_PASSWORD);
        // for now insert SQL directly as we only need to create these two tables one
        // and only when creating the API
        h2DatabaseConnector.executeUpdate("CREATE TABLE IF NOT EXISTS " + VEHICLE_TABLE + " (`id` int(10) NOT NULL AUTO_INCREMENT, `name` varchar(255) NOT NULL, `registration` varchar(100) NOT NULL, `mileage` int(100) NOT NULL, PRIMARY KEY (`id`))");
        h2DatabaseConnector.executeUpdate("CREATE TABLE IF NOT EXISTS " + VEHICLE_PART_TABLE + " (`id` int(10) NOT NULL AUTO_INCREMENT, `vehicleId` int(10) NOT NULL, `name` varchar(255) NOT NULL, `mileage` int(100) NOT NULL, PRIMARY KEY (`id`))");
        h2DatabaseConnector.close();
    }

    public List<VehicleInformation> getAllVehicleInformation(){
        List<VehicleInformation> vehicleInformation = new ArrayList<VehicleInformation>();
        H2DatabaseConnector h2DatabaseConnector = H2DatabaseConnector.using(VehicleAPI.DATABASE, VehicleAPI.DATABASE_USER, VehicleAPI.DATABASE_PASSWORD);
        ResultSet results = h2DatabaseConnector.executeQuery(VehicleAPI.SELECT_ALL_VEHICLES_SQL);
        try{
            while(results.next()){
                long id = results.getLong("id");
                String name = results.getString("name");
                String registration = results.getString("registration");
                int mileage = results.getInt("mileage");
                List<Long> vehicleIds = this.getVehiclePartIds(id);
                vehicleInformation.add(new VehicleInformation(id, name, registration, mileage));
            }
            results.close();
            h2DatabaseConnector.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return vehicleInformation;
    }

    public List<VehicleInformation> getVehicleInformation(int offset, int max){
        List<VehicleInformation> vehicleInformation = new ArrayList<VehicleInformation>();
        H2DatabaseConnector h2DatabaseConnector = H2DatabaseConnector.using(VehicleAPI.DATABASE, VehicleAPI.DATABASE_USER, VehicleAPI.DATABASE_PASSWORD);
        ResultSet results = h2DatabaseConnector.executeQuery(VehicleAPI.SELECT_VEHICLES_SQL.replace(":offset", String.valueOf(offset))
                .replace(":limit", String.valueOf(max)));
        try{
            while(results.next()){
                long id = results.getLong("id");
                String name = results.getString("name");
                String registration = results.getString("registration");
                int mileage = results.getInt("mileage");
                vehicleInformation.add(new VehicleInformation(id, name, registration, mileage));
            }
            results.close();
            h2DatabaseConnector.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return vehicleInformation;
    }

    public VehicleInformation getVehicleInformation(long id) {
        H2DatabaseConnector h2DatabaseConnector = H2DatabaseConnector.using(VehicleAPI.DATABASE, VehicleAPI.DATABASE_USER, VehicleAPI.DATABASE_PASSWORD);
        ResultSet results = h2DatabaseConnector.executeQuery(VehicleAPI.SELECT_VEHICLE_SQL.replace(":id", String.valueOf(id)));
        try{
            if(results.first()){
                String name = results.getString("name");
                String registration = results.getString("registration");
                int mileage = results.getInt("mileage");
                return new VehicleInformation(id, name, registration, mileage);
            }
            results.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            h2DatabaseConnector.close();
        }
        return null;
    }

    public List<Long> getVehiclePartIds(long vehicleId){
        List<Long> partIds = new ArrayList<Long>();
        H2DatabaseConnector h2DatabaseConnector = H2DatabaseConnector.using(VehicleAPI.DATABASE, VehicleAPI.DATABASE_USER, VehicleAPI.DATABASE_PASSWORD);
        ResultSet results = h2DatabaseConnector.executeQuery(VehicleAPI.SELECT_ID_FROM_PARTS_FOR_SQL.replace(":vehicleId", String.valueOf(vehicleId)));
        try{
            while(results.next()){
                // get id column from result and add them to partIds list
                partIds.add(results.getLong("id"));
            }
            results.close();
            h2DatabaseConnector.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return partIds;
    }

    public VehiclePart getVehiclePart(long id){
        H2DatabaseConnector h2DatabaseConnector = H2DatabaseConnector.using(VehicleAPI.DATABASE, VehicleAPI.DATABASE_USER, VehicleAPI.DATABASE_PASSWORD);
        ResultSet results = h2DatabaseConnector.executeQuery(VehicleAPI.SELECT_PARTS_SQL.replace(":id", String.valueOf(id)));
        try{
            long vehicleId = 0;
            String name = null;
            int mileage = 0;

            if(results.first()){
                vehicleId = results.getLong("vehicleId");
                name = results.getString("name");
                mileage = results.getInt("mileage");
            }
            results.close();

            return new VehiclePart(id, vehicleId, name, mileage);
        }catch (SQLException e){
            e.printStackTrace();
        }finally{
            h2DatabaseConnector.close();
        }
        return null;
    }

    public int saveVehicleInformation(VehicleInformation vehicleInformation){
        H2DatabaseConnector h2DatabaseConnector = H2DatabaseConnector.using(VehicleAPI.DATABASE, VehicleAPI.DATABASE_USER, VehicleAPI.DATABASE_PASSWORD);
        int i = h2DatabaseConnector.executeUpdate(VehicleAPI.INSERT_VEHICLE_SQL
                .replace(":name", String.valueOf(vehicleInformation.getName()))
                .replace(":registration", String.valueOf(vehicleInformation.getRegistration()))
                .replace(":mileage", String.valueOf(vehicleInformation.getMileage())));
        h2DatabaseConnector.close();
        return i;
    }

    public boolean saveVehicleParts(List<VehiclePart> vehicleParts){
        int resultSum = 0;
        for(VehiclePart part: vehicleParts){
            resultSum += saveVehiclePart(part);
        }
        return resultSum == vehicleParts.size();
    }

    public int saveVehiclePart(VehiclePart vehicleParts){
        H2DatabaseConnector h2DatabaseConnector = H2DatabaseConnector.using(VehicleAPI.DATABASE, VehicleAPI.DATABASE_USER, VehicleAPI.DATABASE_PASSWORD);
        int i = h2DatabaseConnector.executeUpdate(VehicleAPI.INSERT_PART_SQL
                .replace(":vehicleId", String.valueOf(vehicleParts.getVehicleId()))
                .replace(":name", String.valueOf(vehicleParts.getName()))
                .replace(":mileage", String.valueOf(vehicleParts.getMileage())));
        h2DatabaseConnector.close();
        return i;
    }

    public long countAllVehicles(){
        H2DatabaseConnector h2DatabaseConnector = H2DatabaseConnector.using(VehicleAPI.DATABASE, VehicleAPI.DATABASE_USER, VehicleAPI.DATABASE_PASSWORD);
        long i = h2DatabaseConnector.count(VehicleAPI.VEHICLE_TABLE);
        h2DatabaseConnector.close();
        return i;
    }

    public long countAllParts(){
        H2DatabaseConnector h2DatabaseConnector = H2DatabaseConnector.using(VehicleAPI.DATABASE, VehicleAPI.DATABASE_USER, VehicleAPI.DATABASE_PASSWORD);
        long i = h2DatabaseConnector.count(VehicleAPI.VEHICLE_PART_TABLE);
        h2DatabaseConnector.close();
        return i;
    }



}
