package jamiesmyth.mobilecomputingapplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CurrentDate {

    public static String getCurrentDate(String date)
    {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        date = df.format(Calendar.getInstance().getTime());

        return date;
    }


}
