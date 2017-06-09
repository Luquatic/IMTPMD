package imtpmd.jb_app_imtpmd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class course_activity extends AppCompatActivity {
    private String studiejaar;
    private String student_naam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_activity);

        // set selected course year as title
        studiejaar = getIntent().getExtras().get("Jaar").toString();
        student_naam = getIntent().getExtras().get("student").toString();
        setTitle(studiejaar + " van " + student_naam);
    }
}
