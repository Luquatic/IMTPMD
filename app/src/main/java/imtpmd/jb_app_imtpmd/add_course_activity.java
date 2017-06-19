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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setTitle("Voeg een vak of project toe");
        final Context context = getApplicationContext();

        // initializing elements
        course_edit = (EditText) findViewById(R.id.edit_vak);
        ec_edit = (EditText) findViewById(R.id.edit_ec);
        save_btn = (Button) findViewById(R.id.save_button);

        // action on save button
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (course_edit.getText().toString().trim().length() > 0 && ec_edit.getText().toString().trim().length() > 0 ) {
                    Intent goBack = new Intent(add_course_activity.this, KeuzeActivity.class);
                    goBack.putExtra("vak", course_edit.getText().toString());
                    goBack.putExtra("ec", ec_edit.getText());
                    goBack.putExtra("student", PreferenceManager.getDefaultSharedPreferences(context).getString("student_name", student_name));
                    startActivity(goBack);
                }
                else {
                    Toast.makeText(context, "Voer juiste informatie in!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
