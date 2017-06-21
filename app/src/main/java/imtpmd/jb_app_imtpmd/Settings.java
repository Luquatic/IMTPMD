package imtpmd.jb_app_imtpmd;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by Luquatic on 19-6-2017.
 */

public class Settings extends AppCompatActivity {

    private String student_naam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Instellingen");

        student_naam = getIntent().getStringExtra("student");
        EditText etHint = (EditText) findViewById(R.id.naam);
        etHint.setHint(student_naam);
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
}
