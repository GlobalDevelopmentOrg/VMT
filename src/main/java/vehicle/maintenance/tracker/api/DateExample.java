package vehicle.maintenance.tracker.api;

import vehicle.maintenance.tracker.api.DateUtil;

import java.text.ParseException;
import java.util.Date;

public class DateExample {

    public static void main(String[] args){
        Date today = DateUtil.getToday();
        System.out.printf("today is %s\n", DateUtil.toDateString(today));

        try{
            Date another = DateUtil.parseDate("13/02/18");

            boolean past = DateUtil.past(today, another);
            boolean same = DateUtil.same(today, another);

            System.out.printf("%s is past %s : %b\n", DateUtil.toDateString(today), DateUtil.toDateString(another), past);
            System.out.printf("%s is same as %s : %b\n", DateUtil.toDateString(today), DateUtil.toDateString(another), same);

        }catch (ParseException e){
            e.printStackTrace();
        }
    }

}
