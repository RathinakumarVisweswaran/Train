package transport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rathinakumar on 12/17/15.
 */
public class Teller {
    StationNetwork stationNetwork;

    public Teller(StationNetwork stationNetwork) {
        this.stationNetwork = stationNetwork;
    }

    public void printDistanceForRoute(String path) throws IllegalInput, NoStationFound {
        List<Station> route = getStationSequenceFor(path);
        int distance = stationNetwork.getDistanceForRoute(route);

        if(distance>0)
            System.out.println(distance);
        else
            System.out.println("NO SUCH ROUTE");
    }

    /**
     * parses the path and return the station sequence
     * @param path
     * @return
     */
    public List<Station> getStationSequenceFor(String path) throws IllegalInput, NoStationFound {
        if( ! path.matches("([A-Z]-)*[A-Z]"))
            throw new IllegalInput(path);

        List<Station> route = new ArrayList<>();
        path = path.replaceAll("-","");
        Station station;
        for(Character name : path.toCharArray())
        {
            station = stationNetwork.getStationWithName(name);
            if(station == null)
                throw new NoStationFound(name);
            route.add(station);
        }
        return route;
    }

    public void printTripsCountWithMaxStopsFrom(Character start, Character destination, int maxStops)
    {
        int count = stationNetwork.getTripsCountWithMaxStopsFrom(stationNetwork.getStationWithName(start), stationNetwork.getStationWithName(destination), maxStops);

        if(count>0)
            System.out.println(count);
        else
            System.out.println("Not Possible");
    }

    public void printTripsCountWithExactStopsFrom(Character start, Character destination, int exactStops)
    {
        int count = stationNetwork.getTripsCountWithExactStopsFrom(stationNetwork.getStationWithName(start), stationNetwork.getStationWithName(destination), exactStops);

        if(count>0)
            System.out.println(count);
        else
            System.out.println("Not Possible");
    }

    public void printShortestRouteDistanceFrom(Character from, Character to) {
        int distance = stationNetwork.getShortestRouteDistanceFrom(stationNetwork.getStationWithName(from), stationNetwork.getStationWithName(to));

        if(distance>0)
            System.out.println(distance);
        else
            System.out.println("Not Possible");
    }

    public void printTripsCountWithMaxDistanceFrom(Character from, Character to, int distanceLimit) {
        int distance = stationNetwork.getTripsCountWithMaxDistanceFrom(stationNetwork.getStationWithName(from), stationNetwork.getStationWithName(to), distanceLimit);
        if(distance>0)
            System.out.println(distance);
        else
            System.out.println("Not Possible");
    }
}
