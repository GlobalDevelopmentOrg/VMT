package vehicle.maintenance.tracker.api.database.sql;

/**
 * Used to build a sql data type declaration string
 * for use in table creation sql statements.
 * Using only the needed methods for this api
 * <code>name INT(10) NOT NULL</code>
 *
 * @author Daile Alimo
 * @since v0.1-SNAPSHOT
 */
public class SQLDataTypeBuilder implements Buildable{

    private String name;
    private String command;
    private int size;

    protected SQLDataTypeBuilder(String name, String command, int size){
        this.name = name;
        this.command = command;
        this.size = size;
    }

    protected SQLDataTypeBuilder(String command, int size){
        this.name = null;
        this.command = command;
        this.size = size;
    }

    public SQLDataTypeBuilder autoIncrement(){
        return new SQLDataTypeBuilder(this.build() + " AUTO_INCREMENT", -1);
    }

    public SQLDataTypeBuilder unique(){
        return new SQLDataTypeBuilder(this.build() + " UNIQUE", -1);
    }

    public SQLDataTypeBuilder notNull(){
        return new SQLDataTypeBuilder(this.build() + " NOT NULL", -1);
    }

    public SQLDataTypeBuilder primaryKey(){
        return new SQLDataTypeBuilder(this.build() + " PRIMARY_KEY", -1);
    }

    @Override
    public String build(){
        StringBuilder build = new StringBuilder();
        if(this.name != null){
            build.append(this.name + " " + this.command);
        }else{
            build.append(this.command);
        }
        if(this.size >= 1){
            build.append("(" + this.size + ")");
        }
        return build.toString();
    }

}
