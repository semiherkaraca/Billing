package com.billing.util;

import com.billing.enums.DateFormat;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateUtils {

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(DateFormat.DATE_FORMAT_DD_MM_YYYY.getDateFormat());
        return formatter.format(new Date());
    }

    public static String getCurrentDate(DateFormat dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat.getDateFormat());
        return formatter.format(new Date());
    }

    public static Date getSimpleCurrentDate() {
        return convertDate(getCurrentDate(DateFormat.DATE_FORMAT_DD_MM_YYYY_HH_MM_SS), DateFormat.DATE_FORMAT_DD_MM_YYYY_HH_MM);
    }

    public static String formatDate(Date givenDate, DateFormat dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat.getDateFormat());
        return simpleDateFormat.format(givenDate);
    }

    public static String getFormatedDate(DateFormat dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat.getDateFormat());
        return simpleDateFormat.format(new Date());
    }

    public static Date convertDate(String givenDate, DateFormat dateFormat) {
        Date convertedDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat.getDateFormat());
        try {
            convertedDate = simpleDateFormat.parse(givenDate);
        } catch (ParseException e) {
            log.error("DateUtils - convertDate, GivenDate: {}, Given DateFormat: {}", givenDate, dateFormat);
            throw new RuntimeException(e);
        }
        return convertedDate;
    }

    public static Date addYear(Date giveDate, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(giveDate);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    public static Date addMonth(Date giveDate, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(giveDate);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static Date addDay(Date giveDate, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(giveDate);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }
}
