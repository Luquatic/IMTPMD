package imtpmd.jb_app_imtpmd;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class EcActivity extends AppCompatActivity {
    private TextView keuze_ec;
    private DatabaseHelper dbHelper;
    private String TABLE_NAME = "table_course";
    private String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ec);
        setTitle("Studiepunten");

        dbHelper = new DatabaseHelper(this);

        // initializing elements
        keuze_ec = (TextView) findViewById(R.id.keuze_ec);

        // making db objects
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // getting total of points
        Cursor cursor = db.rawQuery("SELECT SUM (course_points) AS total_points FROM table_course", null);
        if( cursor != null && cursor.moveToFirst() ){
            num = cursor.getString(cursor.getColumnIndex("total_points"));
            cursor.close();

        }

        // setting points in text
        keuze_ec.setText(num);

    }
}
