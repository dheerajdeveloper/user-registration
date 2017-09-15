package com.dheeraj.user.registration.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by dheeraj on 15/09/17.
 */
public class DateUtil {

    public static final String ISO_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final ThreadLocal<SimpleDateFormat> ISO_DATETIME_FORMAT = new ThreadLocal<SimpleDateFormat>() { // NOPMD
        // by
        // dheerajtyagi
        // on
        // 20/12/16
        // 6:05
        // PM
        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat dateFormat = new SimpleDateFormat(ISO_DATETIME_PATTERN);
            return dateFormat;
        }
    };
    public static final String DATE_PATTERN_SERVICE = "yyyy-MM-dd HH:mm:ss";


    public static String getTimeFromLastNMinute(int minute) {
        DateTimeZone indianZone = DateTimeZone.forID("Asia/Kolkata");
        DateTimeFormatter indianZoneFormatter = DateTimeFormat
                .forPattern(DATE_PATTERN_SERVICE)
                .withLocale(Locale.US)
                .withZone(indianZone);

        DateTime parsed = DateTime.now(indianZone).minusMinutes(minute);
        return indianZoneFormatter.print(parsed);
    }
}
