package vehicle.maintenance.tracker.examples;

import vehicle.maintenance.tracker.api.dao.HibernateDAO;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;

public class HibernateDAOExample {

    public static void main(String[] args){
        HibernateDAO dao = new HibernateDAO();
        System.out.println("on load, total vehicles " + dao.count(VehicleEntity.class));
        VehicleEntity entity = new VehicleEntity("Toyota", "My favourite it seems", "ORH58SH27R", 361);
        System.out.println("Saving entity");
        dao.save(entity);
        System.out.println("duplicate test save entity");
        dao.save(entity);
        System.out.println("recount after save : " + dao.count(VehicleEntity.class));

        dao.dispose();
    }

}
