package e2e.support.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;

import e2e.support.log.Log;

public class DateUtils {

    public static String dateInDaysWithServerFormat(int days, String timestamp) {
        Log.log(Level.FINE, "Starts: Turns days in date with server response format");
        LocalDate date = LocalDate.now().plusMonths(1).withDayOfMonth(days);
        String dateFormat = date.getYear()
                + "-" + String.format("%02d", date.getMonthValue())
                + "-" + String.format("%02d", date.getDayOfMonth());
        String dateToTz = dateFormat + "T" + timestamp;
        Log.log(Level.FINE, "Date formatted: " + dateToTz);
        return getCorrectTZ(dateToTz);
    }

    public static String displayedDate(String day) {
        ZoneId zone = ZoneId.of("UTC");
        ZonedDateTime nowUtc = ZonedDateTime.now(zone);
        LocalDate today = nowUtc.toLocalDate();
        LocalDate targetDate = today.plusMonths(1)
                .withDayOfMonth(Integer.parseInt(day));

        String formattedDay = String.format("%02d", targetDate.getDayOfMonth());
        String formattedMonth = String.format("%02d", targetDate.getMonthValue());
        String year = String.format("%04d", targetDate.getYear());

        Log.log(Level.FINE, "Day: " + formattedDay + " Month: " + formattedMonth + " Year: " + year);
        return formattedDay + "/" + formattedMonth + "/" + year;
    }

    public static String getCorrectTZ(String date) {
        ZonedDateTime localDateTime = parseToInstantUTC(date).atZone(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateFormat = localDateTime.format(formatter);
        Log.log(Level.FINE, "From " + date  + " to " + dateFormat);
        return dateFormat;
    }

    public static String convertDate(String dateToTransform) {
        LocalDate date = LocalDate.parse(dateToTransform);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }

    public static Instant parseToInstantUTC(String date) {
        String dateUTC = date.replace(" ", "T");
        if (!dateUTC.endsWith("Z")) {
            dateUTC += "Z";
        }
        return Instant.parse(dateUTC);
    }

    public static int todayDay() {
        return LocalDate.now().getDayOfMonth();
    }

    public static int todayMonth() {
        return LocalDate.now().getMonthValue();
    }

    public static int todayYear() {
        return LocalDate.now().getYear();
    }

    public static String daysToUTCForExpiration(String day) {
        int targetDay = Integer.parseInt(day.trim());
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime nextMonthDate = now
                .plusMonths(1)
                .withDayOfMonth(targetDay)
                .truncatedTo(ChronoUnit.DAYS);
        return DateTimeFormatter.ISO_INSTANT.format(nextMonthDate.toInstant());
    }
}
