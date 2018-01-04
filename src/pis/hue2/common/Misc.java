package pis.hue2.common;

import java.time.LocalTime;

public class Misc {

    public static String getTime() {
        LocalTime time = LocalTime.now();
        String hour = time.getHour() + "", minute = time.getMinute() + "";
        if (hour.length() == 1) hour = "0" + hour;
        if (minute.length() == 1) minute = "0" + minute;
        return hour + ":" + minute;
    }
}
