package imtpmd.jb_app_imtpmd;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

public class EcActivity extends AppCompatActivity {
    private TextView keuze_ec;
    private TextView keuze_1;
    private TextView keuze_2;
    private TextView keuze_3;
    private TextView keuze_4;
    private DatabaseHelper dbHelper;
    private String student_naam;
    private String TABLE_NAME = "table_courses";
    private String num;
    private int final_num;
    private PieChart chart;
    private int ec_data[];
    private String ec_name[];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ec);
        student_naam = getIntent().getStringExtra("student");
        setTitle("Studiepunten van " + student_naam);



        // initializing elements
        dbHelper = new DatabaseHelper(this);
        chart = (PieChart) findViewById(R.id.ec_chart);


        // making db objects
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // getting total of 'behaalde' points from keuzevakken
        Cursor cursor = db.rawQuery("SELECT SUM (course_points) AS total_points FROM " + TABLE_NAME + " WHERE behaald = 'behaald'", null);
        if( cursor != null && cursor.moveToFirst() ){
            num = cursor.getString(cursor.getColumnIndex("total_points"));
            if (num == null) {
                num = "0";
            }
            final_num = Integer.parseInt(num);
            cursor.close();

        }
        Log.d("TAG", "" + final_num);

        // getting data for chart
        ec_data = new int[]{final_num, 60, 33, 22, 10};
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
        for(int i = 0; i < ec_data.length; i++) {
            yEntry.add(new Entry(ec_data[i], i));
        }
        for(int i = 0; i < ec_name.length; i++) {
            xEntry.add(ec_name[i]);

        }

        Log.d("HALLO", "" + xEntry);

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


}
