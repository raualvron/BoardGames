package com.example.boardgames.Classes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatHelper {
    Date date;
    public DateFormatHelper(String dateFrom, String formatP) {
        SimpleDateFormat format = new SimpleDateFormat(formatP);
        try {
            this.date = format.parse(dateFrom);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDateFormatted() {
        return DateFormat.getDateInstance(ConstantsHelper.DATE_FORMAT_TO).format(this.date);
    }


}
