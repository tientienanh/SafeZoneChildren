package com.example.activity.safezonechildrent;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by Tien on 09-Jan-16.
 */
public class QueryByUser {
    void queryByuser( String tableName,String keyUser, String valueUser, final QueryByUserCallBack callBack) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(tableName);
        query.whereEqualTo(keyUser, valueUser);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (callBack != null) {
                    callBack.queryByUserSuccess(parseObject);
                }
            }
        });
    }

    interface QueryByUserCallBack {
        void queryByUserSuccess(ParseObject parseObject);
    }
}
