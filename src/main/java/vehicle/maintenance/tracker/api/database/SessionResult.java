package vehicle.maintenance.tracker.api.database;

public class SessionResult <T> {

    private T result;

    public SessionResult(T result){
        this.result = result;
    }

    public T getResult(){
        return this.result;
    }

}
