import java.util.HashMap;

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
    private Stop stop;

    public TrieNode(){
        this.children = new HashMap<>();
    }

    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean isCompleteWord() {
        return isCompleteWord;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop){
        this.stop = stop;
    }

    public void setCompleteWord(){
        isCompleteWord = true;
    }
}
