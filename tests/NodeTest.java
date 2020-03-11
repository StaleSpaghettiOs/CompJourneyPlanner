import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void getName() throws Exception{
        Node testNode = new Node("name", "1", 1,2);
        assertEquals("name", testNode.getName());
    }

    @Test
    void getId() {
        Node testNode = new Node("name", "1", 1,2);
        assertEquals("1", testNode.getId());
    }

    @Test
    void getLat() {
        Node testNode = new Node("name", "1", 1,2);
        assertEquals(1, testNode.getLat());
    }

    @Test
    void getLon() {
        Node testNode = new Node("name", "1", 1,2);
        assertEquals(2, testNode.getLon());
    }

    @Test
    void addOutRoad() {
    }

    @Test
    void addInRoad() {
    }
}