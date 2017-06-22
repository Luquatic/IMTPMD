package imtpmd.jb_app_imtpmd;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EcActivity extends AppCompatActivity {
    private TextView keuze_ec;
    private TextView keuze_1;
    private TextView keuze_2;
    private TextView keuze_3;
    private TextView keuze_4;
    private DatabaseHelper dbHelper;
    private String TABLE_NAME = "table_courses";
    private String num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ec);
        setTitle("Studiepunten");



        // initializing elements
        dbHelper = new DatabaseHelper(this);
        keuze_ec = (TextView) findViewById(R.id.keuze_ec);
        keuze_1 = (TextView) findViewById(R.id.keuze_1);
        keuze_2 = (TextView) findViewById(R.id.keuze_2);
        keuze_3 = (TextView) findViewById(R.id.keuze_3);
        keuze_4 = (TextView) findViewById(R.id.keuze_4);


        // making db objects
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // getting total of 'behaalde' points
        Cursor cursor = db.rawQuery("SELECT SUM (course_points) AS total_points FROM " + TABLE_NAME + " WHERE behaald = 'behaald'", null);
        if( cursor != null && cursor.moveToFirst() ){
            num = cursor.getString(cursor.getColumnIndex("total_points"));
            cursor.close();

        }


        if (num == null) {
            keuze_ec.setText("0");
        }
        else {
            keuze_ec.setText(num);
        }


    }


}
