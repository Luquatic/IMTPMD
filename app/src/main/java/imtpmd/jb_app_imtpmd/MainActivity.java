package imtpmd.jb_app_imtpmd;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    // variable to access root of firebase database
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    // declaring variables private for class
    private ListView jaar_list_view;
    private ArrayAdapter jaar_adapter;
    private ArrayList<String> jaar_list = new ArrayList<>();
    private String patient_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("HBO Informatica | HSL");


        // listview for room names
        jaar_list_view = (ListView) findViewById(R.id.jaar_list);


        // arrayadapter for room_names listview
        jaar_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jaar_list);
        jaar_list_view.setAdapter(jaar_adapter);


        // make app request database rooms for chat
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // iterate through root children
                Iterator i = dataSnapshot.getChildren().iterator();
                Set<String> set = new HashSet<String>();

                while (i.hasNext()) {
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                jaar_list.clear();
                jaar_list.addAll(set);
                jaar_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }
}
