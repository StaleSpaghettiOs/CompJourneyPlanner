import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TrieNode object represents a single
 * node in a Trie structure. Used to reference
 * the root node and get information on a single
 * node.
 * @author Jakob Coker
 */
public class TrieNode {

    private HashMap<Character, TrieNode> children;
    private boolean isCompleteWord = false;
    private ArrayList<Stop> stops;

    public TrieNode(){
        this.children = new HashMap<>();
        this.stops = new ArrayList<>();
    }

    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean isCompleteWord() {
        return isCompleteWord;
    }

    public ArrayList<Stop> getStop() {
        return this.stops;
    }

    public void addStop(Stop stop){
        stops.add(stop);
    }

    public void setCompleteWord(){
        isCompleteWord = true;
    }
}
