package com.example.activity.safezonechildrent;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzid.runOnUiThread;

/**
 * Created by Tien on 16-Nov-15.
 */
public class MyService extends Service implements LocationListener {
    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 1000 * 30;  // 30s

    private static final long TIME_DELAY = 2000;  // 2s
    private static final long TIME_PERIOD = 300000;  // 5p

    boolean isGPSEnabled;
    LocationManager locationManager;
    Location location;
    double lat, llong;
    Location bestLocation = null;
    public static Timer timer;
    MyTimerTask myTimerTask;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        timer = new Timer();
        myTimerTask = new MyTimerTask();  // lam viec cap nhat vi tri va put vi tri trong nay
        timer.schedule(myTimerTask, TIME_DELAY, TIME_PERIOD); // cu sau 5p  la lam lai viec nay

    }



    List<Route> routes;
    private int compareTime(Date time1, Date currentTime) {
        int result = 0;
        result = time1.compareTo(currentTime);
        return result;
    }

    HashMap<String, String> hashMapLocation = new HashMap<String, String>();
    String jsonStr = "";
    class MyTimerTask extends TimerTask {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Log.d("", "on Son Service");
                    // 1. lay vi tri GPS roi dua len Parse
                    getCurrentLocation();
                    // put lat, llong on MySQL
                    hashMapLocation.put("PUT", "1");
                    hashMapLocation.put("user_parent", LoginSonActivity.PARENT_USER);
                    hashMapLocation.put("username", ShowChildrenActivity.CHILD_USER);
                    hashMapLocation.put("latitude", String.valueOf(lat));
                    hashMapLocation.put("longitude", String.valueOf(llong));

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Calendar c = Calendar.getInstance();
                    String timeStr = simpleDateFormat.format(c.getTime());
                    hashMapLocation.put("time", timeStr);
                    final ChildThread childThread = CreateThread.getInstance().childThread;
                    if (childThread.clientSocket.isConnected() == true) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    childThread.oos.writeObject(hashMapLocation);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }).start();
                        childThread.mCallback = new ChildThread.Callback() {
                            @Override
                            public void onReceivedData(String json) {
                                jsonStr = json;
                                try {
                                    JSONObject jsonObject = new JSONObject(jsonStr);
                                    String result = jsonObject.getString("RESULT");
                                    if (result.equals("OK")) {
                                        Log.d("B", "insert location");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onConnected(boolean isSuccess) {

                            }
                        };
                    }
                }
            });
        }
    }

    void getCurrentLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled) {
            if (location == null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
                if (locationManager != null)
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    lat = location.getLatitude();
                    llong = location.getLongitude();
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("","on Destroy");
        timer.cancel();
        super.onDestroy();
    }


}
