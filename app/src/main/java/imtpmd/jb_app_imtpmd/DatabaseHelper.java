package imtpmd.jb_app_imtpmd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Bram on 20-6-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "table_courses";

    private static final String col1 = "ID";
    private static final String col2 = "course_name";
    private static final String col3 = "course_points";
    private static final String col4 = "behaald";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + col2 + " TEXT, " + col3 + " INTEGER, " + col4 + " TEXT, " + col1 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public void delDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item, int ec, String bool) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2, item);
        contentValues.put(col3, ec);
        contentValues.put(col4, bool);
        Log.d(TAG, "addData: Adding " + item + " and " + ec + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);


        // checks if data is inserted correctly
        if (result == -1) {
            // not inserted correctly
            return false;
        }
        else {
            // inserted correctly
            return true;
        }


    }

    // method to return data from database
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return c;
    }

    public void remove(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String del_item = String.valueOf(id);
        db.execSQL("DELETE FROM table_course WHERE id = " + del_item );

    }


}
