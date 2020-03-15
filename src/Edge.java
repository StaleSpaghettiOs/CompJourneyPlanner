public class Edge {

    /** Fields */
    private String tripId;
    private Node sourceNode;
    private Node destinationNode;
    private String edgeName;

    /** Constructor */
    public Edge(String tripId, Node sourceNode, Node destinationNode){
        this.tripId = tripId;
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.edgeName = sourceNode.getName() + "->" + destinationNode.getName();
    }

    /** Getter methods  */
    public String getTripId() {
        return tripId;
    }

    public Node getSourceNode() {
        return sourceNode;
    }

    public Node getDestinationNode() {
        return destinationNode;
    }

    public String getEdgeName() {
        return edgeName;
    }
}
