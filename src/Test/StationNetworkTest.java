package Test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import transport.*;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by rathinakumar on 12/7/15.
 */
public class StationNetworkTest {

    StationNetwork stationNetwork;
    /*Teller teller;
    Railways railways;*/
    File testFile;

    @Before
    public void setUp() throws Exception {
        testFile = new File("testFile.txt");
        FileWriter f = new FileWriter(testFile);
        f.write("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        f.close();

        stationNetwork = new StationNetwork();
        stationNetwork.loadRailwaysNetworkFromFile("testFile.txt");
        /*teller = new Teller(stationNetwork);
        railways = new Railways();*/
    }

    @After
    public void teardown()
    {
        stationNetwork.clear();
        testFile.delete();
    }

    @Test
    public void testIsValidRouteDetail() throws Exception {

        String routingDetails = "AB5";
        assertTrue(StationNetwork.isValidRouteDetail(routingDetails));

        routingDetails = "A65";
        assertFalse(StationNetwork.isValidRouteDetail(routingDetails));
    }

    @Test
    public void testLoadRailwaysNetwork() throws Exception {
        StationNetwork stationNetwork = new StationNetwork();
        File trueTest = new File("trueCase.txt");
        FileWriter f = new FileWriter(trueTest);
        f.write("AB5, BC4");
        f.close();

        stationNetwork.loadRailwaysNetworkFromFile("trueCase.txt");
        Station station = new Station('A');
        assertTrue(stationNetwork.hasStation(station));
        station = new Station('B');
        assertTrue(stationNetwork.hasStation(station));
        station = new Station('C');
        assertTrue(stationNetwork.hasStation(station));
        station = new Station('D');
        assertFalse(stationNetwork.hasStation(station));

        stationNetwork.clear();
        trueTest.delete();
    }

    @Test
    public void testGetDistanceForRoute() throws Exception {

        Station A = stationNetwork.getStationWithName('A'),
                B = stationNetwork.getStationWithName('B'),
                C = stationNetwork.getStationWithName('C'),
                D = stationNetwork.getStationWithName('D'),
                E = stationNetwork.getStationWithName('E');

        List<Station> stationPath = new ArrayList<>();
        stationPath.add(A);
        stationPath.add(B);
        stationPath.add(C);
        assertTrue(stationNetwork.getDistanceForRoute(stationPath)==9); // A-B-C
        stationPath.remove(B);
        stationPath.remove(C);
        stationPath.add(D);
        assertTrue(stationNetwork.getDistanceForRoute(stationPath)==5); //A-D
        stationPath.add(C);
        assertTrue(stationNetwork.getDistanceForRoute(stationPath)==13); //A-D-C
        stationPath.remove(D);
        stationPath.remove(C);
        stationPath.add(E);
        stationPath.add(B);
        stationPath.add(C);
        stationPath.add(D);
        assertTrue(stationNetwork.getDistanceForRoute(stationPath)==22);//A-E-B-C-D
        stationPath.remove(B);
        stationPath.remove(C);
        assertTrue(stationNetwork.getDistanceForRoute(stationPath)==-1); //A-E-D
    }

    @Test
    public void testGetTripsCountWithMaxStops() throws Exception {
        Station station = stationNetwork.getStationWithName('C');
        assertTrue(stationNetwork.getTripsCountWithMaxStopsFrom(station, station, 3)==2 );
    }

    @Test
    public void testGetTripsCountWithExactStops() throws Exception {
        Station start = stationNetwork.getStationWithName('A'), dest = stationNetwork.getStationWithName('C');
        assertTrue(stationNetwork.getTripsCountWithExactStopsFrom(start, dest, 4)==3 );
    }

    @Test
    public void testGetTripsCountWithMaxDistance() throws Exception {
        Station station = stationNetwork.getStationWithName('C');
        assertTrue(stationNetwork.getTripsCountWithMaxDistanceFrom(station, station, 30)==7 );
    }

    @Test
    public void testGetShortestRouteDistanceFrom() throws Exception{
        Station start = stationNetwork.getStationWithName('A');
        Station dest = stationNetwork.getStationWithName('C');
        assertTrue(stationNetwork.getShortestRouteDistanceFrom(start, dest)==9 );
    }


}


