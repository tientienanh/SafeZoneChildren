package com.example.activity.safezonechildrent;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.Date;

public class ChildenActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tvPrUser, tvPrPass, tvLogin;
    EditText edtPrUser, edtPrPass;
    Button btnLogout;
    Button btn;
    LinearLayout ln;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_childen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvPrUser = (TextView) findViewById(R.id.tvPrUser);
        tvPrPass = (TextView) findViewById(R.id.tvPrPass);
        edtPrUser = (EditText) findViewById(R.id.edtPrUser);
        edtPrPass = (EditText) findViewById(R.id.edtPrPass);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        ln = (LinearLayout) findViewById(R.id.linear);

        Typeface tf1 = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mtcorsva.ttf");
        tvLogin.setTypeface(tf1);

        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
        updateTime();
//        setContentView(btn);

        btnLogout.setOnClickListener(this);

    }

    private void updateTime(){
        btn.setText(new Date().toString());
    }

    @Override
    public void onBackPressed() {
        btn.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_son, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout_son) {
           /* tvPrUser.setVisibility(View.VISIBLE);
            tvPrUser.setVisibility(View.VISIBLE);
            edtPrUser.setVisibility(View.VISIBLE);
            edtPrPass.setVisibility(View.VISIBLE);*/
            tvLogin.setText("Enter your parent's user and pass!");
            btn.setVisibility(View.GONE);
            ln.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    // Method to stop the service
    public void stopService(View view) {
        Log.d("", "stop son Service");
        stopService(new Intent(getBaseContext(), MyService.class));

    }

    private boolean compareAccount(){
        String prUser = edtPrUser.getText().toString();
        String prPass = edtPrPass.getText().toString();
        if (prUser.equals(LoginSonActivity.parentUser) && prPass.equals(LoginSonActivity.prentPass)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnLogout) {
            if (compareAccount()) {
                // cai dat lai
//                MainActivity.isLoginSon = false;
                // dung chay ngam
                stopService(getCurrentFocus());
                // xoa tren Parse
                QueryByUser queryByUser = new QueryByUser();
                queryByUser.queryByuser("Children", LoginSonActivity.CHILD_NAME, LoginSonActivity.childUser, new QueryByUser.QueryByUserCallBack() {
                    @Override
                    public void queryByUserSuccess(ParseObject parseObject) {
                        try {
                            parseObject.delete();
                            Log.d("", "delete on Parse");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        parseObject.saveInBackground();
                    }
                });

                // quay ve man hinh login
                Intent intentSonBack = new Intent(ChildenActivity.this, LoginSonActivity.class);
                startActivity(intentSonBack);
                finish();
            } else {
                Toast.makeText(getBaseContext(), "User or password is not correct!", Toast.LENGTH_SHORT).show();
            }
        } else {
            updateTime();
        }
    }
}
