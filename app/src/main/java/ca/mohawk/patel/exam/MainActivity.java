package ca.mohawk.patel.exam;
/**
 * I, Kruti Patel, 000857563 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 *
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    public static final String tag = "==MainActivity==";
    MyDbHelper mydbhelp = new MyDbHelper(this);
    /**
     * for searching bu Id
     */
    public EditText editTextWhaleId;
    /**
     * Search button
     */
    public Button buttonSearchDb;
    /**
     * switch between sort by length and id
     */
    public Switch switchSortID;
    /**
     * display no of records
     */
    public Spinner spinnerLimit;
    /**
     * listview to display data
     */
    public ListView listViewWhale;
    /**
     * ArrayList  that contains all Items, which are displayed by the listview
     */
    public static ArrayList<WhaleData> myWhaleList;
    /**
     * Adaptor  of the list view. It takes an arraylist and displays it in a listview.
     */
    public static ArrayAdapter<WhaleData> adapter;

    private static final String LIST_KEY = "itemList";
    private static final String ORDER_BY = "order_by";
    int totalRecords = 0;
    /**
     * no of records display when first load the app
     */
    private int minRecords = 200;
    private String orderBy = MyDbHelper.WHALE_ID;
    private String selection = null;
    /**
     * boolean to check when application start
     */
    private boolean isFirstTime = true;

    /**
     * The onCreate function is called when the activity is first created.
     * It sets up the layout of the activity, and initializes all of its components.
     *
     * @param  savedInstanceState Save the state of the activity
     *
     * @return Void
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydbhelp.init();

        editTextWhaleId = (EditText) findViewById(R.id.editText);
        buttonSearchDb = (Button) findViewById(R.id.button);
        switchSortID = (Switch) findViewById(R.id.switchId);
        spinnerLimit = (Spinner) findViewById(R.id.spinner);
        listViewWhale = (ListView) findViewById(R.id.ListView);

        myWhaleList = new ArrayList<WhaleData>();
        listViewWhale.setOnItemClickListener(this::onItemClick);

        if (savedInstanceState != null) {//checking when activity is not loading first time.
            orderBy = savedInstanceState.getString(ORDER_BY);
            myWhaleList = (ArrayList<WhaleData>) savedInstanceState.getSerializable(LIST_KEY);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myWhaleList);
            listViewWhale.setAdapter(adapter);
        } else {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myWhaleList);
            listViewWhale.setAdapter(adapter);
        }
        updateOutput(getCursor(), false);
        switchSortID.setOnClickListener(this);
        buttonSearchDb.setOnClickListener(this);
        spinnerLimit.setOnItemSelectedListener(this);
        spinnerLimit.setSelection(3);

    }
    /**
     * The updateOutput function is used to update the output of the ListView.
     * It takes in a cursor and a boolean value as parameters. The cursor is used to iterate through
     * all of the records in our database, and add them to an ArrayList that will be displayed on screen.
     * The boolean value determines whether or not we should display a Toast message when this function runs,
     * which we do not want for our initial search query (when we first open up the app).     *
     * @param cursor Get the data from the database
     * @param  isDisplayToast Determine whether or not to display the toast message
     *
     */
    public void updateOutput(Cursor cursor, boolean isDisplayToast) {

        if (myWhaleList != null && myWhaleList.size() > 0)
            myWhaleList.clear();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Log.d(tag, "found DB item");

                WhaleData whaleData = new WhaleData();

                whaleData.setWhaleId(cursor.getString(0));
                whaleData.setLength(cursor.getFloat(1));
                whaleData.setSightings(cursor.getInt(2));

                myWhaleList.add(whaleData);
            }
            cursor.close();

            if (isDisplayToast)
                Toast.makeText(getApplicationContext(), "Searching " + totalRecords + " Whales \n Showing " + myWhaleList.size(), Toast.LENGTH_SHORT).show();

            adapter.notifyDataSetChanged();
        }


    }

    /**
     * The getCursor function returns a cursor object that contains the data from the database.
     * The function takes in three parameters: selection, orderBy, and minRecords.
     * Selection is used to filter out records based on their length value (e.g., &quot;length &amp;gt;= 10&quot;).
     * OrderBy is used to sort the records by either length or sightings (e.g., &quot;length ASC&quot; or &quot;sightings DESC&quot;).
     * MinRecords is used to limit how many records are returned by getCursor(). If minRecords = 0, then all of the
     *   whale data will be
     *
     *
     * @return A cursor that contains the records
     *

     */
    public Cursor getCursor() {
        SQLiteDatabase db = mydbhelp.getReadableDatabase();
        String[] columns = {MyDbHelper.WHALE_ID, MyDbHelper.LENGTH,
                MyDbHelper.SIGHTINGS};

        Cursor cursor = db.query(MyDbHelper.WHALE_TABLE, columns,
                null, null, null, null, null);
        totalRecords = cursor.getCount();

        Cursor cursor1 = db.query(MyDbHelper.WHALE_TABLE, columns,
                selection, null, null, null, orderBy, String.valueOf(minRecords));
        return cursor1;
    }

    /**
     * The onClick function is called when the user clicks on a button.
     * It checks which button was clicked and performs an action accordingly.
     *
     *
     * @param  view Identify which view is being clicked
     *
     * @return A boolean value, which is not used in the code
     *

     */
    @Override
    public void onClick(View view) {
        if (view == switchSortID) {

            if (switchSortID.isChecked()) {
                orderBy = MyDbHelper.LENGTH;
                updateOutput(getCursor(), false);
            } else {
                orderBy = MyDbHelper.WHALE_ID;
                updateOutput(getCursor(), false);
            }


        } else if (view == buttonSearchDb) {
            String inputText = editTextWhaleId.getText().toString();
            // Check if input is empty to display default data
            if (inputText != null && inputText.length() > 0 && inputText.matches("^[FMfm\\d]+$")) {
                selection = MyDbHelper.WHALE_ID + " LIKE \'%" + inputText + "%\'";
                updateOutput(getCursor(), true);

            } else {
                // Display a toast if the input is illegal
                Toast.makeText(getApplicationContext(), "Invalid input! Only 'F', 'M', and numbers are allowed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * The onItemClick function is called when an item in the listview is clicked.
     * It takes four parameters: parent, v, position and id.
     * The parent parameter refers to the adapterView that was clicked (in this case it's a ListView).
     * The v parameter refers to the view within the adapterView that was clicked (a row in our ListView).
     * The position parameter indicates which row of data was selected within our arrayAdapter.
     * Finally, id represents a unique identifier for each item in your Adapter (not used here)
     *
     * @param  parent Get the adapter that is associated with the list view
     * @param  v Get the view that was clicked
     * @param  position Determine which item in the list was clicked
     * @param  id Identify the item in the list that was clicked
     *
     *
     */
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        // Do something in response to the click
        Log.d(tag, "adapter = " + parent);

        Intent switch2Activity2 = new Intent(MainActivity.this, MainActivity2.class);
        switch2Activity2.putExtra("WhaleData", myWhaleList.get(position));
        startActivity(switch2Activity2);

    }

    /**
     * The onSaveInstanceState function is called when the activity is about to be destroyed.
     * This function saves the current state of the application so that it can be restored later.
     * In this case, we are saving our list of items and our current index in order to restore them later.
     *
     * @param  outState Save the state of the activity
     *
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LIST_KEY, myWhaleList);
        outState.putSerializable(ORDER_BY, orderBy);
    }

    /**
     * The onItemSelected function is called when an item in the spinner is selected.
     * It sets the minRecords variable to a value based on which item was selected.
     * If it's the first time that this function has been called, then updateOutput() will be called with false as its second parameter (meaning that no toast message should be displayed). Otherwise, updateOutput() will be called with true as its second parameter (meaning that a toast message should be displayed).
     *
     * @param  parent Identify the spinner that was selected
     * @param  view Get the view that was clicked on
     * @param  position Determine which item was selected
     * @param id Identify the row in the database that was selected

     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (position == 0) {
            minRecords = 5;
        } else if (position == 1) {
            minRecords = 10;
        } else if (position == 2) {
            minRecords = 50;
        } else if (position == 3) {
            minRecords = 200;
        }

        if (isFirstTime) {
            updateOutput(getCursor(), false);
            isFirstTime = false;
        } else {
            updateOutput(getCursor(), true);
        }

    }

    /**
     * The onNothingSelected function is called when the user selects nothing from a spinner.
     *
     *
     * @param parent Reference the adapterview that is being used
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}