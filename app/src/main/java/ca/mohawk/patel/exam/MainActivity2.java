package ca.mohawk.patel.exam;
/**
 * I, Kruti Patel, 000857563 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 *
 *
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity  implements View.OnClickListener {

    public static final String tag = "==MainActivity2==";
    MyDbHelper mydbhelp = new MyDbHelper(this);

    WhaleData whaleData;

    TextView textViewWhaleInfo;
    Button buttonClose ,buttonHelp;
    /**
     * The onCreate function is called when the activity is created.
     * It sets up the layout and initializes all of the buttons and text views.

     *
     * @param  savedInstanceState Save the state of the activity
     *
     * @return Void
     *
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textViewWhaleInfo= (TextView) findViewById(R.id.textView);
        buttonClose = (Button) findViewById(R.id.buttonClose);
        buttonHelp = (Button) findViewById(R.id.buttonHelp);


        if(getIntent() != null) {
            whaleData = (WhaleData) getIntent().getSerializableExtra("WhaleData");

            if(whaleData != null) {
                // Calculate rarity
                int rarity = (int)100.0 / whaleData.getSightings();
                String rarityDescription = rarity + getRarity(rarity);
                textViewWhaleInfo.setText( "WhaleId = " + whaleData.getWhaleId() + "\n"
                        + "Sightings = " + whaleData.getSightings() + "\n"
                        + "Length = " + String.format("%.1f", whaleData.getLength()) + "meters\n"
                        + "Rarity = " + rarityDescription);
            }
        }


        buttonClose.setOnClickListener(this);
        buttonHelp.setOnClickListener(this);
    }
    /**
     * The getRarityDescription function takes a double rarity value and returns a string description of the rarity.
     *
     *
     * @param rarity Determine the rarity of the pokemon
     *
     * @return A string that describes the rarity of a car
     *
     *
     */
    private String getRarity(double rarity) {
        if (rarity < 10) {
            return "(seen every day)";
        } else if (rarity < 15) {
            return "( Common)";
        } else if (rarity < 25) {
            return "(Rare)";
        } else if (rarity < 50) {
            return "(Very rare)";
        } else if (rarity < 100) {
            return "(Extremely rare)";
        } else {
            return "(seen once)";
        }
    }



    /**
     * The onClick function is a function that allows the user to click on buttons and have them perform
     * certain actions. In this case, it allows the user to close out of the app or go back to the main
     * menu. It also has an intent that will allow for a new activity (MainActivity3) to be opened when
     * buttonHelp is clicked. This will allow for more information about how this app works and what it does.

     *
     * @param view Get the id of the button that was clicked
     *
     * @return A boolean
     *
     *
     */
    @Override
    public void onClick(View view) {
        if(view == buttonClose) {
            finish();
        }
        else if(view == buttonHelp){
            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            startActivity(intent);

        }
    }
}