import java.util.*;

/**
 * Trie Structure, constructed from TrieNode objects
 * @author Jakob Coker
 */
public class Trie {
    /** Fields */

    private TrieNode root;



    /** Constructor */
    public Trie(){
        root = new TrieNode();

    }


    /**
     * Placeholder add method for trie class
     * @param word - the input word
     * @param stop - the Stop of that name
     */
    public void add(char[] word, Stop stop){
        /* Let current stop = root stop */
        TrieNode current = root;
        for(char c : word){
            /* create a new child of stop, connecting to stop via c */
            if(!current.getChildren().containsKey(c)) current.getChildren().put(c, new TrieNode());

            current = current.getChildren().get(c);
        }
        current.setCompleteWord();
        current.addStop(stop);
    }





    /**
     * Retrieves the stop
     * @param word - the input word
     * @return objects
     */
    public ArrayList<Stop> get(char[] word){
        //Set node to the root of the trie
        TrieNode current = root;
        for(char c : word){
           if(!current.getChildren().containsKey(c)) return null;
           current = current.getChildren().get(c);
        }

        if(!current.isCompleteWord()) return null;

        return current.getStop();
    }

    /**
     * Get all nodes that start with a
     * specified prefix
     * @param prefix the string to complete
     * @return the list of stops
     */
    public List<Stop> getAll(char[] prefix){
        List<Stop> results = new ArrayList<>();
        //Set node to the root of the trie
        TrieNode current = root;

        for(char c : prefix) {
            if (!current.getChildren().containsKey(c)) return null;
            current = current.getChildren().get(c);
        }
        getAllFrom(current, results);
        return results;
    }

    /**
     * Used by getAll method to add all stops
     * under a node to the list of results
     * @param node the parent to start at
     * @param results an empty list
     */
    public void getAllFrom(TrieNode node, List<Stop> results){
        TrieNode current = node;

        if (!current.isCompleteWord()) {
            for(TrieNode trieNode : current.getChildren().values()){
                getAllFrom(trieNode, results);
            }
        } else {
            results.addAll(current.getStop());
        }
    }

    /**
     * Query whether the word is in the trie structure AND is a word ending
     * @param word - the input word
     * @return current.isWordEnding
     */
    public boolean queryWord(char[] word){
        TrieNode current = root;

        /* traverse the input word */

        for(char c : word){
            if(current.getChildren().containsKey(c)) current = current.getChildren().get(c);
            else return false;
        }
        /* Return true iff the word is a word ending */
        return current.isCompleteWord();
    }

    /**
     * Query whether a word is in the trie structure,
     * doesn't have to be a word ending - telling us the word
     * is a prefix
     * @param word
     * @return
     */
    public boolean queryPrefix(char[] word){
        TrieNode current = root;

        for(char c : word){
            if(current.getChildren().containsKey(c)) current = current.getChildren().get(c);
            else return false;
        }
        return true;
    }
}
