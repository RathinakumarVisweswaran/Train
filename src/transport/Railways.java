package transport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by rathinakumar on 12/17/15.
 */
public class Railways {

    public static String config = "config.properties";

    public static void main(String[] args) throws IOException, IllegalInput, NoStationFound {

        Properties config = new Properties();
        FileInputStream input = new FileInputStream("config.properties");
        config.load(input);

        StationNetwork stationNetwork = new StationNetwork();
        stationNetwork.loadRailwaysNetworkFromFile(config.getProperty("networkFile"));

        Teller teller = new Teller(stationNetwork);

        System.out.print("Output #1: ");teller.printDistanceForRoute("A-B-C");
        System.out.print("Output #2: ");teller.printDistanceForRoute("A-D");
        System.out.print("Output #3: ");teller.printDistanceForRoute("A-D-C");
        System.out.print("Output #4: ");teller.printDistanceForRoute("A-E-B-C-D");
        System.out.print("Output #5: ");teller.printDistanceForRoute("A-E-D");

        System.out.print("Output #6: ");teller.printTripsCountWithMaxStopsFrom('C','C',3);
        System.out.print("Output #7: ");teller.printTripsCountWithExactStopsFrom('A','C',4);
        System.out.print("Output #8: ");teller.printShortestRouteDistanceFrom('A','C');
        System.out.print("Output #9: ");teller.printShortestRouteDistanceFrom('B','B');
        System.out.print("Output #10: ");teller.printTripsCountWithMaxDistanceFrom('C','C',30);
    }
}
