package ca.mohawk.patel.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * I, Kruti Patel, 000857563 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 *
 */
public class MainActivity3 extends AppCompatActivity {
    /**
     * Text view that displays message
     */
    private TextView message;
    /**
     * button to close activity.
     */
    private Button closeButton;

    /**
     * Loads the main activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *
     * Set the description of extra features in a message.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        message =findViewById(R.id.textView1);
        closeButton = findViewById(R.id.button4);
        message.setText("-This Beluga Whale Finder offers users insights into whale sightings and helps gauge the rarity of each sighting. Seeing 10 whales to gather at a time one day is very common. If you are lucky you get to see 100 whales at a time in a day. \n" +
                "\n"
                + "Actionbar background Change , Button Background Change ,Font for title change, Error message in EditTextView");
    }

    /**
     * onClick handler for Close button.
     * @param view
     * Close the activity.
     */
    public  void onClose(View view){
        finish();
    }

}