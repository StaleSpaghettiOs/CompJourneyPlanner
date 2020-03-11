import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    @Test
    void getId() throws Exception{
        Edge testEdge = new Edge("i1_1", new Node("s", "1", 52.0, 24.2), new Node("s", "2", 52.0, 24.2));
        assertEquals("i1_1", testEdge.getTripId());
    }
    @Test
    void getSourceNode() throws Exception{
        Node testSourceNode = new Node("test", "1", 12.0,12.0);
        Node testDestNode = new Node("test2", "2", 50.0, 50.0);
        Edge testEdge = new Edge("i1_1", testSourceNode, testDestNode);
        assertEquals(testSourceNode, testEdge.getSourceNode());
    }
    @Test
    void getDestinationNode() throws Exception{
        Node testSourceNode = new Node("test", "1", 12.0,12.0);
        Node testDestNode = new Node("test2", "2", 50.0, 50.0);
        Edge testEdge = new Edge("i1_1", testSourceNode, testDestNode);
        assertEquals(testDestNode, testEdge.getDestinationNode());
    }
    @Test
    void getEdgeStartLatLon() throws Exception{
        Node testSourceNode = new Node("test", "1", 12.0,12.0);
        Node testDestNode = new Node("test2", "2", 50.0, 50.0);
        Edge testEdge = new Edge("i1_1", testSourceNode, testDestNode);
        assertEquals(12.0, testEdge.getSourceNode().getLat());
        assertEquals(12.0, testEdge.getSourceNode().getLon());
    }
    @Test
    void getEdgeEndLatLon() throws Exception{
        Node testSourceNode = new Node("test", "1", 12.0,12.0);
        Node testDestNode = new Node("test2", "2", 50.0, 50.0);
        Edge testEdge = new Edge("i1_1", testSourceNode, testDestNode);
        assertEquals(50.0, testEdge.getDestinationNode().getLat());
        assertEquals(50.0, testEdge.getDestinationNode().getLon());
    }
}