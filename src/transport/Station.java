package transport;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rathinakumar on 12/7/15.
 */
public class Station
{
    Character name;
    Map<Station, Integer> routes;

    public Station(Character name) {
        this.name = name;
        this.routes = new HashMap<>();
    }

    public boolean addRoute(Station to, int distance)
    {
        routes.put(to, distance);
        return true;
    }

    public int getDistanceTo(Station destination)
    {
        if(routes.containsKey(destination))
            return routes.get(destination);
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        return name.equals(station.name);

    }

    public Collection<Station> getNextStations()
    {
        return routes.keySet();
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public Character getName() {
        return name;
    }
}
