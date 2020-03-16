import java.util.*;


public class Trip {

    /** Fields */
    private ArrayList<Node> stopSequence;
    private ArrayList<Edge> edges;
    private String id;

    /** Constructor */
    public Trip(String id){
        this.stopSequence = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.id = id;

    }

    /**
     * This method will add a stop to the ArrayList of stops
     * and create a connection from the previous stop to it
     * if it's not the first stop
     * @param node
     */
    public void addStop(Node node){
        if(!stopSequence.isEmpty()){
            Node previousNode = stopSequence.get(stopSequence.size()-1);
            Edge edge = new Edge(id, previousNode, node);
            edges.add(edge);
            /* Add the edge into the inEdge list of the node and outEdge of the previous */
            node.addInEdge(edge);
            previousNode.addOutEdge(edge);
            //System.out.println("Stop \" " + node.getId() + "\" added to trip ID:  " + id + ", coming from Stop:  " + stopSequence.get(stopSequence.size()-1).getId());
        }
        stopSequence.add(node);

    }

    /**
     * Print the connections in the console
     * in the same format as the file but with arrows
     * Used to ensure stops are in correct order
     */
    public void showConnections(){
        System.out.print("Trip route: " + id + " Start: " + stopSequence.get(0).getId());
        for(Edge e : edges)
            System.out.print(" -> " + e.getDestinationNode().getId());

        System.out.print("\n");
    }


    public String getId() {
        return id;
    }

    /** Getter methods */
    public ArrayList<Node> getStopSequence(){
        return this.stopSequence;
    }

    public Collection<Edge> getEdges(){
        return edges;
    }







}
