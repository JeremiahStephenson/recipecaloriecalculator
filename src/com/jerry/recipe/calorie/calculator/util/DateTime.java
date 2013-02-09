package com.jerry.recipe.calorie.calculator.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class DateTime {
    public interface DateTimeSetListener {
        void onDateTimeSet(Calendar calendar, String calendarText);
    }

    private static SimpleDateFormat sInternalDateTimeFormat;
    private static DateFormat sUserDateFormat;
    private static DateFormat sUserTimeFormat;
    private static DateFormat sEventDateFormat;

    static {
        sInternalDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sEventDateFormat = new SimpleDateFormat("MM - dd - yy");
    }

    public static SimpleDateFormat getInternalDateTimeFormat() {
        return sInternalDateTimeFormat;
    }

    public static DateFormat getUserDateFormat(Context context) {
        if (sUserDateFormat == null) {
            String format = Settings.System.getString(context.getContentResolver(), Settings.System.DATE_FORMAT);
            if (TextUtils.isEmpty(format)) {
                sUserDateFormat = android.text.format.DateFormat.getDateFormat(context);
            } else {
                sUserDateFormat = new SimpleDateFormat(format);
            }
        }

        return sUserDateFormat;
    }

    public static DateFormat getUserTimeFormat(Context context) {
        if (sUserTimeFormat == null) {
            sUserTimeFormat = android.text.format.DateFormat.getTimeFormat(context);
        }

        return sUserTimeFormat;
    }

    public static void showDatePicker(final Context context, final Button datePicker, final DateTimeSetListener timeSetListener) {
        Calendar calendar = parseDateTime(getUserDateFormat(context), datePicker.getText().toString());

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String calendarText = getUserDateFormat(context).format(calendar.getTime());

                datePicker.setText(calendarText);

                if (timeSetListener != null) {
                    timeSetListener.onDateTimeSet(calendar, calendarText);
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public static void showTimePicker(final Context context, final Button timePicker, final DateTimeSetListener timeSetListener) {
        Calendar calendar = parseDateTime(getUserTimeFormat(context), timePicker.getText().toString());

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                String calendarText = getUserTimeFormat(context).format(calendar.getTime());

                timePicker.setText(calendarText);

                if (timeSetListener != null) {
                    timeSetListener.onDateTimeSet(calendar, calendarText);
                }
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), android.text.format.DateFormat.is24HourFormat(context));

        timePickerDialog.show();
    }

    public static Calendar parseDateTime(DateFormat dateFormat, String date) {
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;
    }

    public static String calendarToString(Calendar calendar) {
        if (calendar == null)
            return "";
        if (calendar.get(Calendar.YEAR) < 2000)
            return "0000-00-00 00:00:00";
        return sInternalDateTimeFormat.format(calendar.getTime());
    }

    public static String calendarToEventDateString(Calendar calendar) {
        if (calendar == null)
            return "";
        if (calendar.get(Calendar.YEAR) < 2000)
            return "00 - 00 - 00";
        return sEventDateFormat.format(calendar.getTime());
    }

    public static String calendarToDayString(Calendar calendar, Context context) {
        if (calendar == null)
            return "";
        if (calendar.get(Calendar.YEAR) < 2000)
            return "00 - 00 - 00";
        return getUserDateFormat(context).format(calendar.getTime());
    }

    public static String calendarToDateTimeString(Calendar calendar, Context context) {
        String date = getUserDateFormat(context).format(calendar.getTime());
        String time = getUserTimeFormat(context).format(calendar.getTime());
        return date + " " + time;
    }

    public static Calendar stringToCalendar(String string) {
        if (string == null)
            return null;
        if (string.equals(""))
            return null;
        Calendar calendar = Calendar.getInstance();
        Date date;
        try {
            date = sInternalDateTimeFormat.parse(string);
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String calendarToTimeString(Calendar calendar, Context context) {
        if (calendar == null)
            return "";
        if (calendar.get(Calendar.YEAR) < 2000)
            return "00:00";
        return getUserTimeFormat(context).format(calendar.getTime());
    }

    public static Calendar copyDate(Calendar date) {
        if (date == null)
            return null;
        Calendar newDate = stringToCalendar(calendarToString(date));
        return newDate;
    }

    public static int roundUp(int value, int roundTo) {
        int mod = value % roundTo;
        if (mod == 0)
            return value;
        return (roundTo - mod) + value;
    }

    public static String getTimeAgoString(String dateTime) {
        Date created = null;
        String timeAgoString = null;

        try {
            created = DateTime.getInternalDateTimeFormat().parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (created != null) {
            Date now = Calendar.getInstance().getTime();
            long differenceInSeconds = (now.getTime() - created.getTime()) / 1000;

            long seconds = (differenceInSeconds >= 60 ? differenceInSeconds % 60 : differenceInSeconds);
            long minutes = (differenceInSeconds = (differenceInSeconds / 60)) >= 60 ? differenceInSeconds % 60 : differenceInSeconds;
            long hours = (differenceInSeconds = (differenceInSeconds / 60)) >= 24 ? differenceInSeconds % 24 : differenceInSeconds;
            long days = (differenceInSeconds = (differenceInSeconds / 24));

            if (days > 0) {
                timeAgoString = String.format("%d day%s ago", days, days > 1 ? "s" : "");
            } else if (hours > 0) {
                timeAgoString = String.format("%d hour%s ago", hours, hours > 1 ? "s" : "");
            } else if (minutes > 0) {
                timeAgoString = String.format("%d minute%s ago", minutes, minutes > 1 ? "s" : "");
            } else if (seconds > 0) {
                timeAgoString = String.format("%d second%s ago", seconds, seconds > 1 ? "s" : "");
            } else {
                timeAgoString = "right now";
            }
        }

        return timeAgoString;
    }
}
