package com.example.activity.safezonechildrent;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Tien on 04-Apr-16.
 */
public class GetListChild {
    public static GetListChild instance = new GetListChild();

    public static GetListChild getInstance(){
        return instance;
    }

    List<Child> childList = new ArrayList<>();
    GetlistCallback mGetListCallback;
    public List<Child> getListChild(Context context){

        HashMap<String, String> hashMapListChild = new HashMap<>();
        hashMapListChild.put("PUT","5");
        hashMapListChild.put("parent_user", LoginSonActivity.PARENT_USER);
        SocketAsynctask socketAsynctask = new SocketAsynctask(context);
        socketAsynctask.execute(hashMapListChild);
        socketAsynctask.socketResponse = new SocketAsynctask.SocketResponse() {
            @Override
            public void response(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("Children");
                    childList.clear();
                    for (int i = 0;i < jsonArray.length();i++) {
                        JSONObject jsonChild = jsonArray.getJSONObject(i);
                        String pr_user = jsonChild.getString(Child.PARENT_USER);
                        String fullname = jsonChild.getString("full_name");
                        String nickname = jsonChild.getString("nick_name");
                        int age = jsonChild.getInt("age");
                        int gender = jsonChild.getInt("gender");
                        int grade = jsonChild.getInt("grade");
                        int id = jsonChild.getInt("id");
                        ///////////NOTE************ add image
//                        String image = jsonChild.getString(Child.IMAGE);
                        Child c = new Child(id, fullname, nickname, age, grade, gender, null);
                        childList.add(c);
                    }


                    mGetListCallback.getListCallback(childList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        return childList;
    }

interface GetlistCallback {
        void getListCallback(List<Child> childList);
    }

}
