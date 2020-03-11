import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Trip {

    /** Fields */
    private Map<String, Node> nodesMap;
    private Collection<Edge> edges;
    private String id;

    /** Constructor */
    public Trip(String id, List<Node> nodes){
        nodesMap = new HashMap<String, Node>();
        this.id = id;
        //put the nodes (and ids) into the map of nodes
        for(Node n : nodes){
            this.nodesMap.put(n.getId(), n);
        }

    }

    /** Getter methods */
    public Map<String, Node> getNodes(){
        return this.nodesMap;
    }


    /** Returns true if trip contains node of that ID */
    public boolean hasNode(String id){
        return nodesMap.containsKey(id);
    }
    /** Return the node in the trip by it's ID, only if the node exists */
    public Node getNodeById(String id){
        return hasNode(id) ? nodesMap.get(id) : null;
    }




}
