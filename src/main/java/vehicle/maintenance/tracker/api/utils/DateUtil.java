package vehicle.maintenance.tracker.api.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Simple utility for getting today's date on the local system
 * parsing dates for strings and helpful comparison methods
 *
 * @author Daile Alimo
 * @since 0.2-SNAPSHOT
 */
public final class DateUtil {

    public static final String toDateString(Date date){
        return DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date);
    }

    public static final Date getToday(){
        return new Date(System.currentTimeMillis());
    }

    public static final Date parseDate(String date) throws ParseException {
        return DateFormat.getDateInstance(DateFormat.DATE_FIELD).parse(date);
    }

    public static final boolean past(Date check, Date against) {
        return check.after(against);
    }

    public static final boolean pastToday(Date check) {
        return check.after(DateUtil.getToday());
    }

    public static final boolean isToday(Date check) {
        return DateUtil.same(DateUtil.getToday(), check);
    }

    public static final boolean same(Date check, Date against) {
        return DateUtil.toDateString(check).contentEquals(DateUtil.toDateString(against));
    }

}
