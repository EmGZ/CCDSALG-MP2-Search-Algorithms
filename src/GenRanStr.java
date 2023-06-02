/*
    MCO2 Searching S21 Group 4
    Cabungcal, Mary Joselle
    Ladrido, Eryl Gabriel
    Rejano, Hans Martin
    Uy, Gleezell Vina

    References:
    https://www.geeksforgeeks.org/split-given-string-into-substrings-of-size-k-by-filling-elements/ - modified the code block on performing substrings
*/

import java.util.ArrayList;
import java.lang.Math;

/**
 * This class contains the methods in creating a random dna string based on
 * alphabet a,c,g,t and getting the substrings in computing for the k-mer distribution
 * of the dna string given a length k.
 */
public class GenRanStr {
    private char[] alphabet;
    private ArrayList<String> kmerDistribution;
    private ArrayList<Integer> kmerFrequency;

    /**
     * This constructs an object that will will be used to create a random dna string based on 
     * alphabet a,c,g,t given a length. 
     */
    public GenRanStr() {
        alphabet = new char[4];
        alphabet[0] = 'a';
        alphabet[1] = 'c';
        alphabet[2] = 'g';
        alphabet[3] = 't';

        this.kmerDistribution = new ArrayList<String>();
    }

    /**
     * This generates a random dna string given string length n
     * @param n the length of the dna string to be generated
     * @return the generated dna string
     */
    public String getDNASequence(int n) {
        String dnaString = "";
        int i;

        for (i = 0; i < n; i++)
            dnaString = dnaString + alphabet[(int) (Math.random() * 100 % 4)]; // concatenate a random char from array alphabet with string res

        return dnaString;
    }

    /**
     * This creates the k-mer substrings from the constructed dna string 
     * given substring length k
     * @param dnaString the generated random string from alphabet a,c,g,t
     * @param k the substring length 
     * @return the arraylist of the generated substrings of the dna string
     */
    public ArrayList<String> createKMer(String dnaString, int k) {
        int i = 0;

        String temp = "";
        while (i < dnaString.length()) {
            temp += dnaString.charAt(i); //concatenate characters to res
            if (temp.length() == k) { //check if length of temp reaches k
                kmerDistribution.add(temp); //add the k-mer string to the arraylist
                temp = ""; //make temp an empty string
                i -= k-1; //index will start at next letter of the dnaString
            }
            i++;
        }

        //System.out.println("Generated: " + dnaString);
        //i = 1;
        //for(String s: kmerDistribution) {
            //System.out.println(i + ":" + s);
            //i++;
        //}

        return kmerDistribution;
    }

    /**
     * This returns the same example DNA sequence given in the MP specs for testing & debugging.
     *
     * @return - ArrayList of kmer Distribution
     */
    public ArrayList<String> setTempDNA () {
        this.kmerDistribution.add("taccac");
        this.kmerDistribution.add("accacc");
        this.kmerDistribution.add("ccacca");
        this.kmerDistribution.add("caccac");
        this.kmerDistribution.add("accacc");
        this.kmerDistribution.add("ccacca");
        this.kmerDistribution.add("caccat");
        this.kmerDistribution.add("accata");
        this.kmerDistribution.add("ccatag");

        return this.kmerDistribution;
    }

    /**
     * This prints the table of the generated DNA sequence and its substrings' frequencies
     * @param k - the size of substrings
     */
    public void printTable ( int k) {
        int i;

        System.out.println(k+"-mer: no. of occurences");
        for (i = 0; i < kmerFrequency.size(); i++) {
            System.out.print((i+1) + ": " + kmerDistribution.get(i) + ": " + kmerFrequency.get(i));
            System.out.println("");
        }
    }

    /**
     * This returns the arraylist of the kmer distribution
     * @return the arraylist of kmer distribution
     */
    public ArrayList<String> getKmerDistribution() {
        return this.kmerDistribution;
    }
}