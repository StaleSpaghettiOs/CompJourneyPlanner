import java.util.*;

public class TrieNode {
    /** Fields */
    List<Object> objects;
    HashMap<Character, TrieNode> children;

    /** Constructor */
    public TrieNode(){
        //create a new empty trie
    }

    /**
     * Placeholder add method for trie class
     * @param word
     * @param obj
     */
    public void add(char[] word, Object obj){
        for(char c : word){
            //if node's children do not contain c
            //-> then create a new child of node, connecting to node via c

            //move node to the child corresponding to c
        }
        //add obj into node.objects
    }


    /**
     * Placeholder code for trie get method
     * @param word
     * @return objects
     */
    public List<Object> get(char[] word){
        //Set node to the root of the trie

        for(char c : word){
           //if nodes children do not contain c
           return null;
           //move node to the child corresponding to c
        }
        return objects;
    }

}
