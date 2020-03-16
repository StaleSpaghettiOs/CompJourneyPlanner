import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class JourneyPlanner extends GUI {

    /** Fields */
    private Map<String, Node> nodesMap;//Used to identify nodes via String ID
    private ArrayList<Edge> edgeCollection;
    private ArrayList<Trip> tripCollection;
    private Location origin;
    private double scale;
    private double ZOOM_FACTOR = 1.75;
    private Location topLeft, topRight, botLeft, botRight;
    private double width, height;


    /* Used for calculating scale */
    private double lowestX, lowestY = Double.POSITIVE_INFINITY;
    private double highestX, highestY = Double.NEGATIVE_INFINITY;


    public JourneyPlanner() {
        nodesMap = new HashMap<>();
        edgeCollection = new ArrayList<>();
        tripCollection = new ArrayList<>();



    }



    @Override
    protected void redraw(Graphics g) {
        g.setColor(Color.CYAN);
        for(Node n : nodesMap.values()){
            g.fillOval((int)(n.getLocation().asPoint(origin, scale).getX()), (int)(n.getLocation().asPoint(origin, scale).getY()), 4,4);

        }

        g.setColor(Color.GRAY);
        int x1,y1,x2,y2;
        for(int i = 0; i < edgeCollection.size(); i++){
            x1 = (int) edgeCollection.get(i).getSourceNode().getLocation().asPoint(origin, scale).getX();
            y1 = (int) edgeCollection.get(i).getSourceNode().getLocation().asPoint(origin, scale).getY();
            x2 = (int) edgeCollection.get(i).getDestinationNode().getLocation().asPoint(origin, scale).getX();
            y2 = (int) edgeCollection.get(i).getDestinationNode().getLocation().asPoint(origin, scale).getY();
            g.drawLine(x1, y1, x2, y2);
        }


        g.setColor(Color.BLUE);

    }

    @Override
    protected void onClick(MouseEvent e) {
        Point mousePoint = new Point(e.getX(),e.getY());
        Location mouseLoc = Location.newFromPoint(mousePoint, origin, scale);
        double closestDist = Double.POSITIVE_INFINITY;
        Node closestNode = nodesMap.get("OOBUS003");
        for(Node n : nodesMap.values()){
            double currentDist = n.getLocation().distance(mouseLoc);
            if(currentDist < closestDist){
                closestDist = currentDist;
                closestNode = n;
            }
        }

        getTextOutputArea().append("Stop ID: "+ closestNode.getId() + " Stop Name: " +closestNode.getName() + "\n");
        getTextOutputArea().append("Trips going through this stop: \n");
        for(Trip t : tripCollection){
            if(t.getStopSequence().contains(closestNode)){
                getTextOutputArea().append(t.getId() + ", ");
            }
        }

    }

    @Override
    protected void onSearch() {

    }

    @Override
    protected void onMove(Move m) {

        switch (m) {
            case ZOOM_IN:
                /* The 4 corners are measured in reference to the origin in terms of distance */
                topLeft = origin;
                topRight = topLeft.moveBy(getDrawingAreaDimension().getWidth()/scale, 0);
                botLeft = topLeft.moveBy(0, -(getDrawingAreaDimension().getHeight()/scale));
                botRight = topRight.moveBy(0, -(getDrawingAreaDimension().getHeight()/scale));
                /* The width and height of our displayed section can now be found */
                width = topRight.x - topLeft.x;
                height = botLeft.y - topLeft.y;

                /*Move the origin to the new origin of the zoomed in section and increase the scale */
                origin = origin.moveBy((width-width/ZOOM_FACTOR)/2, (height-height/ZOOM_FACTOR)/2);
                scale *= ZOOM_FACTOR;
                break;
            case ZOOM_OUT:
                /* The 4 corners are measured in reference to the origin in terms of distance */
                topLeft = origin;
                topRight = topLeft.moveBy(getDrawingAreaDimension().getWidth()/scale, 0);
                botLeft = topLeft.moveBy(0, -(getDrawingAreaDimension().getHeight()/scale));
                botRight = topRight.moveBy(0, -(getDrawingAreaDimension().getHeight()/scale));

                width /* The width and height of our displayed section can now be found */= topRight.x - topLeft.x;
                height = botLeft.y - topLeft.y;
                /*Move the origin to the new origin of the zoomed out section and decrease the scale */
                origin = origin.moveBy((width-width*ZOOM_FACTOR)/2, (height-height*ZOOM_FACTOR)/2);
                scale /= ZOOM_FACTOR;
                break;

            /**
             * The movement is a constant amount of pixels in a direction
             * given by the distance to origin divided by the scale.
             * i.e, the more zoomed in the slower we pan across.
             */
            case NORTH:
                origin = origin.moveBy(0, 10/scale);
                break;
            case EAST:
                origin = origin.moveBy(10/scale, 0);
                break;
            case SOUTH:
                origin = origin.moveBy(0, -10/scale);
                break;
            case WEST:
                origin = origin.moveBy(-10/scale, 0);
        }
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
                if(node.getLocation().x < lowestX) {
                    lowestX = node.getLocation().x;
                }
                if(node.getLocation().y < lowestY) {
                    lowestY = node.getLocation().y;
                }
                if(node.getLocation().x > highestX) {
                    highestX = node.getLocation().x;
                }
                if(node.getLocation().y > highestY) {
                    highestY = node.getLocation().y;
                }
                /* Our origin is the top-left-most point that any points lay */
                origin = new Location(lowestX, highestY);

                /* Choose the smaller scale factor based on whether the width or the height has the wider range of nodes (more pixels to fit) */
                scale = Math.min(this.getDrawingAreaDimension().getWidth() / (highestX - lowestX), this.getDrawingAreaDimension().getHeight() / (highestY - lowestY));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
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
                tripCollection.add(trip);
                /* Loop through each Stop until the end of the line */
                for(int s = 1; s < StringArray.length; s++){
                        Node node = nodesMap.get(StringArray[s]); //Add the Stop to the nodesMap
                        trip.addStop(node); //Add the Stop to the Trip (this also adds the in/outEdges to the nodes)

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
