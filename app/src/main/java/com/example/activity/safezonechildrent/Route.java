package com.example.activity.safezonechildrent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tien on 01-Dec-15.
 */
public class Route {

    public static final String TIME_FROM = "timeFrom";
    public static final String TIME_TO = "timeTo";
    public static final String LOCATION = "location";  //name
    public static final String RADIUS = "radius";
    public static final String TABLE_ROUTE = "Route";
    public static final String ID = "id";
    public static final String D_LATITUTE = "dLatitute";
    public static final String D_LONGITUTE = "dLongitute";
    public static final String CHILDREN_NAME = "children_name";

    String timeFrom;
    String timeTo;
    String location; //name
    String radius;

    public String getChildrenName() {
        return childrenName;
    }

    public void setChildrenName(String childrenName) {
        this.childrenName = childrenName;
    }

    String childrenName;

    public String getdLatitute() {
        return dLatitute;
    }

    public void setdLatitute(String dLatitute) {
        this.dLatitute = dLatitute;
    }

    public String getdLongtitute() {
        return dLongtitute;
    }

    public void setdLongtitute(String dLongtitute) {
        this.dLongtitute = dLongtitute;
    }

    String dLatitute;
    String dLongtitute;




    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public static final Date convertStringToDate(String dateStr){
        Date d  = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d-MM-yyyy hh:mm");
        try {
            d = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }
}
