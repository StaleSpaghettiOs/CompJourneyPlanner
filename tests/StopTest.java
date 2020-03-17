import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StopTest {

    @Test
    void getName() throws Exception{
        Stop testStop = new Stop("name", "1", 1,2);
        assertEquals("name", testStop.getName());
    }

    @Test
    void getId() {
        Stop testStop = new Stop("name", "1", 1,2);
        assertEquals("1", testStop.getId());
    }

    @Test
    void getLat() {
        Stop testStop = new Stop("name", "1", 1,2);
        assertEquals(1, testStop.getLat());
    }

    @Test
    void getLon() {
        Stop testStop = new Stop("name", "1", 1,2);
        assertEquals(2, testStop.getLon());
    }

    @Test
    void addOutRoad() {
    }

    @Test
    void addInRoad() {
    }
}