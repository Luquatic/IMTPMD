package imtpmd.jb_app_imtpmd;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class course_activity extends AppCompatActivity {
    private String studiejaar;
    private String student_naam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_activity);

        // set selected course year as title
        studiejaar = getIntent().getStringExtra("Jaar");
        student_naam = getIntent().getStringExtra("student");
        setTitle(studiejaar + " van " + student_naam);

        // add return button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }
}
