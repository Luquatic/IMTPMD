package imtpmd.jb_app_imtpmd;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3500;
    private TextView app_name;
    private TextView app_description;
    private String student_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        // method to hide actionbar from activity
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // initializing attributes
        app_name = (TextView) findViewById(R.id.nameApp);
        app_description = (TextView) findViewById(R.id.descriptionApp);

        // handler for splashscreen (redirects splashscreen to webview activity after 3500 seconds
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
