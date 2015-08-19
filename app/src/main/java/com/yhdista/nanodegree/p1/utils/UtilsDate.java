package com.yhdista.nanodegree.p1.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by VlachJan on 13.8.2015.
 */
public class UtilsDate {





    /**
     * Basic method to change Date format from one String format to another String format
     *
     * @param inputDate     we want to change
     * @param inputPattern  input String format like "yyyy-MM-dd HH:mm:ss"
     * @param outputPattern output String format like "dd.MM.yyyy HH:mm:ss"
     * @return date in new format
     * @throws ParseException
     */
    public static String parseDate(String inputDate, String inputPattern, String outputPattern) throws ParseException {
        String outputDate = null;
        if (!inputDate.isEmpty()) {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
            outputDate = outputFormat.format(inputFormat.parse(inputDate));
        }
        return outputDate;
    }

    public static String parseDate(String inputDate) throws ParseException {
        String outputDate = null;
        if (!inputDate.isEmpty()) {
            String inputPattern = "yyyy-MM-dd HH:mm:ss";
            String outputPattern = "dd.MM.yyyy HH:mm:ss";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
            outputDate = outputFormat.format(inputFormat.parse(inputDate));
        }
        return outputDate;
    }

    public static Date parseDate_YYYY_MM_DD(String stringDate) throws ParseException {
        Date date = null;
        if (!stringDate.isEmpty()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(stringDate);
        }
        return date;
    }



    /**
     * Get time in millis as a Long
     *
     * @param date input time
     * @return time as a Long
     */
    public static long getTimeInMillis(Date date) {
        return date.getTime();
    }

    /**
     * Geta Date object from Long number in millis
     *
     * @param millis input time
     * @return tome as a Date
     */
    public static Date getDateFromMillis(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat();
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static int getTimeAsYear(Date date) {
        String year = new SimpleDateFormat("yyyy", Locale.ENGLISH).format(date);
        return Integer.valueOf(year);
    }


}
