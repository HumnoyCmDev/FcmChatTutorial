package com.humnoydeveloper.fcmchattutorial.util;

import android.content.Context;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.humnoydeveloper.fcmchattutorial.R;
/**
 * Created by humnoy on 10/3/59.
 */
public class Utils {

    private static final int TIME_HOURS_24 = 24 * 60 * 60 * 1000;
    private static final SimpleDateFormat DAY_OF_WEEK = new SimpleDateFormat("EEE, LLL dd,", Locale.US);

    private Utils() {
    }

    public static String formatTimeDay(Context context, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        long todayMidnight = cal.getTimeInMillis();
        long yesterdayMidnight = todayMidnight - TIME_HOURS_24;
        long weekAgoMidnight = todayMidnight - TIME_HOURS_24 * 7;

        String timeBarDayText;
        if (date.getTime() > todayMidnight) {
            timeBarDayText = context.getString(R.string.time_today);
        } else if (date.getTime() > yesterdayMidnight) {
            timeBarDayText = context.getString(R.string.time_yesterday);
        } else if (date.getTime() > weekAgoMidnight) {
            cal.setTime(date);
            timeBarDayText = context.getResources().getStringArray(R.array.time_days_of_week)[cal.get(Calendar.DAY_OF_WEEK) - 1];
        } else {
            timeBarDayText = DAY_OF_WEEK.format(date);
        }
        return timeBarDayText;
    }

    public static <T extends View> T find(View view, int id)
    {
        return (T) view.findViewById(id);
    }
}
