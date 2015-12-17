package transport;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by rathinakumar on 12/7/15.
 */
public class StationNetwork {

    Map<Character, Station> stations =new HashMap<>();

    public void loadRailwaysNetworkFromFile(String networkFile) throws FileNotFoundException, IllegalInput {

        Scanner scanner = new Scanner(new File(networkFile));
        scanner.useDelimiter(",");
        Station startStation;
        while(scanner.hasNext())
        {
            String routeDetail = scanner.next().trim();

            if( ! isValidRouteDetail(routeDetail))
                throw new IllegalInput(routeDetail);

            Character start = routeDetail.charAt(0);
            Character destination  = routeDetail.charAt(1);
            int distance = Integer.valueOf(routeDetail.substring(2));

            if( ! stations.containsKey(start))
                stations.put(start, new Station(start));
            if( ! stations.containsKey(destination))
                stations.put(destination, new Station(destination));

            startStation = stations.get(start);
            startStation.addRoute(stations.get(destination), distance);
        }
        scanner.close();
    }

    /**
     * This is a wrapper function that calls another function to calculate the distance
     * @param start
     * @param destination
     * @return
     */
    public int getShortestRouteDistanceFrom(Station start, Station destination)
    {
        return getShortestRouteDistanceFrom(start, destination, new ArrayList<Station>());
    }

    /**
     * Recursion function to get the shortest distance between the stations
     * @param current
     * @param destination
     * @param visited
     * @return
     */
    public int getShortestRouteDistanceFrom(Station current, Station destination, List<Station> visited)
    {
        int minDistance = Integer.MAX_VALUE;
        int newDistance = 0;
        Collection<Station> nextStations = current.getNextStations();
        for(Station nextStation : nextStations)
        {
            if(visited.contains(nextStation))
                continue;

            visited.add(nextStation);
            if(nextStation.equals(destination))
                newDistance = current.getDistanceTo(nextStation);
            else
                newDistance = current.getDistanceTo(nextStation)+
                        getShortestRouteDistanceFrom(nextStation, destination, visited);

            visited.remove(nextStation);

            minDistance = (newDistance<minDistance)? newDistance:minDistance;
        }
        return minDistance;
    }

    /**
     * calculates the possible trips from start to destination with a maximum stops inbetween
     * @param start
     * @param destination
     * @param maxStops
     * @return
     */
    public int getTripsCountWithMaxStopsFrom(Station start, Station destination, int maxStops)
    {
        int totalPaths = 0;
        Collection<Station> nextStations = start.getNextStations();
        for(Station nextStation : nextStations)
        {
            if(nextStation.equals(destination) && maxStops>0)
                totalPaths++;
            if(maxStops>1)
                totalPaths += getTripsCountWithMaxStopsFrom(nextStation, destination, maxStops-1);
        }
        return totalPaths;
    }

    /**
     * calculates the possible trips from start to destination with exact stops inbetween
     * @param start
     * @param destination
     * @param exactStops
     * @return
     */
    public int getTripsCountWithExactStopsFrom(Station start, Station destination, int exactStops)
    {
        int totalPaths = 0;
        Collection<Station> nextStations = start.getNextStations();
        for(Station nextStation : nextStations)
        {
            if(nextStation.equals(destination) && exactStops==1)
                totalPaths++;

            if(exactStops>1)
                totalPaths += getTripsCountWithExactStopsFrom(nextStation, destination, exactStops-1);
        }
        return totalPaths;
    }

    /**
     * calculates the possible trips from start to destination with a maximum distance in-between
     * @param current
     * @param destination
     * @param maxDistance
     * @return
     */
    public int getTripsCountWithMaxDistanceFrom(Station current, Station destination, int maxDistance)
    {
        int totalPaths = 0;
        Collection<Station> nextStations = current.getNextStations();
        for(Station nextStation : nextStations)
        {
            if(nextStation.equals(destination) && current.getDistanceTo(nextStation)<maxDistance)
                totalPaths++;
            if(current.getDistanceTo(nextStation)<maxDistance)
                totalPaths += getTripsCountWithMaxDistanceFrom(nextStation, destination, maxDistance-current.getDistanceTo(nextStation));
        }
        return totalPaths;
    }

    /**
     * calculates the distance from start to destination
     * @param route
     * @return
     * @throws IllegalInput
     */
    public int getDistanceForRoute(List<Station> route) throws IllegalInput {

        int totalRouteDistance = 0;

        Station start, destination;
        int distance;
        for(int i=1; i<route.size(); i++)
        {
            start = route.get(i-1);
            destination = route.get(i);

            distance = start.getDistanceTo(destination);
            if(distance>0)
                totalRouteDistance+=distance;
            else
                return -1;
        }
        return totalRouteDistance;
    }

    /**
     * validates the incoming routeDetail
     * @param routeDetail
     * @return
     */
    public static boolean isValidRouteDetail(String routeDetail)
    {
        if( ! (Character.isLetter(routeDetail.charAt(0)) && Character.isLetter(routeDetail.charAt(1)))
                || ! routeDetail.substring(2).matches("[0-9]+"))
            return false;

        return true;
    }

    public Station getStationWithName(Character name)
    {
        for(Station station : stations.values())
        {
            if (station.getName() == name)
                return station;
        }
        return null;
    }

    public boolean hasStation(Station station)
    {
        return stations.containsValue(station);
    }

    public void clear()
    {
        stations.clear();
    }
}
