package transport;

/**
 * Created by rathinakumar on 12/17/15.
 */
public class NoStationFound extends Exception {
    Character stationName;

    public NoStationFound(Character stationName) {
        this.stationName = this.stationName;
    }

    public void printMessage()
    {
        System.err.println("The station is not valid - "+ stationName);
    }

    public Character getStationName() {
        return stationName;
    }
}
