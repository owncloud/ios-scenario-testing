package utils.date;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;

import utils.log.Log;

public class DateUtils {

    public static String dateInDays(String days) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(days));
        Log.log(Level.FINE, "Date: " + gregorianCalendar.getTime());
        int dayNumber = gregorianCalendar.get(Calendar.DAY_OF_MONTH);
        String day;
        if (dayNumber < 10){
            day = "0" + dayNumber;
        } else {
            day = String.valueOf(dayNumber);
        }
        String dateAfterDays = day + " " + getNameMonth(gregorianCalendar.get(Calendar.MONTH))
                + " " + gregorianCalendar.get(Calendar.YEAR);
        Log.log(Level.FINE, "Date to set: " + dateAfterDays);
        return dateAfterDays;
    }

    public static String shortDate(String days) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.add(Calendar.DAY_OF_YEAR, Integer.valueOf(days));
        Log.log(Level.FINE, "Date: " + gregorianCalendar.getTime());
        String shortDate = getNameMonth(gregorianCalendar.get(Calendar.MONTH)).substring(0, 3)
                + " " + gregorianCalendar.get(Calendar.DAY_OF_MONTH)
                + ", " + gregorianCalendar.get(Calendar.YEAR);
        Log.log(Level.FINE, "Short Date: " + shortDate);
        return shortDate;
    }

    private static String getNameMonth(int numMonth) {
        DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
        String[] months = dateFormatSymbols.getMonths();
        if (numMonth >= 0 && numMonth <= 11 ) {
            return months[numMonth];
        } else
            return "";
    }
}
