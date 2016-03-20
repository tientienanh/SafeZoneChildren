package com.example.activity.safezonechildrent;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by Tien on 08-Jan-16.
 */
public class QueryAccount {
    void queryAccount( String tableName, final QueryRouteCallBack callBack) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (callBack != null) {
                    callBack.queryRouteSuccess(list);
                }
            }
        });
    }

    void queryAccount( String tableName,String keyUser, String valueUser, final QueryRouteCallBack callBack) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
        query.whereEqualTo(keyUser, valueUser);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (callBack != null) {
                    callBack.queryRouteSuccess(list);
                }
            }
        });
    }



    interface QueryRouteCallBack {
        void queryRouteSuccess(List<ParseObject> parseObjects);
    }
}

