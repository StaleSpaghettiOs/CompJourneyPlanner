public class Edge {

    /** Fields */
    private String tripId;
    private Node sourceNode;
    private Node destinationNode;

    /** Constructor */
    public Edge(String tripId, Node sourceNode, Node destinationNode){
        this.tripId = tripId;
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
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
}
