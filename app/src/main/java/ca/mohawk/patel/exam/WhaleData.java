package ca.mohawk.patel.exam;

import java.io.Serializable;

/**
 * I, Kruti Patel, 000857563 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 *
  *
 */
public class WhaleData implements Serializable {
    /**
     * whalee id
     */
    private String whaleId;
    /**
     * Sihhting of wheal
     */
    private int sightings;
    /**
     * length of whale
     * */

    private  double length;
      /**
     * The getWhaleId function returns the whaleId of a Whale object.
     *
     * @return The whaleid variable
     *

     */
    public String getWhaleId() {
        return whaleId;
    }

    /**
     * The setWhaleId function sets the whaleId variable to a new value.
     * @param  whaleId Set the value of the whaleid variable
     *

     */
    public void setWhaleId(String whaleId) {
        this.whaleId = whaleId;
    }

    /**
     * The getSightings function returns the number of sightings for a given animal.

     * @return The number of times the animal has been seen
     *
     */
    public int getSightings() {
        return sightings;
    }

    /**
     * The setSightings function sets the number of sightings for a given animal.
     *
     *
     * @param  sightings Set the sightings variable in the class
     *

     */
    public void setSightings(int sightings) {
        this.sightings = sightings;
    }

    /**
     * The getLength function returns the length of the line segment.
     *

     * @return The length of the rectangle
     *

     */
    public double getLength() {
        return length;
    }

    /**
     * The setLength function sets the length of the rectangle.
     *
     *
     * @param length Set the length of the rectangle
     *

     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * The toString function returns a string representation of the whale object.
     *

     * @return The whale id and the length of the whale
     *
     */
    @Override
    public String toString() {
        return "Whale ID = " + whaleId + "            " + String.format("%.1f", length) + " meters";
    }
}
