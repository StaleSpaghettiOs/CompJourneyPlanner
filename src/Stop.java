import java.util.ArrayList;
import java.util.List;

/**
 * Stop Object, contains two lists for outgoing and incoming
 * roads
 */
public class Stop {

    /** Fields */
    private String name, id;
    private List<Connection> inConnections, outConnections;
    private double lat, lon;
    private Location location;


    /**
     * Constructor for the Stop object
     * @param name
     * @param id
     * @param lat
     * @param lon
     */
    public Stop(String id, String name, double lat, double lon){
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.location = Location.newFromLatLon(lat, lon);
        this.inConnections = new ArrayList<>();
        this.outConnections = new ArrayList<>();
    }


    /**
     * Returns the name of the Stop
     * @return name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the ID of the Stop
     * @return id
     */
    public String getId(){
        return this.id;
    }

    /**
     * Returns the latitude and longitude of the Stop
     * @return Location
     */
    public double getLat(){
        return this.lat;
    }
    public double getLon(){
        return this.lon;
    }

    /* Returns the location of the stop */
    public Location getLocation(){
        return this.location;
    }

    public List<Connection> getInConnections() {
        return inConnections;
    }

    public List<Connection> getOutConnections() {
        return outConnections;
    }

    /**
     * add an outgoing connection to this Stop
     * @param c the edge to add
     */
    public void addOutEdge(Connection c){
        outConnections.add(c);
    }

    /**
     * add an incoming connection to this stop
     * @param c the edge to add
     */
    public void addInEdge(Connection c){
        inConnections.add(c);
    }
}
