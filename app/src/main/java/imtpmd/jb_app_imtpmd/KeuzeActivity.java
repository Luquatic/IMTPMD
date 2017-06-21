package imtpmd.jb_app_imtpmd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    boolean wantDelete;
    private ArrayAdapter adapter;
    private ArrayList<Course> courseItems;
    private String vak;
    private int ec;
    private static final String TAG = "KeuzeActivity";
    private DatabaseHelper dbHelper;


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

        addToList();



        // make fab go to other activity
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KeuzeActivity.this, add_course_activity.class));
            }
        });


        // long press deletes item
        keuze_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog();

                if (!wantDelete) {

                    Log.d(TAG, "Sorry m8, couldn't delete");
                }
                else {
                    dbHelper.remove(id);
                    Log.d(TAG, "I deleted that for ya!");
                }
                return true;
            }
        });

    }

    public void addToList() {
        Log.d(TAG, "Inserting to list");

        // get data from db
        Cursor c = dbHelper.getData();
        courseItems = new ArrayList<Course>();

        // iterating through db
        while (c.moveToNext()) {
            String course = c.getString(0);
            String punten = c.getString(1);
            courseItems.add(new Course(course, punten));
        }

        // make adapter and set it to listview
        adapter = new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, courseItems);
        keuze_list.setAdapter(adapter);

    }





    private void showDeleteDialog() {
        AlertDialog.Builder infobuilder = new AlertDialog.Builder(this);
        infobuilder.setCancelable(false);
        infobuilder.setTitle("Vak/project verwijderen");
        infobuilder.setMessage("Weet je zeker dat je het vak of project wilt verwijderen?");
        final TextView text = new TextView(this);
        // action when pressed OK
        infobuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                wantDelete = true;
                dialog.cancel();
            }
        });

        infobuilder.setNegativeButton("Nee", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                wantDelete = false;
                dialog.cancel();
            }
        });
        infobuilder.show();

    }

}
