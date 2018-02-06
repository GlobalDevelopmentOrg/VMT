package vehicle.maintenance.tracker.database.sql.statements;

public class SQLDataTypeBuilder {

    private String name;
    private String command;
    private int size;

    public SQLDataTypeBuilder(String name, String command, int size){
        this.name = name;
        this.command = command;
        this.size = size;
    }

    public SQLDataTypeBuilder(String command, int size){
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
