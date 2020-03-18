/**
 * Connection object, stores the source
 * and destination stop associated with
 * that trip ID
 * @author Jakob Coker
 */
public class Connection {

    /** Fields */
    private String tripId;
    private Stop sourceStop;
    private Stop destinationStop;

    /** Constructor */
    public Connection(String tripId, Stop sourceStop, Stop destinationStop){
        this.tripId = tripId;
        this.sourceStop = sourceStop;
        this.destinationStop = destinationStop;
    }

    /** Getter methods  */
    public String getTripId() {
        return tripId;
    }

    public Stop getSourceStop() {
        return sourceStop;
    }

    public Stop getDestinationStop() {
        return destinationStop;
    }

}
