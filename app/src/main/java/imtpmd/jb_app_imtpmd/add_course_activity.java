package imtpmd.jb_app_imtpmd;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_course_activity extends AppCompatActivity {
    private EditText course_edit;
    private EditText ec_edit;
    private Button save_btn;
    private String student_name;
    private int ec;
    private DatabaseHelper mDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);


        setTitle("Voeg een keuzevak toe");
        final Context context = getApplicationContext();


        // initializing elements
        course_edit = (EditText) findViewById(R.id.edit_vak);
        ec_edit = (EditText) findViewById(R.id.edit_ec);
        save_btn = (Button) findViewById(R.id.save_button);
        mDatabaseHelper = new DatabaseHelper(this);




        // action on save button
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                if (course_edit.getText().toString().trim().length() > 0 && ec_edit.getText().toString().trim().length() > 0 ) {
                    // getting variables from textfields
                    String course_name = course_edit.getText().toString();
                    int EC = Integer.parseInt(ec_edit.getText().toString());
                    String bool = "Niet behaald";
                    // adding the data
                    addData(course_name, EC, bool);

                    // starting the intent
                    Intent goBack = new Intent(add_course_activity.this, KeuzeActivity.class);
                    goBack.putExtra("student", PreferenceManager.getDefaultSharedPreferences(context).getString("student_name", student_name));
                    startActivity(goBack);
                }
                else {
                    Toast.makeText(context, "Voer juiste informatie in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addData (String item, int ec, String bool) {
        Context context = getApplicationContext();
        // boolean to insert data in sqldatabase
        boolean insertData = mDatabaseHelper.addData(item, ec, bool);

        if (insertData) {
            Toast.makeText(context, "Vak toegevoegd!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Vak toevoegen mislukt!", Toast.LENGTH_SHORT).show();
        }
    }
}
