/**
    MCO2 Searching S21 Group 4
    Cabungcal, Mary Joselle
    Ladrido, Eryl Gabriel
    Rejano, Hans Martin
    Uy, Gleezell Vina
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

//import BST.Node;

class Main {
  public static void main(String[] args) {
	  
	  int count = 0;

	    Scanner numScanner = new Scanner(System.in);
	    // Asking user for number of iterations K
	    System.out.print("Enter k: ");
	    int k = numScanner.nextInt();
	    
	    numScanner.close();

	    double[] hTime1 = new double[k]; // contains running times for hashing function 1
	    double[] hTime2 = new double[k]; // contains running times for hashing function 2
	    double[] bTime = new double[k]; // contains running times for BST
	    int[] hCol1 = new int[k];
	    int[] hCol2 = new int[k];
	
	while(count < k) {
		GenRanStr genRanStr = new GenRanStr();
	
	    double startTime1 = System.currentTimeMillis() / 1.0;
	    String dnaSequence = genRanStr.getDNASequence((int) Math.pow(10, 4));
	
	    int len = dnaSequence.length();
	    //System.out.println("len = " + len);
	
	    ArrayList<String> kmer = genRanStr.createKMer(dnaSequence, 5);
	    // compute running time
	    double stopTime1 = System.currentTimeMillis() / 1.0;
	    double elapsedTime1 = stopTime1 - startTime1;
	
	    double startTime2 = System.currentTimeMillis() / 1.0;
	    ArrayList<String> fnvKmer = new ArrayList<>(kmer); // copy elements of kmer-distribution to test

      //Hashing Function #1
	    System.out.println("FNV1A - Folding Method Chain Array");
	    FNV1A hash1 = new FNV1A(fnvKmer, dnaSequence.length());
	    hash1.createHashValuesArray();
	    hash1.setChainArray(dnaSequence.length());
	    System.out.println("");
	    hash1.printHashTable(dnaSequence.length());
	    System.out.println("Collision: " + hash1.getCollisionCount());
	    // compute running time for merge sort
	    double stopTime2 = System.currentTimeMillis() / 1.0;
	    double elapsedTime2 = (stopTime2 - startTime2) + elapsedTime1;
	
	    System.out.println("Running time of Hashing#1: " + elapsedTime2);
	
	    System.out.println();

      //Hashing Function #2
	    System.out.println("Hash Division Chaining Method");
	    double startTime3 = System.currentTimeMillis() / 1.0;
	    ArrayList<String> kmer2 = new ArrayList<>(kmer); // copy elements of kmer-distribution to test
	
	    HashDivisionChainingMethod hash2 = new HashDivisionChainingMethod(kmer2, len);
	    hash2.printHashTable(len);
	    System.out.println("Collision: " + hash2.getCollisionCount());
	
	    // compute running time
	    double stopTime3 = System.currentTimeMillis() / 1.0;
	    double elapsedTime3 = (stopTime3 - startTime3) + elapsedTime1;
	    System.out.println("Running time of Hashing2: " + elapsedTime3);
	
	    System.out.println();
      //Binary Search Tree
	    System.out.println("Binary Search Tree");
	    double startTime4 = System.currentTimeMillis() / 1.0;
	    BST bst = new BST();
	    BST.Node root = null;
	
	    for (String s : kmer) {
	      root = bst.insert(root, s);
	    }
	
	    System.out.println(" ");
	    bst.BSTTable(kmer);
	    bst.printBSTTable(root);

	    bst.destroy(root);
	    bst.printBSTTable(root);
	
	    // compute running time
	    double stopTime4 = System.currentTimeMillis() / 1.0;
	    double elapsedTime4 = (stopTime4 - startTime4) + elapsedTime1;
	    System.out.println("Running time of BST: " + elapsedTime4);
	    
	    //Storing of Running times
	    hTime1[count] = elapsedTime2;
	    hTime2[count] = elapsedTime3;
	    bTime[count] = elapsedTime4;
	    
	    //Storing of Collision Count for Hashing Methods
	    hCol1[count] = hash1.getCollisionCount();
	    hCol2[count] = hash2.getCollisionCount();
	    
	    count++; //increment iteration count
	}

    //exporting to a text file
	//Uncomment this and change desire directory file to export txt file
	/*
	  try{
        BufferedWriter bw = new BufferedWriter(
            new FileWriter("D:\\Java\\eclipse-workspace\\CCDSALGMP2\\src\\test.txt"));
        bw.write("FNV1AFM Collision Count;HDMC Collision Count;FNV1AFM Running Time;HDMC Running Time;BST Running Time\n");
        for (int i = 0; i < k; i++) {
            bw.write(hCol1[i]+ ";" + hCol2[i] + ";" + hTime1[i] + ";" + hTime2[i] + ";" + bTime[i] + "\n");
        }
        
        bw.close();
    } catch(Exception ex) {
        return;
    }*/
  }
}