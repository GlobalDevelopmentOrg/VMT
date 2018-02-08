package vehicle.maintenance.tracker.api.csv;

/**
 * Small class to help with simple conversion to comma-separated-values
 * and back to and array
 *
 * @author Daile Alimo
 * @since 0.1-SNAPSHOT
 */
public class CsvUtils {

    public static final int[] toIntArray(String csv){
        int[] values = null;
        if(csv != null && csv.length() > 0 && csv.contains(",")){
            String separated[] = csv.split(",");
            values = new int[separated.length];
            for(int i = 0; i < values.length; i++){
                values[i] = Integer.parseInt(separated[i]);
            }
        }
        return values;
    }

    public static final String toCsv(int...values){
        StringBuilder csv = new StringBuilder();
        if(values != null && values.length > 0){
            for(int i = 0; i < values.length; i++){
                csv.append(values[i]);
                if(i < values.length - 1){
                    csv.append(",");
                }
            }
        }
        return csv.toString();
    }

}
