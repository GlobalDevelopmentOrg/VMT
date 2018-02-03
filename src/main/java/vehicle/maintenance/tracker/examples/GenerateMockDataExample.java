package vehicle.maintenance.tracker.examples;

import vehicle.maintenance.tracker.api.VehicleAPI;
import vehicle.maintenance.tracker.api.development.MockDataGenerator;

/**
 * Demo of the usage of MockDataGenerator
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
