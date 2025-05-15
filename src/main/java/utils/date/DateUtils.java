package utils.date;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;

import utils.log.Log;

public class DateUtils {

    //Received the day of the following month in which it expiration date is set
    public static String dateInDaysWithServerFormat(int days, String timestamp) {
        Log.log(Level.FINE, "Starts: Turns days in date with server response format");
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
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, days);
        Log.log(Level.FINE, "Date to format: " + gregorianCalendar.getTime());
        String dateFormat = gregorianCalendar.get(Calendar.YEAR)
                + "-" + formatInt(gregorianCalendar.get(Calendar.MONTH) + 1)
                + "-" + formatInt(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
        String dateToTz = dateFormat + "T"+ timestamp;
        Log.log(Level.FINE, "Date formatted: " + dateToTz);
        return getCorrectTZ(dateToTz);
    }

    //Builds the string with the date displayed in the app
    public static String displayedDate(String day) {
        String year = Integer.toString(DateUtils.todayYear()).substring(2);
        String month = Integer.toString(DateUtils.todayMonth() + 1);
        Log.log(Level.FINE, "Day: " + day + " Month: " + month + " Year: " + year);
        if (month.equals("12")) { //Jump to next year
            year = String.valueOf(Integer.parseInt(year) + 1);
            month = "1";
        }
        return day + "/" + month + "/" + year;
    }

    //Builds the string with the current time, moved to UTC (used in shares)
    public static String getCorrectTZ(String date) {
        ZonedDateTime localDateTime = parseToInstantUTC(date).atZone(ZoneId.systemDefault());
        //Set as string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateFormat = localDateTime.format(formatter);
        Log.log(Level.FINE, "From " + date  + " to " + dateFormat);
        return dateFormat;
    }

    //Takes care of correct UTC format
    public static Instant parseToInstantUTC(String date) {
        String dateUTC = date.replace(" ", "T");
        if (!dateUTC.endsWith("Z")) {
            dateUTC += "Z";
        }
        return Instant.parse(dateUTC);
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
        return switch (month) {
            case 1, 3, 5 ,7, 8, 10, 12 -> 31;
            case 4, 6, 9, 11 -> 30;
            case 2 -> {
                if (isLeapYear(year)) {
                    yield 29;
                } else {
                    yield 28;
                }
            }
            default -> 0;
        };
    }

    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
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
