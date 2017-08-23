package com.nmerris.roboresumedb;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    public static String getTodaysDateString() {
        String todaysDateString = "";
        Date today = new Date();
        SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMM");
        todaysDateString += (dateFormatMonth.format(today) + " ");
        SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd");
        todaysDateString += (dateFormatDay.format(today) + ", ");
        SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
        todaysDateString += (dateFormatYear.format(today));
        return todaysDateString;
    }


    // returns 'Present' if date is null, otherwise returns MMM dd, yyyy date string
    public static String getMonthDayYearFromDate(Date date) {
        String dateString = "";

        if(date == null) {
            SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMM");
            dateString += (dateFormatMonth.format(date) + " ");
            SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd");
            dateString += (dateFormatDay.format(date) + ", ");
            SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
            dateString += (dateFormatYear.format(date));
        }
        else {
            dateString += "Present";
        }

        return dateString;
    }
    

}
