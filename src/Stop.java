import java.util.ArrayList;
import java.util.List;

/**
 * Stop object, contains two lists for outgoing and incoming
 * roads
 * @author Jakob Coker
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

    /** Getters */
    public String getName(){
        return this.name;
    }
    public String getId(){
        return this.id;
    }
    public double getLat(){
        return this.lat;
    }
    public double getLon(){
        return this.lon;
    }
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
    public void addOutConnection(Connection c){
        outConnections.add(c);
    }

    /**
     * add an incoming connection to this stop
     * @param c the edge to add
     */
    public void addInConnection(Connection c){
        inConnections.add(c);
    }
}
