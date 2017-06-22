package imtpmd.jb_app_imtpmd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import static android.media.CamcorderProfile.get;

public class KeuzeActivity extends AppCompatActivity {
    private FloatingActionButton fab_add;
    private String student_naam;
    private ListView keuze_list;
    private ArrayAdapter adapter;
    private ArrayList<Course> courseItems;
    private String vak;
    private int ec;
    private static final String TAG = "KeuzeActivity";
    private DatabaseHelper dbHelper;
    private final static String BOOL = "bool";
    private String TABLE_NAME = "table_courses";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keuze);

        // setting title
        student_naam = getIntent().getStringExtra("student");
        setTitle("Keuzevakken en projecten van " + student_naam);

        //initialzing elements
        fab_add = (FloatingActionButton)findViewById(R.id.fab_add);
        keuze_list = (ListView) findViewById(R.id.keuze_list);
        dbHelper = new DatabaseHelper(this);

        // setting dialog
        SharedPreferences settings = getSharedPreferences(BOOL, 0);
        boolean shownDialog = settings.getBoolean("showndialog", false);

        // show dialog for first time
        if(!shownDialog) {
            SharedPreferences.Editor edit_dialog = settings.edit();
            showInfo();
            edit_dialog.putBoolean("showndialog", true);
            edit_dialog.apply();
        }

        addToList();

        // make fab go to other activity
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KeuzeActivity.this, add_course_activity.class));
            }
        });

        // get course/project name and set it to 'behaald'
        keuze_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                int pos = (int) id;
                String selectedFromList =(keuze_list.getItemAtPosition(pos).toString());
                String[] parts = selectedFromList.split(" ");
                String[] final_part = parts[1].split("\n");
                String COURSE = final_part[0];
                Log.d("course name", COURSE);
                Log.d("You clicked ", "" + pos );
                db.execSQL("UPDATE " + TABLE_NAME + " SET behaald = 'behaald' WHERE course_name = '" + COURSE + "'");

                finish();
                startActivity(getIntent());
                return true;
            }
        });

    }

    // makes back button always go back to mainactivity (not to add_course_activity, if in activity stack)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        return super.onKeyDown(keyCode, event);
    }

    // adding data to list
    public void addToList() {
        Log.d(TAG, "Inserting to list");

        // get data from db
        Cursor c = dbHelper.getData();
        courseItems = new ArrayList<Course>();

        // iterating through db
        while (c.moveToNext()) {
            String course = c.getString(0);
            String punten = c.getString(1);
            String behaald = c.getString(2);
            courseItems.add(new Course(course, punten, behaald));
        }

        // make adapter and set it to listview
        adapter = new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, courseItems);
        keuze_list.setAdapter(adapter);


    }


    // menu button top right
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_keuze, menu);
        return true;
    }

    // menu event handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        // go to settings activity
        if (id == R.id.clean_list) {
            showDeleteDialog();

            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    // shows confirmation dialog if user wants to clean list
    private boolean showDeleteDialog() {
        AlertDialog.Builder infobuilder = new AlertDialog.Builder(this);
        infobuilder.setCancelable(false);
        infobuilder.setTitle("Vakken/projecten verwijderen");
        infobuilder.setMessage("Weet je zeker dat je de lijst leeg wilt maken?");
        final TextView text = new TextView(this);
        // action when pressed OK
        infobuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

                // cleaning list
                Context context = getApplicationContext();
                context.deleteDatabase(TABLE_NAME);
                finish();
                startActivity(getIntent());

            }
        });


        infobuilder.setNegativeButton("Nee", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        infobuilder.show();

    return true;
    }

    private void showInfo() {
        AlertDialog.Builder infobuilder = new AlertDialog.Builder(this);
        infobuilder.setTitle("Keuzevakken en projecten");
        infobuilder.setMessage("Voeg hier je keuzevakken en projecten toe. Houdt een vak/project ingedrukt om aan te geven dat je hem behaald hebt.");
        infobuilder.setCancelable(false);

        // action when pressed OK
        infobuilder.setPositiveButton("Ga door", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        infobuilder.show();
    }
}
