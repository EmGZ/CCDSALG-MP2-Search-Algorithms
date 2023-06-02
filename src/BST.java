/*
    MCO2 Searching S21 Group 4
    Cabungcal, Mary Joselle
    Ladrido, Eryl Gabriel
    Rejano, Hans Martin
    Uy, Gleezell Vina
*/
import java.util.ArrayList;

public class BST {
  Node currRoot = null;
  private ArrayList<String> originalStringArray;
  private ArrayList<String> compressedStringArray;
  
  public static class Node {
		String data;
		Node left;
		Node right;
	}
  	
	/**
	   * Creates a new BST 
	   *
	   * @param s - root node
	   * @return - root node with empty left and right node
	   * @author - Joselle
	   */
	public Node create(String s) {
		Node a = new Node();
		a.data = s;
		a.left = null;
		a.right = null;
		return a;
	}
	
	/**
	   * Inserts a new node in the BST
	   *
	   * @param node - root node of the BST
	   * @param s - new string node to be added
	   * @return - new string node with empty left and right node
	   * @author - Joselle
	   */
	public Node insert(Node node, String s) {
		if(node == null) {
			return create(s);
		}
		
		if(s.compareTo(node.data) < 0) {
			node.left = insert(node.left, s);
		} else if(s.compareTo(node.data) >= 0) {
			node.right = insert(node.right, s);
		}
		
		return node;
	}
	
	
   /**
    * Search for the key in the given BST
    * @param node - root/current node to be searched
    * @param key - kmer/string to be searched
    * @return true/false if the key exists
    */
	public boolean search (Node node, String key) {
     if (node == null) 
       return false;

     while (node != null) {
       if(key.compareTo(node.data) < 0) {
         node = node.left;
       } else if (key.compareTo(node.data) > 0) {
         node = node.right;
       } else {
          this.currRoot = node;
          return true;
       }
     }
     return false;
   }
   
   /**
	   * Count the frequency of key in the BST 
	   *
	   * @param root - root node of the BST
	   * @param key - string key to be counted in the BST
	   * @return - number of times the key was found
	   * @author - Joselle
	   */

   public int counter (Node root, String key) {
     int count = 0;

     while (search(root, key)) {
       count++;
       root = this.currRoot.right;
     }
     return count;
   
  }	

	/**
	   * Destroy the whole made BST
	   * @param root - root node of the BST
     * @return root - empty BST
	   * @author - Eryl
	   */
    public Node destroy(Node root) {
        root = null;
        this.compressedStringArray.clear();
        return root;
    }
    
	/**
	   * Create an array with original strings for the kmer distribution
	   * @param arr - array to be checked
	   * @author - Eryl
	   */    
    public void BSTTable (ArrayList<String> arr) { 
        int i, j;
       
        //initialize originalStringArray by copying contents of array in parameter
        originalStringArray = new ArrayList<String>();
        for (i = 0; i < arr.size(); i++) {
            originalStringArray.add(arr.get(i));
        }

        //initialize compressed string array by removing repeating strings from original string array
        compressedStringArray = new ArrayList<String>();
        compressedStringArray.add(originalStringArray.get(0));
        for (i = 0; i < originalStringArray.size(); i++) {
            compressedStringArray.add(originalStringArray.get(i));
        }
        for (i = 0; i < compressedStringArray.size(); i++) {
            for (j = i+1; j < compressedStringArray.size(); j++) {
                if (compressedStringArray.get(i).equals(compressedStringArray.get(j)))
                    compressedStringArray.remove(j);
            }
        }
      }
	/**
	   * Prints the kmer distribution table of the bst
	   * @param root - root node of the BST
	   * @author - Eryl
	   */      
    	public void printBSTTable(Node root){ 
        int i;
        
        if(compressedStringArray.size() > 0) {
          System.out.println("k-mer | no. of occurrences");

          for (i = 0; i < compressedStringArray.size(); i++) {
              System.out.println(compressedStringArray.get(i) + ": "
              + counter(root, compressedStringArray.get(i)));
          }
        }
        else
          System.out.println("There is no tree");
      }
}
