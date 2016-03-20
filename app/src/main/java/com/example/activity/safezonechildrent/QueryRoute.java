package com.example.activity.safezonechildrent;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tien on 04-Dec-15.
 */
public class QueryRoute {

    public static final String TIME_FROM = "timeFrom";
    public static final String TIME_TO = "timeTo";
    public static final String LOCATION = "location";
    public static final String RADIUS = "rsdius";
    public static final String D_LATITUTE = "dLatitute";
    public static final String D_LONGITUTE = "dLongitute";

    final List<LatLng> latLngs = new ArrayList<>();

    void queryRoute(final QueryRouteCallBack callBack) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("CurrentLocation");
        final List<Route> routes = new ArrayList<Route>();
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (int i = 0; i < list.size(); i++) {
                    LatLng currentLocation = new LatLng(list.get(i).getDouble("lat"),list.get(i).getDouble("long"));
                    latLngs.add(currentLocation);
                }

                if (callBack != null) {
                    callBack.queryRouteSuccess(latLngs);
                }
            }
        });
    }

    interface QueryRouteCallBack {
        void queryRouteSuccess(List<LatLng> latLngs);
    }
}
