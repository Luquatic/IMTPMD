package imtpmd.jb_app_imtpmd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import imtpmd.jb_app_imtpmd.Models.CourseModel;
import imtpmd.jb_app_imtpmd.R;
import imtpmd.jb_app_imtpmd.content.VakContent;

/**
 * Created by Luquatic on 19-6-2017.
 */

public class StudieJaar1Activity extends AppCompatActivity {

    // declaring variables private for class
    private ListView vakken_list_view;
    private String student_naam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_jaar);
        student_naam = getIntent().getStringExtra("student");
        setTitle("Studiejaar 1 van " + student_naam);

        // spinner top right
        Spinner spinner = (Spinner) findViewById(R.id.periode_spinner);
        // create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.periodes_array, android.R.layout.simple_spinner_item);
        // specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("Alle Periodes")) {
                    vakken_list_view.setAdapter(new ArrayAdapter<CourseModel>(StudieJaar1Activity.this, android.R.layout.simple_list_item_1, VakContent.ITEMS));
//                } else if (item.equals("Periode 1")) {
//                    vakken_list_view.setAdapter(new ArrayAdapter<String>(StudieJaar1Activity.this, android.R.layout.simple_list_item_1, ));
//                } else if (item.equals("Periode 2")) {
//                    vakken_list_view.setAdapter(new ArrayAdapter<String>(StudieJaar1Activity.this, android.R.layout.simple_list_item_1, periode_2));
//                } else if (item.equals("Periode 3")) {
//                    vakken_list_view.setAdapter(new ArrayAdapter<String>(StudieJaar1Activity.this, android.R.layout.simple_list_item_1, periode_3));
//                } else if (item.equals("Periode 4")) {
//                    vakken_list_view.setAdapter(new ArrayAdapter<String>(StudieJaar1Activity.this, android.R.layout.simple_list_item_1, periode_4));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // listview for course years
        vakken_list_view = (ListView) findViewById(R.id.vakken_list);
        // arrayadapter for jaar_list_view listview
        for(CourseModel vak:VakContent.ITEMS){
            Log.e("vakobject",vak.getNaam());
        }
        vakken_list_view.setAdapter(new ArrayAdapter<CourseModel>(this, android.R.layout.simple_list_item_1, VakContent.ITEMS));
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

        if (id == R.id.action_settings) {
            Intent intent = new Intent(StudieJaar1Activity.this, Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
