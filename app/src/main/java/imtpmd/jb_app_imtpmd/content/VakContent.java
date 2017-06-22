package imtpmd.jb_app_imtpmd.content;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import imtpmd.jb_app_imtpmd.Course;
import imtpmd.jb_app_imtpmd.Models.CourseModel;

/**
 * Created by Jessey Fransen on 21/06/2017.
 */

public class VakContent {

    public static List<CourseModel> ITEMS = new ArrayList<>();

    public static Map<String, CourseModel> ITEM_MAP = new HashMap<>();

    public static void addItem(final CourseModel i) {
        ITEMS.add(i);
        ITEM_MAP.put(i.getNaam(), i);
    }

    public static void printTest() {
        for(CourseModel vak:ITEMS) {
            Log.e("abc", vak.getNaam());
        }
    }
}
