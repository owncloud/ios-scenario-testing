package utils.date;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;

import utils.log.Log;

public class DateUtils {

    //Received the day of the following month in which it expiration date is set
    public static String dateInDaysWithServerFormat(int days) {
        Log.log(Level.FINE, "Starts: Turns days in date with server response format");
        int day = days;
        int year = todayYear();
        int month = todayMonth();
        int dayToday = todayDay();
        if (month == 12) { //Jump to next year
            year++;
        }
        //By default, expiration date is one week later. In the last week of the month
        //the expiration date in the following month will jump to the next month
        if (daysOfMonth(todayMonth(), todayYear()) - dayToday < 7) {
            month++;
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, day);
        Log.log(Level.FINE, "Date to format: " + gregorianCalendar.getTime());
        String dateFormat = gregorianCalendar.get(Calendar.YEAR)
                + "-" + formatInt(gregorianCalendar.get(Calendar.MONTH) + 1)
                + "-" + formatInt(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
        Log.log(Level.FINE, "Date formatted: " + dateFormat);
        return dateFormat;
    }

    //Builds the string with the date displayed in the app
    public static String displayedDate(String day) {
        String year = Integer.toString(DateUtils.todayYear()).substring(2);
        String month = Integer.toString(DateUtils.todayMonth() + 1);
        //String dayToday = Integer.toString(DateUtils.todayDay());
        Log.log(Level.FINE, "Day: " + day + " Month: " + month + " Year: " + year);
        if (month.equals("12")) { //Jump to next year
            year = String.valueOf(Integer.parseInt(year) + 1);
            month = "1";
        }
        //if (DateUtils.daysOfMonth(DateUtils.todayMonth(), DateUtils.todayYear()) - Integer.parseInt(day) < 7) {
        //    month = Integer.toString(DateUtils.todayMonth() + 2);
        //} else {
        //    month = Integer.toString(DateUtils.todayMonth() + 1);
        //}
        return day + "/" + month + "/" + year;
    }

    private static String formatInt(int dateNumber) {
        String day;
        if (dateNumber < 10) {
            day = "0" + dateNumber;
        } else {
            day = String.valueOf(dateNumber);
        }
        return day;
    }

    public static int daysOfMonth(int month, int year) {
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: {
                return 31;
            }
            case 4:
            case 6:
            case 9:
            case 11: {
                return 30;
            }
            case 2: { //leap year
                if (year % 4 == 0) {
                    return 29;
                } else {
                    return 28;
                }
            }
            default:
                return 0;
        }
    }

    public static int todayDay() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        return gregorianCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int todayMonth() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        return gregorianCalendar.get(Calendar.MONTH) + 1;
    }

    public static int todayYear() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        return gregorianCalendar.get(Calendar.YEAR);
    }

}
