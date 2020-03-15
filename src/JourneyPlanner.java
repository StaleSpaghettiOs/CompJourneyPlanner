import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.Buffer;
import java.util.*;
import java.util.List;

public class JourneyPlanner extends GUI {

    /** Fields */
    private Map<String, Node> nodesMap;//Used to identify nodes via String ID
    private ArrayList<Edge> edgeCollection;
    private Location origin;
    private double SCALE_FACTOR;

    /* Used for calculating scale */
    private double lowestX, lowestY = Double.POSITIVE_INFINITY;
    private double highestX, highestY = Double.NEGATIVE_INFINITY;


    public JourneyPlanner() {
        nodesMap = new HashMap<>();
        edgeCollection = new ArrayList<>();
        /* Arbitrary values assigned */
        origin = new Location(0, 0);
        SCALE_FACTOR = 0;
    }



    @Override
    protected void redraw(Graphics g) {
        g.setColor(Color.GRAY);
    }

    @Override
    protected void onClick(MouseEvent e) {

    }

    @Override
    protected void onSearch() {

    }

    @Override
    protected void onMove(Move m) {

    }


    /**
     * Load the data into the appropriate
     * structures (handled by separate methods)
     * @param stopFile
     * *            the stops.txt file
     * @param tripFile
     * *            the trips.txt file
     */
    @Override
    protected void onLoad(File stopFile, File tripFile){
        loadNodes(stopFile);
        loadTrips(tripFile);

    }

    public void loadNodes(File stopFile) {
        /* Create a buffered reader, and catch IOException */
        try {
            BufferedReader br = new BufferedReader(new FileReader(stopFile));
            String line; //String of the current entire line
            String[] StringArray;
            br.readLine(); //skip first line
            while ((line = br.readLine()) != null) {
                StringArray = line.split("\t"); //split line into separate Strings
                /* Construct a new node in order of (ID, Name, Lat, Lon) */
                Node node = new Node(StringArray[0], StringArray[1], Double.parseDouble(StringArray[2]), Double.parseDouble(StringArray[3]));
                nodesMap.put(node.getId(), node); //Put into nodesMap

                /* Now find the highest x,y and lowest x,y values in order to calculate origin and scale */
                if(node.getLocation().x < lowestX) lowestX = node.getLocation().x;
                if(node.getLocation().y < lowestY) lowestY = node.getLocation().y;
                if(node.getLocation().x > highestX) highestX = node.getLocation().x;
                if(node.getLocation().y > highestY) highestY = node.getLocation().y;
                /* Our origin is the top-left-most point that any points lay */
                origin = new Location(lowestX, highestY);
                /* Choose the smaller scale factor based on whether the width or the height has the wider range of nodes (more pixels to fit) */
                SCALE_FACTOR = Math.min(getDrawingAreaDimension().width / (highestX - lowestX), getDrawingAreaDimension().height / (highestY - lowestY));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTrips(File tripFile){
        try  {
            BufferedReader br = new BufferedReader(new FileReader(tripFile));

            String line; //String of the current entire line
            String[] StringArray;


            br.readLine(); //Skip the first line
            while((line = br.readLine()) != null){ //while not eof

                StringArray = line.split("\t"); //split the line into separate strings

                /* Construct a new Trip object with the trip ID */
                Trip trip = new Trip(StringArray[0]);

                /* Loop through each Stop until the end of the line */
                for(int s = 1; s < StringArray.length; s++){

                        Node node = nodesMap.get(StringArray[s]); //Add the Stop to the nodesMap
                        trip.addStop(node); //Add the Stop to the Trip (this also adds the in/outEdges to the nodes)
                        //edgeCollection.add(trip.getEdges().)


                }

                edgeCollection.addAll(trip.getEdges());
                trip.showConnections();


            }

            br.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[]args){
        new JourneyPlanner().onLoad(new File("data/stops.txt"), new File("data/trips.txt"));

    }


}
