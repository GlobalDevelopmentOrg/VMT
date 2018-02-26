package vehicle.maintenance.tracker.api.dao;

import org.hibernate.ResourceClosedException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import vehicle.maintenance.tracker.api.entity.Entity;
import vehicle.maintenance.tracker.api.entity.VehicleEntity;
import vehicle.maintenance.tracker.api.utils.HibernateUtil;

import java.util.List;

/**
 * Hibernate DAO manages all basic CRUD
 * operations for any child of Entity class
 * not to be confused with the DAO class in this package.
 * This is a different implementation of the same functionality
 * using hibernate rather than basic SQL queries using the
 * standard Statement and ResultSet classes.
 */
@SuppressWarnings({"unchecked", "unused"})
public class HibernateDAO {

    public List<? extends Entity> getAll(Class entityClass){
        return (List<VehicleEntity>) HibernateUtil.use(session -> {
            return new SessionResult(session.createCriteria(entityClass).list());
        }).getResult();
    }

    public List<? extends Entity> getAssociatedWith(Class entityClass, String field, String value){
        return (List<VehicleEntity>) HibernateUtil.use(session -> {
            return new SessionResult(session.createCriteria(entityClass).add(Restrictions.eq(field, value)).list());
        }).getResult();
    }

    public Entity getById(Class entityClass, String id){
        return (VehicleEntity) HibernateUtil.use(session -> {
            return new SessionResult(session.get(entityClass, id));
        }).getResult();
    }

    public HibernateDAO delete(Entity entity){
        HibernateUtil.use(session -> {
            session.delete(entity);
            return null;
        });
        return this;
    }

    public HibernateDAO save(Entity entity) {
        HibernateUtil.use(session -> {
            session.saveOrUpdate(entity);
            return null;
        });
        return this;
    }

    public long count(Class entityClass) {
        return (long) HibernateUtil.use(session -> {
            Object result = session.createCriteria(entityClass).setProjection(Projections.rowCount()).uniqueResult();
            if(result != null){
                return new SessionResult(result);
            }
            return new SessionResult<>(0L);
        }).getResult();
    }

    public void dispose() throws ResourceClosedException {
        if(HibernateUtil.isOpen()){
            HibernateUtil.close();
        }else{
            throw new ResourceClosedException("Resource already closed");
        }
    }

}
