package vehicle.maintenance.tracker.database.sql.statements;

import java.util.ArrayList;
import java.util.List;

public class SQLCommand {

    // SELECT *
    // UPDATE 0,1,2
    // SELECT name,id
    // AS name,id
    // WHERE 1=1
    // WHERE id=2

    protected String command;
    protected List<String> argument = new ArrayList<String>();
    boolean paramiterizeArguments;

    public SQLCommand(String command){
        this.command = command;
        this.paramiterizeArguments = false;
    }

    public SQLCommand(String command, boolean paramiterizeArguments){
        this.command = command;
        this.paramiterizeArguments = paramiterizeArguments;
    }

    public SQLCommand addArgument(String argument){
        this.argument.add(argument);
        return this;
    }

    public SQLCommand where(){
        return new SQLCommand(this.build() + " WHERE");
    }

    public SQLCommand ifNotExists(){
        this.argument.add("IF NOT EXISTS");
        return this;
    }

    public SQLCommand ifNotExists(String name){
        this.argument.add("IF NOT EXISTS " + name);
        return this;
    }

    public SQLCommand into(){
        return new SQLCommand(this.build() + " INTO");
    }

    public SQLCommand table(){
        return new SQLCommand(this.build() + " TABLE");
    }

    public SQLCommand table(String table){
        return new SQLCommand(this.build() + " " + table);
    }

    public SQLCommand values(){
        return new SQLCommand(this.build() + " VALUES", true);
    }

    public SQLCommand branch(String command){
        return new SQLCommand(this.build() + " " + command);
    }

    public SQLCommand branchAndParameterizeArguments(String command){
        return new SQLCommand(this.build() + " " + command, true);
    }

    public String build(){
        StringBuilder build = new StringBuilder(this.command);
        int paramSize = this.argument.size();
        if(paramSize > 0){
            build.append(" ");
            if(this.paramiterizeArguments){
                build.append("(");
            }
            for(int i = 0; i < paramSize; i++){
                build.append(argument.get(i));
                if(i < paramSize - 1){
                    build.append(",");
                }
            }
            if(this.paramiterizeArguments){
                build.append(")");
            }
        }
        return build.toString();
    }

}
