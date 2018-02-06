package vehicle.maintenance.tracker.database.sql.statements;

import java.util.ArrayList;
import java.util.List;

public class SQLStatementBuilder {

    private String command;
    private List<String> argument = new ArrayList<String>();
    private boolean parameterizeArguments;

    public SQLStatementBuilder(String command){
        this.command = command;
        this.parameterizeArguments = false;
    }

    public SQLStatementBuilder(String command, boolean parameterizeArguments){
        this.command = command;
        this.parameterizeArguments = parameterizeArguments;
    }

    public SQLStatementBuilder addArgument(String argument){
        this.argument.add(argument);
        return this;
    }

    public SQLStatementBuilder where(){
        return new SQLStatementBuilder(this.build() + " WHERE");
    }

    public SQLStatementBuilder ifNotExists(){
        return new SQLStatementBuilder(this.build() + " IF NOT EXISTS");
    }

    public SQLStatementBuilder ifNotExists(String name){
        return new SQLStatementBuilder(this.build() + " IF NOT EXISTS " + name);

    }

    public SQLStatementBuilder into(){
        return new SQLStatementBuilder(this.build() + " INTO");
    }

    public SQLStatementBuilder table(){
        return new SQLStatementBuilder(this.build() + " TABLE");
    }

    public SQLStatementBuilder table(String table){
        return new SQLStatementBuilder(this.build() + " " + table);
    }

    public SQLStatementBuilder values(){
        return new SQLStatementBuilder(this.build() + " VALUES", true){
            @Override
            public SQLStatementBuilder addArgument(String argument){
                super.addArgument("'" + argument + "'");
                return this;
            }
        };
    }

    public SQLStatementBuilder branch(String command){
        return new SQLStatementBuilder(this.build() + " " + command);
    }

    public SQLStatementBuilder branchAndParameterizeArguments(String command){
        return new SQLStatementBuilder(this.build() + " " + command, true);
    }

    public SQLStatementBuilder allFrom(String table){
        this.addArgument("*");
        return new SQLStatementBuilder(this.build() + " FROM " + table);
    }

    public SQLStatementBuilder columnsFrom(String table, int...columnIndexes){
        for(int i = 0; i < columnIndexes.length; i++){
            this.addArgument(String.valueOf(columnIndexes[i]));
        }
        return new SQLStatementBuilder(this.build() + " FROM " + table);
    }

    public SQLStatementBuilder columnsFrom(String table, String...columnNames){
        for(int i = 0; i < columnNames.length; i++){
            this.addArgument(columnNames[i]);
        }        return new SQLStatementBuilder(this.build() + " FROM " + table);
    }

    public String build(){
        StringBuilder build = new StringBuilder(this.command);
        int paramSize = this.argument.size();
        if(paramSize > 0){
            build.append(" ");
            if(this.parameterizeArguments){
                build.append("(");
            }
            for(int i = 0; i < paramSize; i++){
                build.append(argument.get(i));
                if(i < paramSize - 1){
                    build.append(",");
                }
            }
            if(this.parameterizeArguments){
                build.append(")");
            }
        }
        return build.toString();
    }

}
