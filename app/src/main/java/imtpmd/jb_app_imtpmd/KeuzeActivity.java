package imtpmd.jb_app_imtpmd;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class KeuzeActivity extends AppCompatActivity {
    private FloatingActionButton fab_add;
    private String student_naam;
    private ListView keuze_list;
    private boolean wantDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_keuze);
        student_naam = getIntent().getStringExtra("student");
        setTitle("Keuzevakken en projecten van " + student_naam);


        //initialzing elements
        fab_add = (FloatingActionButton)findViewById(R.id.fab_add);
        keuze_list = (ListView) findViewById(R.id.keuze_list);

        // initializing list


        // make fab go to other activity
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KeuzeActivity.this, add_course_activity.class));
            }
        });










    }

    protected void onStart() {
        super.onStart();
        final ArrayList<String> courseItems = new ArrayList<String>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, courseItems);
        keuze_list.setAdapter(adapter);

        // long press deletes item
        keuze_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog();
                if (wantDelete) {
                    courseItems.remove(position);
                    adapter.notifyDataSetChanged();

                }
                return true;


            }
        });

        if (getIntent().hasExtra("vak") && getIntent().hasExtra("ec")) {
            String vak = getIntent().getStringExtra("vak");
            String ec = getIntent().getStringExtra("ec");
            courseItems.add(vak);
            adapter.notifyDataSetChanged();
        }
    }


    private void showDeleteDialog() {
        AlertDialog.Builder infobuilder = new AlertDialog.Builder(this);
        infobuilder.setCancelable(false);
        infobuilder.setTitle("Vak/project verwijderen");
        infobuilder.setMessage("Weet je zeker dat je het vak of project wilt verwijderen?");
        final TextView text = new TextView(this);
        // action when pressed OK
        infobuilder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                wantDelete = true;
            }
        });

        infobuilder.setNegativeButton("Nee", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                wantDelete = false;
            }
        });
        infobuilder.show();

    }

}
