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

public class EcActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private String student_naam;
    private String TABLE_NAME = "table_courses";
    private String num;
    private int ec_keuze;
    private int ec_1;
    private int ec_2;
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
            ec_keuze = Integer.parseInt(num);
            cursor.close();
        }

        // get ec from years
        ec_1 = getRequest(1, new VolleyCallback() {
            @Override
            public void onSucces(String result) {
            }
        });

        Log.d("EC TEST = ", String.valueOf(ec_1));




        // getting data for chart
        ec_data = new int[]{ec_keuze, ec_1, ec_2, ec_3, ec_4};
        ec_name = new String[]{"Keuzevakken", "Studiejaar 1", "Studiejaar 2", "Studiejaar 3", "Studiejaar 4"};

        // customizing and adding data to chart
        chart.setDescription("");
        chart.setHoleRadius(10f);
        chart.setDrawSliceText(false);



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
    private int getRequest(int jaar, final VolleyCallback callback) {
        String url = "http://aid.jesseyfransen.com/api/medtgrades/jaar/" + jaar + "/ec";
        final RequestQueue requestQueue = Volley.newRequestQueue(EcActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE IS", response);
                number = response;
                callback.onSucces(number);
                requestQueue.stop();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", ": COULD NOT CONNECT");

            }
        });
        requestQueue.add(stringRequest);
        return jaar;
    }

    public interface VolleyCallback {
        void onSucces(String result);
    }
}



