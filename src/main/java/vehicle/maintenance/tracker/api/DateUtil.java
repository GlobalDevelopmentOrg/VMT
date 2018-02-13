package vehicle.maintenance.tracker.api;

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

    protected static final String toDateString(Date date){
        return DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(date);
    }

    protected static final Date getToday(){
        return new Date(System.currentTimeMillis());
    }

    protected static final Date parseDate(String date) throws ParseException {
        return DateFormat.getDateInstance(DateFormat.DATE_FIELD).parse(date);
    }

    protected static final boolean past(Date check, Date against) {
        return check.after(against);
    }

    protected static final boolean pastToday(Date check) {
        return check.after(DateUtil.getToday());
    }

    protected static final boolean isToday(Date check) {
        return DateUtil.same(DateUtil.getToday(), check);
    }

    protected static final boolean same(Date check, Date against) {
        return DateUtil.toDateString(check).contentEquals(DateUtil.toDateString(against));
    }

}
