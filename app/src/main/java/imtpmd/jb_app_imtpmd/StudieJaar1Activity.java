package imtpmd.jb_app_imtpmd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luquatic on 19-6-2017.
 */

public class StudieJaar1Activity extends AppCompatActivity {

    // declaring variables private for class
    private ListView vakken_list_view_p1, vakken_list_view_p2, vakken_list_view_p3, vakken_list_view_p4;
    private String student_naam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_jaar);
        student_naam = getIntent().getStringExtra("student");
        setTitle("Studiejaar 1 van " + student_naam);

        String[] periode_1 = {
                "IARCH",
                "IIBPM",
                "IHBO",
                "IOPR1"
        };

        String[] periode_2 = {
                "IWDR",
                "IRDB",
                "IIBUI",
                "INET",
                "IPRODAM",
                "IPOMEDT"
        };

        String[] periode_3 = {
                "IMUML",
                "IOPR2",
                "IFIT",
                "IPOFIT",
                "IPOSE"
        };

        String[] periode_4 = {
                "IPROV",
                "ICOMMP",
                "ISLP"
        };

        // listview for course years
        vakken_list_view_p1 = (ListView) findViewById(R.id.vakken_list_p1);
        vakken_list_view_p2 = (ListView) findViewById(R.id.vakken_list_p2);
        vakken_list_view_p3 = (ListView) findViewById(R.id.vakken_list_p3);
        vakken_list_view_p4 = (ListView) findViewById(R.id.vakken_list_p4);

        // arrayadapter for jaar_list_view listview
        vakken_list_view_p1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, periode_1));
        vakken_list_view_p2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, periode_2));
        vakken_list_view_p3.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, periode_3));
        vakken_list_view_p4.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, periode_4));
    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }

    // menu button top right
    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // menu event handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(StudieJaar1Activity.this, Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
