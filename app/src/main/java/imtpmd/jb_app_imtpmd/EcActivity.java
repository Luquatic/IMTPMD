package imtpmd.jb_app_imtpmd;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class EcActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private String student_naam;
    private String TABLE_NAME = "table_courses";
    private String num;
    private int ec_keuze;
    private static int ec_1;
    private static int ec_2;
    private int ec_3;
    private int ec_4;
    private String number;
    private PieChart chart;
    private int ec_data[];
    private String ec_name[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ec);
        student_naam = getIntent().getStringExtra("student");
        setTitle("Studiepunten van " + student_naam);

        // add return button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initializing elements
        dbHelper = new DatabaseHelper(this);
        chart = (PieChart) findViewById(R.id.ec_chart);

        // making db objects
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // getting total of 'behaalde' points from keuzevakken
        Cursor cursor = db.rawQuery("SELECT SUM (course_points) AS total_points FROM " + TABLE_NAME + " WHERE behaald = 'behaald'", null);
        if (cursor != null && cursor.moveToFirst()) {
            num = cursor.getString(cursor.getColumnIndex("total_points"));
            if (num == null) {
                num = "0";
            }
            ec_keuze = parseInt(num);
            cursor.close();
        }

        // get ec from years
//        ec_1 = getRequest(1);


        for(int j = 1; j < 5; j++){
            getRequest(j);
        }


        // getting data for chart
        Log.d("POEP KUT ANDROID: ", String.valueOf(ec_1));
        ec_data = new int[]{ec_keuze, ec_1, ec_2, ec_3, ec_4};
        ec_name = new String[]{"Keuzevakken", "Studiejaar 1", "Studiejaar 2", "Studiejaar 3", "Studiejaar 4"};

        // customizing and adding data to chart
        chart.setDescription("");
        chart.setHoleRadius(10f);
        chart.setDrawSliceText(false);
        addDataSet();



    }

    // add data to chart
    private void addDataSet() {

        ArrayList<Entry> yEntry = new ArrayList<>();
        ArrayList<String> xEntry = new ArrayList<>();

        // adding data to arraylists
        for (int i = 0; i < ec_data.length; i++) {
            yEntry.add(new Entry(ec_data[i], i));
        }
        for (int i = 0; i < ec_name.length; i++) {
            xEntry.add(ec_name[i]);

        }


        // create dataset
        PieDataSet pieDataSet = new PieDataSet(yEntry, "");
        pieDataSet.setSliceSpace(4);
        pieDataSet.setValueTextSize(12);

        // setting color
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);


        // create pie data object
        PieData pieData = new PieData(xEntry, pieDataSet);
        chart.setData(pieData);
        chart.invalidate();
    }

    // GET request to get behaalde ec from database per year
    private void getRequest(int jaar) {
        String url = "http://aid.jesseyfransen.com/api/medtgrades/jaar/" + jaar + "/ec";
        final RequestQueue requestQueue = Volley.newRequestQueue(EcActivity.this);
        if(jaar == 1) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("RESPONSE IS", response);
                    setEc_1(parseInt(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ERROR", ": COULD NOT CONNECT");

                }
            });
            requestQueue.add(stringRequest);
        } else if (jaar == 2) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("RESPONSE IS", response);
                    setEc_2(parseInt(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ERROR", ": COULD NOT CONNECT");

                }
            });
            requestQueue.add(stringRequest);
        }
    }

    public static void setEc_1(int ec_1) {
        EcActivity.ec_1 = ec_1;
    }

    public static void setEc_2(int ec_2) {
        EcActivity.ec_2 = ec_2;
    }
}



