package imtpmd.jb_app_imtpmd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView jaarList;
    private ArrayAdapter listAdapter;
    private ArrayList<String> jaarArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("HBO Informatica | HSL");

        // initializing listview and adapter
        jaarList = (ListView) findViewById(R.id.jaar_list);
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jaarArrayList);


    }
}
