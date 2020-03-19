import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class JourneyPlanner extends GUI {

    /** Fields */
    private Map<String, Stop> stopMap;//Used to identify nodes via String ID
    private ArrayList<Stop> stopList; //my attempt at fixing ConcurrentModificationException
    private ArrayList<Connection> connectionCollection; //Used to store edges for drawing/highlighting
    private ArrayList<Trip> tripCollection; //used to store trips for outputting ones passing through nodes
    private Trie trie;
    private Location origin;
    private double scale;
    private double ZOOM_FACTOR = 1.75; //Modifiable zoom factor
    private int stopCount, connectionCount, tripCount;

    /* Used to calculate new origins */
    private Location topLeft, topRight, botLeft, botRight;
    private double width, height;

    /* Used to decide nodes to highlight */
    private Stop closestStop; //Either single highlighted node or the node closest to mouse click
    private ArrayList<Trip> highlightedTrips;
    private List<Stop> prefixStops;


    /* Used for calculating scale */
    private double lowestX, lowestY = Double.POSITIVE_INFINITY;
    private double highestX, highestY = Double.NEGATIVE_INFINITY;

    /* Used to drag screen */
    private double oldX, oldY, newX, newY;
    private boolean isDrag = false;

    /* Used for normal and highlighted pixel width and height (and keeping them centered) */
    private int pWidth = 6;
    private int pHeight = 6;
    private int hPWidth = 8;
    private int hPHeight = 8;

    /** Constructor */
    public JourneyPlanner() {
        this.stopMap = new HashMap<>();
        this.connectionCollection = new ArrayList<>();
        this.tripCollection = new ArrayList<>();
        this.prefixStops = new ArrayList<>();
        this.highlightedTrips = new ArrayList<>();
        this.trie = new Trie();
        this.stopList = new ArrayList<>();
    }

    /**
     * Implementation of redraw method
     * manipulates the graphics objects
     * to do all drawing of stops&edges
     * @param g graphics object
     */
    protected void redraw(Graphics g) {

        /* Only purpose is to change stroke width 2 make it prettier */
        Graphics2D g2 = (Graphics2D) g;

        /** Draw all the connections */
        g2.setColor(Color.GRAY);
        for (int i = 0; i < connectionCollection.size(); i++) {
            g2.setStroke(new BasicStroke(2));
            g.drawLine((int) connectionCollection.get(i).getSourceStop().getLocation().asPoint(origin, scale).getX(),
                    (int) connectionCollection.get(i).getSourceStop().getLocation().asPoint(origin, scale).getY(),
                    (int) connectionCollection.get(i).getDestinationStop().getLocation().asPoint(origin, scale).getX(),
                    (int) connectionCollection.get(i).getDestinationStop().getLocation().asPoint(origin, scale).getY());

        }
        /** Draw all the stops */
        for (int s = 0; s < stopList.size(); s++) {
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            g.fillOval((int) (stopList.get(s).getLocation().asPoint(origin, scale).getX())-pWidth/2,
                    (int) (stopList.get(s).getLocation().asPoint(origin, scale).getY())-pHeight/2, pWidth, pHeight);
        }



        /** Draw the selected stop */
        if (stopList.contains(closestStop)) {
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3));
            g.fillOval((int) closestStop.getLocation().asPoint(origin, scale).getX() - hPWidth/2,
                    (int) closestStop.getLocation().asPoint(origin, scale).getY()-hPHeight/2, hPWidth, hPHeight);
        }

        /** Highlight all connections passing through the selected stop */
        if (!highlightedTrips.isEmpty()) {
            g.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(3));
            for (Trip t : highlightedTrips) {
                for (Connection e : t.getConnections()) {
                    g.drawLine((int) e.getSourceStop().getLocation().asPoint(origin, scale).getX(),
                            (int) e.getSourceStop().getLocation().asPoint(origin, scale).getY(),
                            (int) e.getDestinationStop().getLocation().asPoint(origin, scale).getX(),
                            (int) e.getDestinationStop().getLocation().asPoint(origin, scale).getY());
                }
            }
        }

        /** Highlight all stops matching searched prefix */
        if (!prefixStops.isEmpty()) {
            for (Stop n : prefixStops) {
                g2.setStroke(new BasicStroke(3));
                g2.setColor(Color.RED);
                g2.setPaintMode();
                g.fillOval((int) n.getLocation().asPoint(origin, scale).getX()-hPWidth/2,
                        (int) n.getLocation().asPoint(origin, scale).getY()-hPHeight/2, hPWidth, hPHeight);
            }
        }

    }

    /**
     * onClick implementation
     * Highlights stops when they're clicked.
     * Checks if the user isn't dragging first.
     * @param e MouseReleased event
     */
    @Override
    protected void onClick(MouseEvent e) {
        /* Only do onClick if we aren't dragging */
        if(!isDrag) {
            highlightedTrips.clear(); //Clear highlighted trips and stops from the search
            prefixStops.clear();
            Point mousePoint = new Point(e.getX(), e.getY());
            Location mouseLoc = Location.newFromPoint(mousePoint, origin, scale);
            double closestDist = Double.POSITIVE_INFINITY;
            closestStop = stopList.get(0);
            for (Stop n : stopMap.values()) {
                double currentDist = n.getLocation().distance(mouseLoc);
                if (currentDist < closestDist) {
                    closestDist = currentDist;
                    closestStop = n;
                }
            }
            for (Trip t : tripCollection) {
                if (t.getStopSequence().contains(closestStop)) {
                    highlightedTrips.add(t);
                }
            }
            getTextOutputArea().removeAll();
            printInfo();
        }
        isDrag = false;
    }

    /**
     * Like mouseClicked, except occurs on press
     * and not release. Saves location upon clicking
     * on the display - used for dragging.
     * @param e MousePressed event
     */
    @Override
    public void onMousePress(MouseEvent e){
        oldX = e.getX();
        oldY = e.getY();
    }


    /**
     * Mouse drag implementation, sets the origin
     * to the new x,y location (inverted x axis)
     * of the mouse and then sets oldX,oldY to that.
     * @param e
     */
    @Override
    protected void onMouseDrag(MouseEvent e){
        newX = e.getX();
        newY = e.getY();
        origin = origin.moveBy((oldX - newX)/scale, (newY-oldY)/scale);
        oldX = e.getX();
        oldY = e.getY();
        isDrag = true;

    }

    /**
     * My implementation of a scroll wheel for challenge
     * part of improving UI. Calls onMove for zoom-in/out
     * depending on the direction of the scroll to remain
     * consistent with my zoom parameters.
     * @param e
     */
    @Override
    protected void onMouseWheelMove(MouseWheelEvent e) {
        if(e.getWheelRotation() < 0){
            onMove(Move.ZOOM_IN);
        }
        if(e.getWheelRotation() > 0){
            onMove(Move.ZOOM_OUT);
        }
    }

    /**
     * Implementation of onSearch() method, which searches and
     * displays all Stops matching the prefix searched.
     * If the searched word exactly matches a stop, or the prefix
     * has only one Stop matching it, it displays that
     * Stop's info and trips going through it.
     */
    @Override
    protected void onSearch() {
        /* Reset the highlighted trips */
        highlightedTrips.clear();
        /* Set the current search string to a char array*/
        String word = getSearchBox().getText();
        char[] searchWord = word.toCharArray();
        /* Reset the highlighted nodes of matching prefixes */
        prefixStops = new ArrayList<>();
        Stop queryStop;

        /* If the search is a prefix */
        if(trie.queryPrefix(searchWord)){

            /* If the word exactly matches a stop */
            if(trie.queryWord(searchWord)){
                prefixStops.addAll(trie.get(searchWord)); //add to list as stops can have the same name
                for(Stop s : prefixStops){
                    getTextOutputArea().append("\n\nStop found: "+s.getName()+" (" + s.getId()+")    ");
                    for(Trip t : tripCollection){
                        if(t.getStopSequence().contains(s)){
                            highlightedTrips.add(t);
                        }
                    }
                    getTextOutputArea().append("\nTrips going through this stop:");
                    for(Trip t : highlightedTrips){
                        getTextOutputArea().append(" "+t.getId()+",");
                    }
                }
            }else{ //The Stop(s) are further down

                prefixStops.addAll(trie.getAll(searchWord));
                getTextOutputArea().append("\n\nStops matching search: ");
                for(Stop s : prefixStops){
                    getTextOutputArea().append(s.getName()+" (" + s.getId()+")    ");
                }
            }
        }else{
            getTextOutputArea().append("\nNo stops found.\n");
        }


    }

    /**
     * Fill the TextOutputArea with information on the selected stop
     * and all trips going through it. Cleans up code in several methods.
     */
    public void printInfo(){
        getTextOutputArea().append("\nFound Stop: " + closestStop.getName()+ ", ID-"+ closestStop.getId() + "\nTrips going through this stop: ");
        for(Trip t : tripCollection){
            if(t.getStopSequence().contains(closestStop)){
                getTextOutputArea().append(t.getId());
                getTextOutputArea().append(", ");
            }

        }
    }

    /**
     * Controls the logic behind what to do when
     * the user selects the buttons (and when a
     * scroll event is passed to it), such as zooming,
     * moving north,east,south,west.
     * @param m the move enum
     */
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
        loadStops(stopFile);
        loadTrips(tripFile);
        redraw();
        System.out.println("Done!");
        System.out.println(stopCount + " stops loaded");
        System.out.println(tripCount + " trips loaded");
        System.out.println(connectionCount + " connections created");
    }

    /**
     * Handles all of the processing of the stops.txt
     * file and creating the stop objects
     * @param stopFile
     */
    public void loadStops(File stopFile) {
        /* Create a buffered reader, and catch IOException */
        try {
            BufferedReader br = new BufferedReader(new FileReader(stopFile));
            String line; //String of the current entire line
            String[] StringArray;
            br.readLine(); //skip first line
            while ((line = br.readLine()) != null) {
                StringArray = line.split("\t"); //split line into separate Strings
                /* Construct a new stop in order of (ID, Name, Lat, Lon) */
                Stop stop = new Stop(StringArray[0], StringArray[1], Double.parseDouble(StringArray[2]), Double.parseDouble(StringArray[3]));
                stopCount+=1;
                stopMap.put(stop.getId(), stop); //Put into nodesMap
                stopList.add(stop);
                trie.add(StringArray[1].toCharArray(), stop); //Add word to TrieNode


                /* Now find the highest x,y and lowest x,y values in order to calculate origin and scale */
                if(stop.getLocation().x < lowestX) {
                    lowestX = stop.getLocation().x;
                }
                if(stop.getLocation().y < lowestY) {
                    lowestY = stop.getLocation().y;
                }
                if(stop.getLocation().x > highestX) {
                    highestX = stop.getLocation().x;
                }
                if(stop.getLocation().y > highestY) {
                    highestY = stop.getLocation().y;
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

    /**
     * Handles all of the processing of the trips.txt
     * file and creates Trip objects. Creates edge objects
     * using trip.addStop() with stops generated from loadNodes
     * @param tripFile the trips.txt file
     *
     */
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
                tripCount += 1;
                tripCollection.add(trip);
                /* Loop through each Stop until the end of the line */
                for(int s = 1; s < StringArray.length; s++){
                        Stop stop = stopMap.get(StringArray[s]); //Get the Stop from the stopMap
                        trip.addStop(stop); //Add the Stop to the Trip (this also adds the in/outEdges to the nodes)
                        if(s > 1) connectionCount+=1;
                }
                connectionCollection.addAll(trip.getConnections());
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
