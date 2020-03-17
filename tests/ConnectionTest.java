import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionTest {
    @Test
    void getId() throws Exception{
        Connection testConnection = new Connection("i1_1", new Stop("s", "1", 52.0, 24.2), new Stop("s", "2", 52.0, 24.2));
        assertEquals("i1_1", testConnection.getTripId());
    }
    @Test
    void getSourceNode() throws Exception{
        Stop testSourceStop = new Stop("test", "1", 12.0,12.0);
        Stop testDestStop = new Stop("test2", "2", 50.0, 50.0);
        Connection testConnection = new Connection("i1_1", testSourceStop, testDestStop);
        assertEquals(testSourceStop, testConnection.getSourceStop());
    }
    @Test
    void getDestinationNode() throws Exception{
        Stop testSourceStop = new Stop("test", "1", 12.0,12.0);
        Stop testDestStop = new Stop("test2", "2", 50.0, 50.0);
        Connection testConnection = new Connection("i1_1", testSourceStop, testDestStop);
        assertEquals(testDestStop, testConnection.getDestinationStop());
    }
    @Test
    void getEdgeStartLatLon() throws Exception{
        Stop testSourceStop = new Stop("test", "1", 12.0,12.0);
        Stop testDestStop = new Stop("test2", "2", 50.0, 50.0);
        Connection testConnection = new Connection("i1_1", testSourceStop, testDestStop);
        assertEquals(12.0, testConnection.getSourceStop().getLat());
        assertEquals(12.0, testConnection.getSourceStop().getLon());
    }
    @Test
    void getEdgeEndLatLon() throws Exception{
        Stop testSourceStop = new Stop("test", "1", 12.0,12.0);
        Stop testDestStop = new Stop("test2", "2", 50.0, 50.0);
        Connection testConnection = new Connection("i1_1", testSourceStop, testDestStop);
        assertEquals(50.0, testConnection.getDestinationStop().getLat());
        assertEquals(50.0, testConnection.getDestinationStop().getLon());
    }
}