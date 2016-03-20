package com.example.activity.safezonechildrent;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tien on 23-Dec-15.
 */


public class UpdateToParse {
    List<ParseObject> childList = new ArrayList<>();
    int posision;
    public final void updateLocation(final String table, final double latitude, final double longtitude, final Context context) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(table);
        // lay list du lieu cha me ra truoc
        query.whereEqualTo(LoginSonActivity.PARENT_NAME, LoginSonActivity.parentUser);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> listChild, ParseException e) {
                // list nay chua tat ca cac du lieu ve cac dua con cua cha me

                if (e != null) {
                    Toast.makeText(context, "Error!!", Toast.LENGTH_SHORT).show();
                } else {// khong co loi
                    // tim kiem dua con dang xet
                    for (int i = 0; i < listChild.size(); i++) {
                        if (listChild.get(i).getString(LoginSonActivity.CHILD_NAME).equals(LoginSonActivity.childUser)) {
                            childList.add(listChild.get(i));
                            posision = i;
                        }
                    }

                    // neu childList rong
                    if (childList.size() <= 0) { // chua co du lieu tren Parse.com -> tao moi
                        ParseObject object = new ParseObject(table);
                        object.put(LoginSonActivity.CHILD_LATITUDE, latitude);
                        object.put(LoginSonActivity.CHILD_LONGITUDE, longtitude);
                        object.put(LoginSonActivity.PARENT_NAME, LoginSonActivity.parentUser);
                        object.put(LoginSonActivity.PARENT_PASS, LoginSonActivity.prentPass);
                        object.put(LoginSonActivity.CHILD_NAME, LoginSonActivity.childUser);
                        object.saveInBackground();
                    } else {// da co du lieu
                        ParseObject object = listChild.get(posision);
                        object.put(LoginSonActivity.CHILD_LATITUDE, latitude);
                        object.put(LoginSonActivity.CHILD_LONGITUDE, longtitude);
                        object.put(LoginSonActivity.PARENT_NAME, LoginSonActivity.parentUser);
                        object.put(LoginSonActivity.PARENT_PASS, LoginSonActivity.prentPass);
                        object.put(LoginSonActivity.CHILD_NAME, LoginSonActivity.childUser);
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.d("Update", "update thanh cong");
                                } else {
                                    Log.d("Update", "update  KHONG thanh cong");
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public final void updateLocation(final String table,final double latitude, final double longtitude, final Context context, final int position,
                                     final String timeFrom, final String timeTo, final String locationn, final String radius) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(table);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> listLocation, ParseException e) {
                if (e != null) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                } else {// khong co loi
                    if (listLocation.size() <= 0) { // chua co du lieu tren Parse.com -> tao moi
                        ParseObject location = new ParseObject(table);
                        location.put(QueryRoute.D_LATITUTE, latitude);
                        location.put(QueryRoute.D_LONGITUTE, longtitude);
                        location.put(QueryRoute.TIME_FROM, timeFrom);
                        location.put(QueryRoute.TIME_TO, timeTo);
                        location.put(QueryRoute.LOCATION, locationn);
                        location.put(QueryRoute.RADIUS, radius);
                        location.saveInBackground();
                    } else {// da co du lieu
                        ParseObject location = listLocation.get(position);
                        location.put(QueryRoute.D_LATITUTE, latitude);
                        location.put(QueryRoute.D_LONGITUTE, longtitude);
                        location.put(QueryRoute.TIME_FROM, timeFrom);
                        location.put(QueryRoute.TIME_TO, timeTo);
                        location.put(QueryRoute.LOCATION, locationn);
                        location.put(QueryRoute.RADIUS, radius);

                        location.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Log.d("Update", "update thanh cong");
                                } else
                                    Log.d("Update", "update  KHONG thanh cong");
                            }
                        });
                    }
                }
            }
        });
    }


}
