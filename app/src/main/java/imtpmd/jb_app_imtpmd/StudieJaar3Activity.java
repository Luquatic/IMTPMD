package imtpmd.jb_app_imtpmd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Luquatic on 19-6-2017.
 */

public class StudieJaar3Activity extends AppCompatActivity {

    // declaring variables private for class
    private ListView vakken_list_view;
    private String student_naam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_jaar);
        student_naam = getIntent().getStringExtra("student");
        setTitle("Studiejaar 3 van " + student_naam);

        String[] vakken = {

        };

        // listview for course years
//        vakken_list_view = (ListView) findViewById(R.id.vakken_list);
//
//        // arrayadapter for jaar_list_view listview
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, vakken);
//
//        ListView listView = (ListView)findViewById(R.id.vakken_list);
//        listView.setAdapter(adapter);
    }
}
