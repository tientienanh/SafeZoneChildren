package com.example.activity.safezonechildrent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;

import java.util.List;

public class LoginSonActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtChildUser, edtParentUSer_in_child, edtParentPass_in_child;
    Button btnLoginChild;
    public static final String CHILD_NAME = "child_name";
    public static final String PARENT_NAME = "parent_name";
    public static final String PARENT_PASS = "parent_pass";
    public static final String CHILD_LATITUDE = "child_latitude";
    public static final String CHILD_LONGITUDE = "child_longitude";
    public static String parentUser;
    public static String childUser;
    public static String prentPass;

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

        edtChildUser = (EditText) findViewById(R.id.edtChildUser);
        edtParentUSer_in_child = (EditText) findViewById(R.id.edtParentUsername_in_Child);
        edtParentPass_in_child = (EditText) findViewById(R.id.edtParentPass_in_Child);
        btnLoginChild = (Button) findViewById(R.id.btnLoginChild);

        btnLoginChild.setOnClickListener(this);
    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    String child_user, parentUser_in_child, parentPass_in_child;
    boolean isChildUserExist = false;
    @Override
    public void onClick(View view) {
        if (isNetworkAvailable(LoginSonActivity.this) == false) {
            diaLogInternet();
        } else {
            child_user = edtChildUser.getText().toString();
            parentUser_in_child = edtParentUSer_in_child.getText().toString();
            parentPass_in_child = edtParentPass_in_child.getText().toString();

            if (child_user.equals("") || parentUser_in_child.equals("") || parentPass_in_child.equals("")) {
                Toast.makeText(getBaseContext(), "Enter all data, please!", Toast.LENGTH_SHORT).show();
            } else {
                // kiem tra username, pass cha me  co dung k?
                QueryByUser queryByUser = new QueryByUser();
                queryByUser.queryByuser("ParentAccount",USERNAME, parentUser_in_child, new QueryByUser.QueryByUserCallBack() {
                    @Override
                    public void queryByUserSuccess(ParseObject parseObject) {
                        String parentName = parseObject.getString(USERNAME);
                        final String parentPass = parseObject.getString(PASS);
                        if (parentName.equals(parentUser_in_child) == false || parentPass.equals(parentPass_in_child) == false) {
                            Toast.makeText(getBaseContext(), "Parent's username or password is not correct!", Toast.LENGTH_SHORT).show();
                        } else { // neu user pass cha me dung thi xet xem co bi trung ten khong
                            QueryAccount queryAccount = new QueryAccount();
                            queryAccount.queryAccount("Children", "parent_name", parentUser_in_child, new QueryAccount.QueryRouteCallBack() {
                                @Override
                                public void queryRouteSuccess(List<ParseObject> parseObjects) {
                                    if (parseObjects == null) {
                                        return;
                                    } else {
                                        for (int i = 0; i < parseObjects.size(); i++) {
                                            if (parseObjects.get(i).getString(CHILD_NAME).equals(child_user)) {
                                                isChildUserExist = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (isChildUserExist == false) { // neu child user khong bi trung thi dua len Parse va Login
                                        parentUser = parentUser_in_child;
                                        prentPass = parentPass_in_child;
                                        childUser = child_user;
                                        ParseObject children = new ParseObject("Children");
                                        children.put(CHILD_NAME, child_user);
                                        children.put(PARENT_NAME, parentUser_in_child);
                                        children.put(PARENT_PASS, parentPass_in_child);
                                        children.saveInBackground();
                                        // Login
                                        Toast.makeText(getBaseContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                                        // chay ngam
                                        startService(getCurrentFocus());
                                        // chay qua activity moi
                                        Intent intentSon = new Intent(LoginSonActivity.this, ChildenActivity.class);
                                        startActivity(intentSon);

//                                        MainActivity.isLoginSon = true;

                                        // set lai editText null
                                        edtChildUser.setText("");
                                        edtParentPass_in_child.setText("");
                                        edtParentUSer_in_child.setText("");
                                    } else {
                                        Toast.makeText(LoginSonActivity.this, "Child's username is exist!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

            }
        }
    }

    public void diaLogInternet(){
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
