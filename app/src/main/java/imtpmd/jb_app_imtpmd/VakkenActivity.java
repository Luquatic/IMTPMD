package imtpmd.jb_app_imtpmd;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import imtpmd.jb_app_imtpmd.Models.CourseModel;
import imtpmd.jb_app_imtpmd.content.VakContent;

/**
 * Created by Luquatic on 19-6-2017.
 */

public class VakkenActivity extends AppCompatActivity {

    // declaring variables private for class
    private ListView vakken_list_view;
    private String student_naam;
    private String jaar;
    private String periode;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private final static String BOOL2 = "bool2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vakken);
        student_naam = getIntent().getStringExtra("student");
        jaar = getIntent().getStringExtra("final_jaar");
        setTitle("Studiejaar " +jaar +" van " + student_naam);

        // spinner top right
        spinner = (Spinner) findViewById(R.id.periode_spinner);

        // create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this, R.array.periodes_array, android.R.layout.simple_spinner_item);

        // specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // check if activity is opened for first time
        // setting dialog
        SharedPreferences settings = getSharedPreferences(BOOL2, 0);
        boolean shownDialog = settings.getBoolean("showndialog", false);
        if(!shownDialog) {
            SharedPreferences.Editor edit_dialog = settings.edit();
            showInfo();
            edit_dialog.putBoolean("showndialog", true);
            edit_dialog.apply();
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.equals("Alle periodes")) {
                    periode = "5";
                    requestSubjects(jaar, periode);
                } else if (item.equals("Periode 1")) {
                    periode = "1";
                    requestSubjects(jaar, periode);
                } else if (item.equals("Periode 2")) {
                    periode = "2";
                    requestSubjects(jaar, periode);
                } else if (item.equals("Periode 3")) {
                    periode = "3";
                    requestSubjects(jaar, periode);
                } else if (item.equals("Periode 4")) {
                    periode = "4";
                    requestSubjects(jaar, periode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // listview for course years
        vakken_list_view = (ListView) findViewById(R.id.vakken_list);
        // arrayadapter for jaar_list_view listview
        vakken_list_view.setAdapter(new ArrayAdapter<CourseModel>(this, android.R.layout.simple_list_item_1, VakContent.ITEMS));
        // get course name and update this to 'behaald'
        vakken_list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                vakken_list_view.deferNotifyDataSetChanged();

                // this gets the ID
                int pos = (int) id;
                String selectedFromList =(vakken_list_view.getItemAtPosition(pos).toString());

                // split selected String
                String[] parts = selectedFromList.split(" ");
                String[] final_part = parts[1].split("\n");

                // gets course name
                String COURSE = final_part[0];

                // method to update database
                Log.d("You clicked ", "" + pos );
                Log.d("course name", COURSE);
                postSubjects(COURSE);

                // refresh list to immediately show customized list
                requestSubjects(jaar, periode);
                vakken_list_view.deferNotifyDataSetChanged();
                requestSubjects(jaar, periode);
                vakken_list_view.deferNotifyDataSetChanged();
                return true;
            }
        });
    }

    private void postSubjects(String COURSE) {
        String url = "http://aid.jesseyfransen.com/api/medtgrades/update/" +COURSE;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response).getJSONObject("form");
                            String site = jsonResponse.getString("site"),
                                    network = jsonResponse.getString("network");
                            System.out.println("Site: "+site+"\nNetwork: "+network);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("site", "code");
                params.put("network", "tutsplus");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void requestSubjects(String jaar, String periode){
        Type type = new TypeToken<List<CourseModel>>(){}.getType();

        GsonRequest<List<CourseModel>> request = new GsonRequest<>("http://aid.jesseyfransen.com/api/medtgrades/jaar/" +jaar + "/periode/" +periode,
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

        // listview for course years
        vakken_list_view = (ListView) findViewById(R.id.vakken_list);
        // arrayadapter for jaar_list_view listview
        vakken_list_view.setAdapter(new ArrayAdapter<CourseModel>(this, android.R.layout.simple_list_item_1, VakContent.ITEMS));
    }

    private void processRequestError(VolleyError error){
        // WAT ZULLEN WE HIERMEE DOEN ?? - niets..
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
            Intent intent = new Intent(VakkenActivity.this, Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showInfo() {
        AlertDialog.Builder infobuilder = new AlertDialog.Builder(this);
        infobuilder.setTitle("Studievakken per jaar");
        infobuilder.setMessage("Hier zie je de verplichte vakken per studiejaar. Houdt een vak ingedrukt om aan te geven dat je deze behaald hebt.");
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