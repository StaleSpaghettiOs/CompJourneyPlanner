import java.util.ArrayList;
import java.util.List;

/**
 * Stop Object, contains two lists for outgoing and incoming
 * roads
 */
public class Node {

    /** Fields */
    private String name, id;
    private List<Edge> inEdges, outEdges;
    private double lat, lon;
    private Location location;


    /**
     * Constructor for the Stop object
     * @param name
     * @param id
     * @param lat
     * @param lon
     */
    public Node(String id, String name, double lat, double lon){
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.location = Location.newFromLatLon(lat, lon);
        this.inEdges = new ArrayList<>();
        this.outEdges = new ArrayList<>();
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

    public List<Edge> getInEdges() {
        return inEdges;
    }

    public List<Edge> getOutEdges() {
        return outEdges;
    }

    /**
     * add an outgoing connection to this Stop
     * @param e the edge to add
     */
    public void addOutEdge(Edge e){
        outEdges.add(e);
    }

    /**
     * add an incoming connection to this stop
     * @param e the edge to add
     */
    public void addInEdge(Edge e){
        inEdges.add(e);
    }
}
