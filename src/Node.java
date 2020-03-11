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


    /**
     * Constructor for the Stop object
     * @param name
     * @param id
     * @param lat
     * @param lon
     */
    public Node(String name, String id, double lat, double lon){
        this.name = name;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
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



    /**
     * add an outgoing road to this Stop to a specified Stop
     * @param r
     * @param dest
     */
    public void addOutEdge(Edge r, Node dest){
        outEdges.add(r);
        //dest.addInEdge(r, this);
    }

    /**
     * add an incomming road to this Stop from a specified Stop
     * @param r
     * @param origin
     */
    public void addInEdge(Edge r, Node origin){
        inEdges.add(r);
        //dest.addOutEdge(r, this);
    }
}
