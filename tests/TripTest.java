import org.junit.jupiter.api.Test;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

class TripTest {
    @Test
    void hasNode() throws Exception{
        List<Node> testNodes = new ArrayList<>();

        testNodes.add(new Node("a", "a", 1, 2));
        testNodes.add(new Node("b", "b", 3, 4));
        testNodes.add(new Node("c", "c", 5, 6));
        testNodes.add(new Node("d", "d", 7, 8));

        Trip testTrip = new Trip("t1", testNodes);

        assertEquals(true, testTrip.hasNode("a"));
        assertEquals(false, testTrip.hasNode("f"));

    }
    @Test
    void hasNode_2() throws Exception{
        List<Node> testNodes = new ArrayList<>();

        testNodes.add(new Node("a", "a", 1, 2));
        testNodes.add(new Node("b", "b", 3, 4));
        testNodes.add(new Node("c", "c", 5, 6));
        testNodes.add(new Node("d", "d", 7, 8));

        Trip testTrip = new Trip("t2", testNodes);

        assertEquals(true, testTrip.hasNode("c"));
        assertEquals(false, testTrip.hasNode("z"));

    }
    @Test
    void getNode() throws Exception{
        List<Node> testNodes = new ArrayList<>();
        Node testNode = new Node("a", "1", 1, 2);
        testNodes.add(testNode);

        Trip testTrip = new Trip("t3", testNodes);

        assertEquals(testNode, testTrip.getNodeById("1"));
    }
}