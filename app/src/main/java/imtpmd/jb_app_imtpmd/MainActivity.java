package imtpmd.jb_app_imtpmd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Type;
import java.util.List;

import imtpmd.jb_app_imtpmd.Models.CourseModel;
import imtpmd.jb_app_imtpmd.content.VakContent;

public class MainActivity extends AppCompatActivity {


    // variable to access root of firebase database
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    // declaring variables private for class
    private String student_name;
    private String jaar;

    // user name SharedPreferences
    public static final String DIALOG = "dialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences(DIALOG, 0);
        boolean askedForName = settings.getBoolean("askedforname", false);
        final String student = settings.getString("student_name", "");

        // set app title
        setTitle("Overzicht");


        if(!askedForName) {
            SharedPreferences.Editor edit_dialog = settings.edit();
            show_app_info();

            // set boolean for dialog to true
            edit_dialog.putBoolean("askedforname", true);
            edit_dialog.apply();

        }


        // check if name is null
        Context context = getApplicationContext();
        String name_check = PreferenceManager.getDefaultSharedPreferences(context).getString("student_name", student_name);
        if (name_check == null && askedForName) {
            get_student_name();
        }

        Context context2 = getApplicationContext();

        // get student name from SharedPreferences
        final String final_studie_naam = PreferenceManager.getDefaultSharedPreferences(context2).getString("student_name", student_name);

        // buttons
        Button btnJaar1 = (Button) findViewById(R.id.btnJaar1);
        btnJaar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jaar = "1";
                requestSubjects(jaar);
            }
        });
        Button btnJaar2 = (Button) findViewById(R.id.btnJaar2);
        btnJaar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jaar = "2";
                requestSubjects(jaar);
            }
        });
        Button btnJaar3 = (Button) findViewById(R.id.btnJaar3);
        btnJaar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jaar = "3";
                requestSubjects(jaar);
            }
        });
        Button btnJaar4 = (Button) findViewById(R.id.btnJaar4);
        btnJaar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jaar = "4";
                requestSubjects(jaar);
            }
        });
        Button btnKeuze = (Button) findViewById(R.id.btnKeuze);
        btnKeuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent keuzevakken_intent = new Intent(getApplicationContext(), KeuzeActivity.class);
                keuzevakken_intent.putExtra("student", final_studie_naam);
                startActivity(keuzevakken_intent);
            }
        });
    }

    public void goTo() {
        Intent intent = new Intent(getApplicationContext(), VakkenActivity.class);
        String final_student_name = PreferenceManager.getDefaultSharedPreferences(this).getString("student_name", student_name);
        intent.putExtra("student", final_student_name);
        String final_jaar = PreferenceManager.getDefaultSharedPreferences(this).getString("jaar", jaar);
        intent.putExtra("final_jaar", final_jaar);
        startActivity(intent);
    }

    private void requestSubjects(String jaar){
        Type type = new TypeToken<List<CourseModel>>(){}.getType();

        GsonRequest<List<CourseModel>> request = new GsonRequest<>("http://aid.jesseyfransen.com/api/medtgrades/jaar/" +jaar + "/",
                type, null, new Response.Listener<List<CourseModel>>() {
            @Override
            public void onResponse(List<CourseModel> response) {
                processRequestSucces(response);
                Log.e("Request", ": success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                processRequestError(error);
                Log.e("Request", ": error");
            }
        });
        VolleyHelper.getInstance(this).addToRequestQueue(request);
    }

    private void processRequestSucces(List<CourseModel> vakken ){

        VakContent.ITEMS.clear();
        for(CourseModel vak:vakken) {
            VakContent.addItem(vak);
        }
        goTo();
    }

    private void processRequestError(VolleyError error){
        // WAT ZULLEN WE HIERMEE DOEN ?? - niets..
    }



    // alertdialog that shows app information
    public void show_app_info() {
        AlertDialog.Builder infobuilder = new AlertDialog.Builder(this);
        infobuilder.setTitle("Welkom bij Medt Grades!");
        infobuilder.setMessage("Met deze app kan je je vakken bekijken per studiejaar, aanvinken of je ze gehaald hebt, en keuzevakken toevoegen.");

        // action when pressed OK
        infobuilder.setPositiveButton("Ga door", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                // get student name to personalize app
                get_student_name();
            }
        });

        infobuilder.show();
    }


    public void get_student_name() {

        // make dialog box
        AlertDialog.Builder namebuilder = new AlertDialog.Builder(this);
        namebuilder.setTitle("Geef je naam");
        final EditText namefield = new EditText(this);
        namebuilder.setView(namefield);
        namebuilder.setCancelable(false);
        //action when name entered and "Ga door" pressed
        namebuilder.setPositiveButton("Ga door", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Context context = getApplicationContext();
                student_name = namefield.getText().toString();
                // store name in sharedpreferences
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString("student_name", student_name).apply();
            }
        });


        namebuilder.show();
    }

    // menu button top right
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // menu event handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String final_student_name = PreferenceManager.getDefaultSharedPreferences(this).getString("student_name", student_name);

        // go to settings activity
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            intent.putExtra("student", final_student_name);
            startActivity(intent);
            return true;
        }

        // go to EcActivity
        if (id == R.id.education) {
            Intent intent = new Intent(MainActivity.this, EcActivity.class);
            intent.putExtra("student", final_student_name);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
