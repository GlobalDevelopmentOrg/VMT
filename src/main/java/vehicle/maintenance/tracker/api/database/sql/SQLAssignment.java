package vehicle.maintenance.tracker.api.database.sql;

public class SQLAssignment implements Buildable{

    private String property;
    private Object value;

    public SQLAssignment(String property, Object value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public String build(){
        return this.property + "='" + this.value.toString() + "'";
    }

}
