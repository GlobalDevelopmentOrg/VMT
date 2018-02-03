package vehicle.maintenance.tracker.api.development;

import vehicle.maintenance.tracker.api.VehicleAPI;
import vehicle.maintenance.tracker.model.VehicleInformation;
import vehicle.maintenance.tracker.model.VehiclePart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockDataGenerator {

    private static VehicleAPI vehicleAPI;
    private final Random random = new Random();

    private static final String[] randomNames = {
            "Ford", "Fiesta", "Mustang", "Jaguar", "Tiger", "Lion", "Post", "Posh", "Porshe", "Garrot", "Power", "Pool", "Cloud",
            "Trucker", "Trucks", "Buggy", "Bug", "Rand", "Red", "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9",
            "Mk", "I", "II", "III", "IV", "V", "VI", "VII"
    };

    private static final String[] randomParts = {
            "gearbox", "brake-pad", "wheel", "left head light", "right head light", "brake lights", "left indicator", "right indicator"
    };

    public MockDataGenerator(VehicleAPI vehicleAPI){
        this.vehicleAPI = vehicleAPI;
    }

    public void generateMockData(int size){
        for(int i = 0; i < size; i++){
            MockDataGenerator.vehicleAPI.saveVehicleInformation(new VehicleInformation(this.generateRandomName(), this.generateRandomRegistration(), this.generateRandomMileage()));
            long vid = this.vehicleAPI.countAllVehicles() + 1;
            List<VehiclePart> parts = new ArrayList<VehiclePart>();
            for(int r = 0; r < this.randomPartCount(); r++){
                parts.add(new VehiclePart(vid, randomParts[random.nextInt(randomParts.length)], this.generateRandomMileage()));
            }
            MockDataGenerator.vehicleAPI.saveVehicleParts(parts);
        }
    }

    private String generateRandomName(){
        String first = this.randomNames[this.random.nextInt(randomNames.length)];
        String second = this.randomNames[this.random.nextInt(randomNames.length)];
        return first + " " + second;
    }

    public String generateRandomRegistration(){
        int count = 0;
        StringBuilder sb = new StringBuilder();
        while(count < 10){
            boolean numOrLetter = this.random.nextBoolean();
            int charCode = 0;
            if(numOrLetter){
                charCode = 48 + this.random.nextInt(9);
            }else{
                charCode = 65 + this.random.nextInt(26);
            }
            sb.append((char) charCode);
            count++;
        }
        return sb.toString();
    }

    public int randomPartCount(){
        return 1 + this.random.nextInt(randomParts.length - 1);
    }

    public int generateRandomMileage(){
        return this.random.nextInt(9999);
    }


}
