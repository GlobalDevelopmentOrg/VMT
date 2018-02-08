package vehicle.maintenance.tracker.api.database.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>SQLStatementBuilder</code>
 * A chain-able SQL string builder class.
 * Mainly intended to help build basic SQL statements needed for the api
 *
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public class SQLStatementBuilder implements Buildable{

    private String command;
    private List<String> argument = new ArrayList<String>();
    private boolean parameterizeArguments;

    protected SQLStatementBuilder(String command){
        this.command = command;
        this.parameterizeArguments = false;
    }

    protected SQLStatementBuilder(String command, boolean parameterizeArguments){
        this.command = command;
        this.parameterizeArguments = parameterizeArguments;
    }

    public SQLStatementBuilder addArgument(String argument){
        this.argument.add(argument);
        return this;
    }

    public SQLStatementBuilder addArguments(String...arguments){
        for(String arg: arguments){
            this.argument.add(arg);
        }
        return this;
    }

    public SQLStatementBuilder set(String...assignments){
        return new SQLStatementBuilder(this.build() + " SET").addArguments(assignments);
    }

    public SQLStatementBuilder from(String table){
        return new SQLStatementBuilder(this.build() + " FROM " + table);
    }

    public SQLStatementBuilder where(){
        return new SQLStatementBuilder(this.build() + " WHERE");
    }

    public SQLStatementBuilder where(String...assignments){
        return new SQLStatementBuilder(this.build() + " WHERE").addArguments(assignments);
    }

    public SQLStatementBuilder ifNotExists(){
        return new SQLStatementBuilder(this.build() + " IF NOT EXISTS");
    }

    public SQLStatementBuilder ifNotExists(String name){
        return this.ifNotExists().text(name);

    }

    public SQLStatementBuilder into(){
        return new SQLStatementBuilder(this.build() + " INTO");
    }


    public SQLStatementBuilder text(String table){
        return new SQLStatementBuilder(this.build() + " " + table);
    }

    public SQLStatementBuilder table(){
        return new SQLStatementBuilder(this.build() + " TABLE");
    }

    public SQLStatementBuilder table(String name){
        return this.text(name);
    }

    public SQLStatementBuilder database(String name){
        return this.text(name);
    }

    public SQLStatementBuilder values(){
        return new SQLStatementBuilder(this.build() + " VALUES", true){
            @Override
            public SQLStatementBuilder addArguments(String...arguments){
                for(String arg: arguments){
                    super.addArgument("'" + arg + "'");
                }
                return this;
            }
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

    @Override
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
