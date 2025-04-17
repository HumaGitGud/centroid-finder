import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

// Unit tests for DfsBinaryGroupFinder
public class DfsBinaryGroupFinderTest {

    @Test
    void testSinglePixelGroup() {
        int[][] image = {{1}};
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(1, groups.size(), "Expected one group for a single 1 pixel");
        Group g = groups.get(0);
        assertEquals(1, g.size(), "Group size should be 1");
        assertEquals(0, g.centroid().x(), "Centroid X should be 0");
        assertEquals(0, g.centroid().y(), "Centroid Y should be 0");
    }

    @Test
    void testMultipleIsolatedPixels() {
        int[][] image = {
            {1, 0},
            {0, 1}
        };
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(2, groups.size(), "Expected two isolated groups");

        // Because groups are sorted descending by size then x then y,
        // the pixel at (1,1) should come first
        Group first = groups.get(0);
        assertEquals(1, first.size());
        assertEquals(1, first.centroid().x());
        assertEquals(1, first.centroid().y());

        Group second = groups.get(1);
        assertEquals(1, second.size());
        assertEquals(0, second.centroid().x());
        assertEquals(0, second.centroid().y());
    }

    @Test
    void testConnectedCluster() {
        int[][] image = {
            {1, 1},
            {1, 0}
        };
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        List<Group> groups = finder.findConnectedGroups(image);

        assertEquals(1, groups.size(), "Expected one connected cluster");
        Group g = groups.get(0);
        assertEquals(3, g.size(), "Cluster size should be 3");
        // Coordinates in the cluster: (0,0),(0,1),(1,0)
        // Centroid: x = (0+1+0)/3 = 0  ;  y = (0+0+1)/3 = 0
        assertEquals(0, g.centroid().x(), "Centroid X for cluster should be 0");
        assertEquals(0, g.centroid().y(), "Centroid Y for cluster should be 0");
    }

    @Test
    void testNullImageThrows() {
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        assertThrows(NullPointerException.class,
            () -> finder.findConnectedGroups(null),
            "Passing null image should throw NullPointerException");
    }

    @Test
    void testEmptyImageThrows() {
        DfsBinaryGroupFinder finder = new DfsBinaryGroupFinder();
        int[][] empty = new int[0][];
        assertThrows(IllegalArgumentException.class,
            () -> finder.findConnectedGroups(empty),
            "Passing empty image should throw IllegalArgumentException");
    }
}
