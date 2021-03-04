package bankdatecheck;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class BankDateCheck {

  static List<String> weekends = Arrays.asList("saturday", "sunday");
  static List<String> holidays_2021 = Arrays.asList("2021-01-01", "2021-01-18", "2021-02-15",
      "2021-04-02", "2021-04-19", "2021-05-31", "2021-07-05", "2021-09-06", "2021-10-11",
      "2021-11-11", "2021-11-25", "2021-11-26", "2021-12-24");
  static LocalDate date;
  static String day;
  static LocalTime time;
  static LocalTime openingTime = LocalTime.parse("09:00:00");
  static LocalTime closingTime = LocalTime.parse("16:00:00");

  public static void main(String[] args) {
    System.out.println("hello world");
    getTime();
    System.out.println(date);
    System.out.println(time);
    System.out.println(day);
    String blah = date.getDayOfWeek().toString().toLowerCase();
    System.out.println(date.getDayOfWeek());
    day = "Sunday";
    time = LocalTime.parse("10:00:00");
    date = LocalDate.of(2021, 12, 23);
    checkWeekend();
    checkTime();
    checkHolidays();
    System.out.println(date);
    System.out.println(time);
    System.out.println(day);
    System.out.println(nextOpenDateTime());
  }

  public static void getTime() {
    date = LocalDate.now();
    time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
    day = (new SimpleDateFormat("EEEE")).format(new Date(System.currentTimeMillis()));
  }

  public static void checkWeekend() {
    if(day.equals("Saturday")) {
      date = date.plusDays(2L);
      updateTime();
    } else if(day.equals("Sunday")) {
      date = date.plusDays(1L);
      updateTime();
    }
  }

  public static void checkTime() {
    if(time.isAfter(closingTime)){
      date = date.plusDays(1L);
      updateTime();
    } else if(time.isBefore(openingTime)){
      updateTime();
    }
  }

  public static void checkHolidays() {
    while(holidays_2021.contains(date.toString())){
      date = date.plusDays(1L);
      updateTime();
    }
  }

  public static void updateTime() {
    time = openingTime;
  }

  public static String nextOpenDateTime() {
    String nextDate = date.toString();
    String nextTime = time.toString();
    return String.format("{banking_date: %s %s}", nextDate, nextTime);
  }

}
