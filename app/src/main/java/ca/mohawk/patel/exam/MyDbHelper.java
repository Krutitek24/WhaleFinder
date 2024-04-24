package ca.mohawk.patel.exam;
/**
 * I, Kruti Patel, 000857563 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 *
 *
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Random;

public class MyDbHelper extends SQLiteOpenHelper {

    public static final String tag = "==MyDbHelper==";
    /** Database file is stored in the Android file system **/
    public static final String DATABASE_FILE_NAME = "WhaleDatabase.db";
    /** Database is upgradeable, version #s can be used to track schema updates **/
    public static final int DATABASE_VERSION = 1;

    /** SQL query details **/

    /** table name, used for queries, can have multiple tables **/
    public static final String WHALE_TABLE = "whaletable";
    /** primary key **/
    public static final String WHALE_ID = "whaleid";
    /** col #1, stores whale length **/
    public static final String LENGTH = "length";
    /** col #3, stores total # of whale sightings an integer >= 1 **/
    public static final String  SIGHTINGS = "sightings";

    /** The SQL create command is built up from public symbols **/
    private static final String SQL_CREATE =
            "CREATE TABLE " + WHALE_TABLE + " ( " + WHALE_ID + " TEXT PRIMARY KEY, " +
                    LENGTH + " REAL, " + SIGHTINGS + " INTEGER) ";

    /**
     * @param context used to locate the file path for this application
     */
    public MyDbHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        Log.d(tag, "constructor");
    }

    /** track whether the DB has just been created **/
    private boolean dbempty = false;
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(tag, "onCreate " + SQL_CREATE);
        db.execSQL(SQL_CREATE);
        dbempty = true;
    }

    /** We won't deal with override **/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * Populate the database with data describing 200 Beluga Whales.
     *
     * Parameters adapted from:
     *
     * https://en.wikipedia.org/wiki/Beluga_whale
     *
     *
     */
    public void init() {
        SQLiteDatabase db = this.getWritableDatabase();
        if (dbempty == false)
            return;
        Random rn = new Random(1);
        String cons[] = {"M", "F"};
        int id = 1;
        for (int i = 0; i < 200; i++) {
            ContentValues values = new ContentValues();
            int g = rn.nextInt(2);
            id += rn.nextInt(4) + 1;
            // randomly generate an age, use this to compute sightings and length.
            // Longevity is somewhere between 40 and 70 years.
            int a = rn.nextInt(60) + 1;
            int s = a / 3 - rn.nextInt(3);
            if (s < 1)
                s = 1;
            double l;
            if (g == 0) {
                // males are generally longer than females
                l = (35 + rn.nextInt(20)) / 10.0;
            } else {
                l = (30 + rn.nextInt(11)) / 10.0;
            }
            // young whales are smaller than adults, reaching full size at age of 10y
            // estimate new born length as 1/3rd adult length
            if (a < 10) {
                l = l * ( (2 * a + 10.0) / 30.0);
            }

            String wid = String.format("%s%03d", cons[g], id);

            values.put(MyDbHelper.WHALE_ID, wid);
            values.put(MyDbHelper.SIGHTINGS, s);
            values.put(MyDbHelper.LENGTH, l);
            db.insert(MyDbHelper.WHALE_TABLE, null, values);
        }
        dbempty = false;
    }

}