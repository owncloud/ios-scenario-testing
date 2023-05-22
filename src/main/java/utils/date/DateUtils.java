package utils.date;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;

import utils.log.Log;

public class DateUtils {

    //Received the day of the following month in which it expiration date is set
    public static String dateInDaysWithServerFormat2(int days) {
        Log.log(Level.FINE, "Starts: Turns days in date with server response format");
        int day = days;
        int year = todayYear();
        int month = todayMonth();
        if (month == 12){ //Jump to next year
            year++;
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year,month,day);
        Log.log(Level.FINE, "Date to format: " + gregorianCalendar.getTime());
        String dateFormat = gregorianCalendar.get(Calendar.YEAR)
                +"-"+formatInt(gregorianCalendar.get(Calendar.MONTH) + 1)
                +"-"+formatInt(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
        Log.log(Level.FINE, "Date formatted: " + dateFormat);
        return dateFormat;
    }

    private static String formatInt(int dateNumber){
        String day;
        if (dateNumber < 10){
            day = "0" + dateNumber;
        } else {
            day = String.valueOf(dateNumber);
        }
        return day;
    }

    public static int minExpirationDate(int a, int b){
        if (a == 0 && b > 0){
            return b;
        }
        if (a > 0 && b == 0){
            return a;
        }
        return a <= b ? a : b;
    }

    public static int todayDay(){
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        return gregorianCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int todayMonth(){
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        return gregorianCalendar.get(Calendar.MONTH) + 1;
    }

    public static int todayYear(){
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        return gregorianCalendar.get(Calendar.YEAR);
    }

}
