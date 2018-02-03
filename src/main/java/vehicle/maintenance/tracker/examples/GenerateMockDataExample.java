package vehicle.maintenance.tracker.examples;

import vehicle.maintenance.tracker.api.VehicleAPI;
import vehicle.maintenance.tracker.api.development.MockDataGenerator;

/**
 * Demo <code>GenerateMockDataExample</code>
 * This class is only for demo purposes
 *
 * @Author Daile Alimo
 * @Since 0.1-SNAPSHOT
 */
public class GenerateMockDataExample {

    // add n entries into database
    private static final int SIZE = 10;

    public static void main(String[] args){
        // this will add n number of vehicles and random parts for each, each run
        MockDataGenerator mockDataGenerator = new MockDataGenerator(new VehicleAPI());
        mockDataGenerator.generateMockData(GenerateMockDataExample.SIZE);
    }

}
