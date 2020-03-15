import java.util.*;

public class TrieNode {
    /** Fields */
    private List<Object> objects;
    private HashMap<Character, TrieNode> children;
    private TrieNode root = new TrieNode();
    private boolean isWordEnding;


    /** Constructor */
    public TrieNode(){
        this.objects = new ArrayList<Object>();
        this.children = new HashMap<Character, TrieNode>();
    }


    /**
     * Placeholder add method for trie class
     * @param word - the input word
     * @param obj - the Stop of name word
     */
    public void add(char[] word, Object obj){
        /* Let current node = root node */
        TrieNode current = root;
        for(char c : word){
            /* create a new child of node, connecting to node via c */
            if(!current.children.containsKey(c)) current.children.put(c, new TrieNode());
            current = current.children.get(c);
        }
        isWordEnding = true;
        current.objects.add(obj);
    }

    public void addString(String word, Object obj){
        char[] output = word.toCharArray();
        add(output, obj);
    }


    /**
     * Placeholder code for trie get method
     * @param word - the input word
     * @return objects
     */
    public List<Object> get(char[] word){
        //Set node to the root of the trie
        TrieNode current = root;
        for(char c : word){
           if(!current.children.containsKey(c)) return null;

           current = current.children.get(c);

        }
        return objects;
    }

    public List<Object> getAll(char[] prefix){
        List<Object> results = new ArrayList<Object>();
        //Set node to the root of the trie
        TrieNode current = root;

        for(char c : prefix){
            if(!current.children.containsKey(c)) return null;

            current = current.children.get(c);
            getAllFrom(root, results);
            return results;

        }
        return null;
    }


    public void getAllFrom(TrieNode node, List<Object> results){

    }

    /**
     * Query whether the word is in the trie structure AND is a word ending
     * @param word - the input word
     * @return current.isWordEnding
     */
    public boolean queryWord(String word){
        TrieNode current = root;

        /* traverse the input word */
        char[] w = word.toCharArray();
        for(char c : w){
            if(current.children.containsKey(c)) current = current.children.get(c);
            else return false;
        }
        /* Return true iff the word is a word ending */
        return current.isWordEnding;
    }
}
