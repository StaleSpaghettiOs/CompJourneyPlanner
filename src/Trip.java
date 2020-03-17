import java.util.*;


public class Trip {

    /** Fields */
    private ArrayList<Stop> stopSequence;
    private ArrayList<Connection> connections;
    private String id;

    /** Constructor */
    public Trip(String id){
        this.stopSequence = new ArrayList<>();
        this.connections = new ArrayList<>();
        this.id = id;

    }

    /**
     * This method will add a stop to the ArrayList of stops
     * and create a connection from the previous stop to it
     * if it's not the first stop
     * @param stop
     */
    public void addStop(Stop stop){
        if(!stopSequence.isEmpty()){
            Stop previousStop = stopSequence.get(stopSequence.size()-1);
            Connection connection = new Connection(id, previousStop, stop);
            connections.add(connection);
            /* Add the connection into the inEdge list of the stop and outEdge of the previous */
            stop.addInEdge(connection);
            previousStop.addOutEdge(connection);
            //System.out.println("Stop \" " + stop.getId() + "\" added to trip ID:  " + id + ", coming from Stop:  " + stopSequence.get(stopSequence.size()-1).getId());
        }
        stopSequence.add(stop);

    }

    /**
     * Print the connections in the console
     * in the same format as the file but with arrows
     * Used to ensure stops are in correct order
     */
    public void showConnections(){
        System.out.print("Trip route: " + id + " Start: " + stopSequence.get(0).getId());
        for(Connection e : connections)
            System.out.print(" -> " + e.getDestinationStop().getId());

        System.out.print("\n");
    }


    public String getId() {
        return id;
    }

    /** Getter methods */
    public ArrayList<Stop> getStopSequence(){
        return this.stopSequence;
    }

    public Collection<Connection> getConnections(){
        return connections;
    }







}
