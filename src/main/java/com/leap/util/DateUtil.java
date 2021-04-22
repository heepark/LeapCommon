package com.leap.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil
{

    public DateUtil()
    {
    }

    public static String dateToString(Date date, String pattern)
    {
        return dateToString(date, pattern, null);
    }

    public static String dateToString(Date date, String pattern, TimeZone timeZone)
    {
        if(date != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            if(timeZone != null)
                sdf.setTimeZone(timeZone);
            return sdf.format(date);
        } else
        {
            return "";
        }
    }

    public static Date stringToDate(String dateString)
    {
        return stringToDate(dateString, (TimeZone)null);
    }

    public static Date stringToDate(String dateString, TimeZone timeZone)
    {
        if(dateString == null)
            return null;
        if(dateString.length() == 10)
            return stringToDate((new StringBuilder()).append(dateString.substring(0, 4)).append(dateString.substring(5, 7)).append(dateString.substring(8)).toString(), "yyyyMMdd", timeZone);
        if(dateString.length() == 8)
            return stringToDate(dateString, "yyyyMMdd", timeZone);
        if(dateString.length() == 6)
            return stringToDate(dateString, "yyMMdd", timeZone);
        else
            return null;
    }

    public static Date stringToDate(String dateString, String format)
    {
        return stringToDate(dateString, format, (TimeZone)null);
    }

    public static Date stringToDate(String dateString, String format, TimeZone timeZone)
    {
        if(dateString == null)
            return null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            if(timeZone != null)
                sdf.setTimeZone(timeZone);
            return sdf.parse(dateString);
        }
        catch(ParseException pe)
        {
            pe.printStackTrace();
        }
        return null;
    }

    public static String dateStringToString(String dateString, String oldFormat, String newFormat)
    {
        return dateStringToString(dateString, oldFormat, newFormat, (TimeZone)null);
    }

    public static String dateStringToString(String dateString, String oldFormat, String newFormat, TimeZone timeZone)
    {
        try
        {
            Date date = (new SimpleDateFormat(oldFormat)).parse(dateString);
            SimpleDateFormat sdf = new SimpleDateFormat(newFormat);
            if(timeZone != null)
                sdf.setTimeZone(timeZone);
            return sdf.format(date);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return dateString;
    }

    public static Timestamp getStartTimestamp(Date date)
    {
        return getStartTimestamp(date, (TimeZone)null);
    }

    public static Timestamp getStartTimestamp(Date date, TimeZone timeZone)
    {
        Calendar calendar = Calendar.getInstance();
        if(timeZone != null)
            calendar.setTimeZone(timeZone);
        calendar.setTime(date);
        setStartTime(calendar);
        return new Timestamp(calendar.getTime().getTime());
    }

    public static Timestamp getStartTimestamp(int year, int month, int day)
    {
        return getStartTimestamp(year, month, day, (TimeZone)null);
    }

    public static Timestamp getStartTimestamp(int year, int month, int day, TimeZone timeZone)
    {
        Calendar calendar = Calendar.getInstance();
        if(timeZone != null)
            calendar.setTimeZone(timeZone);
        calendar.set(1, year);
        calendar.set(2, month - 1);
        calendar.set(5, day);
        setStartTime(calendar);
        return new Timestamp(calendar.getTime().getTime());
    }

    public static Calendar setStartTime(Calendar calendar)
    {
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar;
    }

    public static Timestamp getEndTimestamp(Date date)
    {
        return getEndTimestamp(date, (TimeZone)null);
    }

    public static Timestamp getEndTimestamp(Date date, TimeZone timeZone)
    {
        Calendar calendar = Calendar.getInstance();
        if(timeZone != null)
            calendar.setTimeZone(timeZone);
        calendar.setTime(date);
        setEndTime(calendar);
        return new Timestamp(calendar.getTime().getTime());
    }

    public static Timestamp getEndTimestamp(int year, int month, int day)
    {
        return getEndTimestamp(year, month, day, (TimeZone)null);
    }

    public static Timestamp getEndTimestamp(int year, int month, int day, TimeZone timeZone)
    {
        Calendar calendar = Calendar.getInstance();
        if(timeZone != null)
            calendar.setTimeZone(timeZone);
        calendar.set(1, year);
        calendar.set(2, month - 1);
        calendar.set(5, day);
        setEndTime(calendar);
        return new Timestamp(calendar.getTime().getTime());
    }

    public static Calendar setEndTime(Calendar calendar)
    {
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.set(14, 999);
        return calendar;
    }
}
