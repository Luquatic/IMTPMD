package imtpmd.jb_app_imtpmd;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    // variable to access root of firebase database
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    // declaring variables private for class
    private ListView jaar_list_view;
    private ArrayAdapter jaar_adapter;
    private ArrayList<String> jaar_list = new ArrayList<>();
    private String student_name;

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
            edit_dialog.commit();

        }

        // listview for course years
        jaar_list_view = (ListView) findViewById(R.id.jaar_list);


        // arrayadapter for jaar_list_view listview
        jaar_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jaar_list);
        jaar_list_view.setAdapter(jaar_adapter);

        // make app request database course years
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // iterate through root children
                Iterator i = dataSnapshot.getChildren().iterator();
                Set<String> set = new HashSet<String>();

                while (i.hasNext()) {
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                jaar_list.clear();
                jaar_list.addAll(set);
                jaar_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // listener to go to desired year
        jaar_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context2 = getApplicationContext();

                // get student name from SharedPreferences
                String final_studie_naam = PreferenceManager.getDefaultSharedPreferences(context2).getString("student_name", student_name);


                // get name of clicked item
                String lsn = jaar_list_view.getItemAtPosition(position).toString();

                // intent to switch to room
                if (lsn.equals("Keuzevakken")) {
                    Intent keuzevakken_intent = new Intent(getApplicationContext(), KeuzeActivity.class);
                    keuzevakken_intent.putExtra("student", final_studie_naam);
                    startActivity(keuzevakken_intent);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), course_activity.class);
                    // give study year and student name to intent
                    intent.putExtra("Jaar", ((TextView)view).getText().toString());
                    intent.putExtra("student", final_studie_naam);
                    startActivity(intent);
                }



            }
        });






    }

    // alertdialog that shows app information
    public void show_app_info() {
        AlertDialog.Builder infobuilder = new AlertDialog.Builder(this);
        infobuilder.setTitle("Welkom bij IT Grades!");
        infobuilder.setMessage("Met deze app kan je je vakken bekijken per studiejaar, aanvinken of je ze gehaald hebt, en keuzevakken toevoegen.");
        final TextView text = new TextView(this);
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

        //action when name entered and "Ga door" pressed
        namebuilder.setPositiveButton("Ga door", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Context context = getApplicationContext();
                student_name = namefield.getText().toString();
                // store name in sharedpreferences
                PreferenceManager.getDefaultSharedPreferences(context).edit().putString("student_name", student_name).commit();
            }
        });

        // action when cancel is pressed
        namebuilder.setNegativeButton("Annuleer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        namebuilder.show();
    }
}
