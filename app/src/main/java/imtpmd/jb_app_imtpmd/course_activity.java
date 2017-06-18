package imtpmd.jb_app_imtpmd;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class course_activity extends AppCompatActivity {
    private String studiejaar;
    private String student_naam;
    FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_activity);

        // set selected course year as title
        studiejaar = getIntent().getStringExtra("Jaar");
        student_naam = getIntent().getStringExtra("student");
        setTitle(studiejaar + " van " + student_naam);

        // floating action button click event
        fab_add = (FloatingActionButton)findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(course_activity.this, Compose.class));
            }
        });


    }
}
