package vehicle.maintenance.tracker.api.utils;

import vehicle.maintenance.tracker.api.dao.HibernateDAO;
import vehicle.maintenance.tracker.api.entity.PartEntity;
import vehicle.maintenance.tracker.api.entity.TaskEntity;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;

import java.util.Random;

public class HibernateMockDataGenerator {

    private HibernateDAO dao;
    private static final Random random = new Random();

    private String brands[] = {
            "KIA MOTORS", "HYUNDAI", "DAEWOO", "FPV", "HONDA", "Mercedes-Benz", "LAMBOGHINI", "LAND-ROVER",
            "HOLDEN", "ACURA", "TOYOTA", "SUZUKI", "NISSAN", "BMW", "FIAT", "ROLLS ROYCE", "MITSUBISHI", "MAZDA",
            "LEXUS", "INFINITI", "CHERY", "AUDI", "MINI", "JAC", "PEUGEOT", "BUGATTI", "FORD", "JAGUAR", "BENTLEY"
    };

    private String models[] = {
            "GT", "GTX", "$x4", "Eco", "Hybrid", "Crawler", "Stalker", "Bird", "T", "NX", "Motor", "V8", "V9", "2 Litre"
    };

    private String parts[] = {
            "Brake pads", "Head lights", "Rear mirror", "Left wing mirror", "Right wing mirror", "Left indicator", "Right indicator",
            "Battery", "Tires", "Windscreen", "Rear Bumper", "Front Bumper", "Fuel Tank"
    };

    private String comments[] = {
            "Checked and working", "Waiting for engineer", "Must check", "Waiting for parts", "Scheduled"
    };

    public HibernateMockDataGenerator(HibernateDAO dao){
        this.dao = dao;
    }

    public void add(int vehicles, int parts, int vehiclesTasks, int partTasks){
        System.out.println("**Start generation of mock data**");
        for(int i = 0; i < vehicles; i++){
            System.out.println("generating vehicle " + i);
            VehicleEntity vehicleEntity = new VehicleEntity(this.getRandomName(), "", this.getRandomReg(), this.getRandomMileage());
            for(int p = 0; p < 1 + random.nextInt(parts); p++){
                PartEntity partEntity = new PartEntity(vehicleEntity.getId(), this.getRandomPart(), "notes", this.getRandomDate());
                for(int pt = 0; pt < 1 + random.nextInt(partTasks); pt++){
                    TaskEntity partTask = new TaskEntity(partEntity.getId(), "Part Task " + pt, this.getRandomComment(), this.getRandomDate());
                    this.dao.save(partTask);
                }
                this.dao.save(partEntity);
            }
            for(int vt = 0; vt < 1 + random.nextInt(vehiclesTasks); vt++){
                TaskEntity vehicleTask = new TaskEntity(vehicleEntity.getId(), "Vehicle Task " + vt, this.getRandomComment(), this.getRandomDate());
                this.dao.save(vehicleTask);
            }
            this.dao.save(vehicleEntity);
        }
        System.out.println("**FINISHED**");
    }

    private String getRandomName(){
        return this.brands[random.nextInt(this.brands.length)] + " " + this.models[random.nextInt(this.models.length)];
    }

    private String getRandomReg(){
        StringBuilder regBuild = new StringBuilder();
        for(int i = 0; i < 10; i++){
            char c = (char) (65 + random.nextInt(26));
            regBuild.append(c);
        }
        return regBuild.toString();
    }

    private int getRandomMileage(){
        return 1 + random.nextInt(99999);
    }

    private String getRandomPart(){
        return this.parts[random.nextInt(this.parts.length)];
    }

    private String getRandomDate(){
        return (1 + random.nextInt(31)) + "/" + (1 + random.nextInt(11)) + "/" + random.nextInt(100);
    }

    private String getRandomComment(){
        return this.comments[random.nextInt(this.comments.length)];
    }

}
