package imtpmd.jb_app_imtpmd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.File;

/**
 * Created by Luquatic on 19-6-2017.
 */

public class Settings extends AppCompatActivity {

    private String student_naam;
    private RadioButton reset_button;
    private DatabaseHelper dbHelper;
    private EditText etHint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Instellingen");

        // get student name from intent
        student_naam = getIntent().getStringExtra("student");

        // set current name as text in edittext (if user doesn't want to change)
        etHint = (EditText) findViewById(R.id.naam);
        etHint.setText(student_naam);

        reset_button = (RadioButton) findViewById(R.id.radioButton);


        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();

                //clean shared preferences
                PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();

                // clean db
                context.deleteDatabase("table_course");

                // clean all data
                clearApplicationData();

                // restart application
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    // menu button top right
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    // menu event handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Context context = getApplicationContext();
            EditText etOld = (EditText) findViewById(R.id.naam);
            etOld.setInputType(InputType.TYPE_CLASS_TEXT);
            String etNew = etOld.getText().toString();
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString("student_name", etNew).commit();
            Intent intent = new Intent(Settings.this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

}
