package ca.bcit.czone;

import android.location.Location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Zone {
    private double lat;
    private double lng;
    private String reportedDate;

    public Zone() {}

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public Date getReportedDate() throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(reportedDate);
    }

    double getRadius() {
        try {
            long diffInDays = getElapsedDays();
            if (diffInDays <= 7)
                return 200;
            else if (diffInDays <= 14)
                return 175;
            else if (diffInDays <= 21)
                return 150;
            return 125;
        } catch (Exception e) {
            return 0;
        }
    }

    public long getElapsedDays() throws ParseException {
        Date reportedDate = getReportedDate();
        long diffInMillies = Math.abs(new Date().getTime() - reportedDate.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    int getColor() {
        int red = 0x30ff0000;
        int orange = 0x30ffa500;
        int yellow = 0x30ffff00;
        int gray = 0x30808080;

        try {
            long diffInDays = getElapsedDays();

            if (diffInDays <= 7)
                return red;
            else if (diffInDays <= 14)
                return orange;
            else if (diffInDays <= 21)
                return yellow;
            return gray;
        } catch (Exception e) {
            return gray;
        }
    }

    boolean containsLocation(Location location) {
        float[] results = new float[1];
        Location.distanceBetween(location.getLatitude(), location.getLongitude(), lat, lng, results);
        return results[0] < getRadius();
    }
}
