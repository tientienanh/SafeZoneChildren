package com.example.activity.safezonechildrent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class LoginSonActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtParentUSer_in_child, edtParentPass_in_child;
    Button btnLoginChild;
    public static final String CHILD_NAME = "child_name";
    public static final String PARENT_NAME = "parent_name";
    public static final String PARENT_PASS = "parent_pass";
    public static final String CHILD_LATITUDE = "child_latitude";
    public static final String CHILD_LONGITUDE = "child_longitude";
    public static String parentUser;
    public static String childUser;
    public static String prentPass;
    public static String PARENT_USER = "";

    public static final String NAME = "name";
    public static final String USERNAME = "username";
    public static final String PASS = "pass";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_son);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        edtChildUser = (EditText) findViewById(R.id.edtChildUser);
        edtParentUSer_in_child = (EditText) findViewById(R.id.edtParentUsername_in_Child);
        edtParentPass_in_child = (EditText) findViewById(R.id.edtParentPass_in_Child);
        btnLoginChild = (Button) findViewById(R.id.btnLoginChild);

        btnLoginChild.setOnClickListener(this);
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    String parentUser_in_child, parentPass_in_child;
    boolean isChildUserExist = false;

    String resultStr = "";
    @Override
    public void onClick(View view) {
        if (isNetworkAvailable(LoginSonActivity.this) == false) {
            diaLogInternet();
        } else {
//            child_user = edtChildUser.getText().toString();
            parentUser_in_child = edtParentUSer_in_child.getText().toString();
            parentPass_in_child = edtParentPass_in_child.getText().toString();

            if (parentUser_in_child.equals("") || parentPass_in_child.equals("")) {
                Toast.makeText(getBaseContext(), "Enter all data, please!", Toast.LENGTH_SHORT).show();
            } else {
                final HashMap<String, String> hashMapLogin = new HashMap<>();
                hashMapLogin.put("PUT", "3");
                hashMapLogin.put("user_parent", parentUser_in_child);
                hashMapLogin.put("pass_parent", parentPass_in_child);
                final ChildThread childThread = CreateThread.getInstance().childThread;
                childThread.mCallback = new ChildThread.Callback() {
                    @Override
                    public void onReceivedData(String json) {
                        resultStr = json;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(resultStr);
                                    String result = jsonObject.getString("RESULT");
                                    if (result.equals("LOGIN_OK")){
                                        Toast.makeText(LoginSonActivity.this, "login success!", Toast.LENGTH_SHORT).show();
                                        PARENT_USER = parentUser_in_child;
                                        Intent intentLoginSon = new Intent(LoginSonActivity.this, ShowChildrenActivity.class);
                                        startActivity(intentLoginSon);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void onConnected(boolean isSuccess) {
                        if (isSuccess == true) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        childThread.oos.writeObject(hashMapLogin);
                                        Log.d("LOGIN", "write object");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    }
                };
            }
        }
    }

    public void diaLogInternet() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("Check the Internet!");
        dialog.setCancelable(false).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }


    public void startService(View view) {
        Intent intent = new Intent(getBaseContext(), MyService.class);
        startService(intent);

    }


    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MyService.class));
    }
}
