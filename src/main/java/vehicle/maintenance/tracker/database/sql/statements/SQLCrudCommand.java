package vehicle.maintenance.tracker.database.sql.statements;

public class SQLCrudCommand extends SQLCommand {

    public SQLCrudCommand(String command){
        super(command);
    }

    public SQLCommand allFrom(String table){
        this.addArgument("*");
        return new SQLCommand(this.build() + " FROM " + table);
    }

    public SQLCommand columnsFrom(String table, int...columnIndexes){
        for(int i = 0; i < columnIndexes.length; i++){
            this.addArgument(String.valueOf(columnIndexes[i]));
        }
        return new SQLCommand(this.build() + " FROM " + table);
    }

    public SQLCommand columnsFrom(String table, String...columnNames){
        for(int i = 0; i < columnNames.length; i++){
            this.addArgument(columnNames[i]);
        }        return new SQLCommand(this.build() + " FROM " + table);
    }

}
