/**
    MCO2 Searching S21 Group 4
    Cabungcal, Mary Joselle
    Ladrido, Eryl Gabriel
    Rejano, Hans Martin
    Uy, Gleezell Vina
 */
/**
 * This class contains the methods for FNV-1A and Folding Method Hashing
 * Function as well as computing for its k-mer distribution along with
 * its number of occurences
 */
import java.util.ArrayList;
import java.lang.Math;
import java.lang.Integer;

public class FNV1A {
    private ArrayList<String> kmerDistribution;
    private ArrayList<Long> hashValues;
    private List[] chainArray;
    private boolean[] booleanArray;
    private int collisionCount = 0;
    private int filePosition;
    private ArrayList<String> compressedStringArray;

    private static final long FNV_64_INIT = 0xcbf29ce484222325L;
    private static final long FNV_64_PRIME = 0x100000001b3L;

    /**
     * This constructs an object given the arraylist of k-mer strings and 
     * the size of the hash table
     */
    public FNV1A(ArrayList<String> kmer, int len) {
        this.kmerDistribution = new ArrayList<>(kmer);
        this.hashValues = new ArrayList<Long>();
        this.chainArray = new List[len];
        this.booleanArray = new boolean[len];
        this.filePosition = len;

        int i, j;

        // initialize compressed string array by removing repeating strings from
        // original string array
        compressedStringArray = new ArrayList<String>();
        compressedStringArray.add(kmerDistribution.get(0));

        for (i = 0; i < kmerDistribution.size(); i++) 
            compressedStringArray.add(kmerDistribution.get(i));
        
        for (i = 0; i < compressedStringArray.size(); i++) {
            for (j = i + 1; j < compressedStringArray.size(); j++) {
                if (compressedStringArray.get(i).equals(compressedStringArray.get(j)))
                    compressedStringArray.remove(j);
            }
        }
    }

    /**
     * FNV-1A Hashing
     * Reference: http://www.java2s.com/Code/Java/Development-Class/FNVHash.htm
     * Converting Negative integer to Positive integer
     * Reference:
     * https://youtu.be/1JZMb3D75PY?list=PLpPXw4zFa0uKKhaSz87IowJnOTzh9tiBk
     *
     * @param str the substring from dna string
     * @return
     */
    public long FNVHash(String str) {
        long hash = FNV_64_INIT;
        
        for (int i = 0; i < str.length(); i++) {
            hash ^= str.charAt(i);
            hash *= FNV_64_PRIME;
        }
        
        if (hash < 0)
            return hash & 0x7FFFFFFF;
        return hash;
    }

    /**
     * This adds the produced hash value to the array of hash values
     */
    public void createHashValuesArray() {
        for (int i = 0; i < kmerDistribution.size(); i++) {
            this.hashValues.add(FNVHash(kmerDistribution.get(i)));
        }
    }

    /**
     * Displays the hash table
     * @param num the size of the hash table
     */
    public void printHashTable(int num) {
        int i, j;

        System.out.println("Hash Table");
        System.out.println("index | k % " + num + " | h(k)"); 

        for (i = 0; i < num; i++) {
            System.out.print(i + " | ");

            if (booleanArray[i]) {
                System.out.print("occupied");
                System.out.print(" | ");

                for (j = 0; j < chainArray[i].getLongSize(); j++) 
                    System.out.print(chainArray[i].getLongElement(j) + " - ");
                    

                System.out.println();
            } 
            else
                System.out.println("empty");
            }

        printFrequencyTable(chainArray);
    }

    /**
     * Prints the hash table after rehashing
     * 
     * @param num the size of the hash table
     * @param boolArr the array that checks if an index is occupied
     * @param cArr the chain array
     */
    public void printHashTable(int num, boolean[] boolArr, List[] cArr) {
        int i, j;

        System.out.println("Hash Table");
        System.out.println("index | k % " + num + " | h(k)");

        for (i = 0; i < num; i++) {
            System.out.print(i + " | ");

            if (boolArr[i]) {
                System.out.print("occupied");
                System.out.print(" | ");

                for (j = 0; j < cArr[i].getLongSize(); j++) {
                    System.out.print(cArr[i].getLongElement(j) + " - ");
                }

                System.out.println();
            } 
            else
                System.out.println("empty");
        }
        printFrequencyTable(cArr); 
    }

    /**
     * Checks if the index of the boolean array contains an element
     * @param boolArr
     * @return the boolean value
     */
    public boolean checkIfLoaded(boolean[] boolArr) {
        int i;
        boolean res = true;

        for (i = 0; i < boolArr.length; i++) {
        if (!boolArr[i])
            res = false;
        }

        return res;
    }

    /**
     * Prints the hash values
     */
    public void printHashValues() {
        for (long i : hashValues) {
            System.out.println(i);
        }
    }

    /**
     * This uses the folding method and returns an index using the long key and the
     * size of the hash table
     *
     * @param key       - the long hash that will be folded into an index
     * @param tableSize - the size of the hash table (determines the max digits for
     *                  the addends and the resulting index)
     * @return - the resulting index using the folding method
     *
     * @author - Gleezell
     */
    public int getResultFromFoldingMethod(long key, int tableSize) {
        ArrayList<Integer> digitsArray = new ArrayList<Integer>();
        int i, result = 0;
        int sizeDigits = Integer.toString(tableSize).length();
        
        while (key > 0) { 
            digitsArray.add((int) (key % (Math.pow(10, sizeDigits))));
            key /= Math.pow(10, sizeDigits);
        }

        for (i = 0; i < digitsArray.size(); i++) {
            result += digitsArray.get(i);
        }
        
        return result %= tableSize;
    }

    /**
     * Creates the chain array for collision resolution
     * @param size the hash table size
     */
    public void setChainArray(int size) {
        chainArray = new List[size];
        booleanArray = new boolean[size];
        int i, res;

        if(!checkIfLoaded(booleanArray)) {
            for (i = 0; i < hashValues.size(); i++) {
                res = getResultFromFoldingMethod(hashValues.get(i), size);
                if (!booleanArray[res]) {
                    booleanArray[res] = true;
    
                    chainArray[res] = new List();
                    chainArray[res].addLong(hashValues.get(i));
                } else {
                    this.collisionCount++;
                    chainArray[res].addLong(hashValues.get(i));
                }
            }
        }
        else{
            rehash(size);
        }
    }

    /**
     * Gets the collision count
     * @return the collision count
     */
    public int getCollisionCount() {
        return this.collisionCount;
    }

    /**
     * Gets the frequency of a k-mer string in the chain array
     * @param cArr the chain array
     * @param num the hash value 
     * @return the frequency of the k-mer string
     */
    public int getFrequency(List[] cArr, long num) {
        int i;
        int frequency = 0;
        int index = getResultFromFoldingMethod(num, this.filePosition); //gets the resulting index

        for (i = 0; i < cArr[index].getLongSize(); i++) {
            if (num == cArr[index].getLongElement(i)) //checks if given hash value is present in the array
                frequency++; //adds frequency count
        }

        return frequency;
    }

    /**
     * Prints the k-mer distribution along with the number of occurences
     * of each k-mer
     * @param cArr the chain array
     */
    public void printFrequencyTable(List[] cArr) {
        int i;

        System.out.println("k-mer | no. of occurrences");

        for (i = 0; i < compressedStringArray.size(); i++) {
            System.out.println(compressedStringArray.get(i) + ": " +
            getFrequency(cArr, FNVHash(compressedStringArray.get(i))));
        }
    }
    
    /**
     * Rehashes the hash table once it reaches 1.0 threshold
     * 
     * @param num the hash table size
     */
    public void rehash(int num) {
        int i, res;
        // reset collisionCount
        this.collisionCount = 0;
        // double the file position
        this.filePosition = num * 2; //doubles the hash table size

        System.out.println("Rehashing with size " + this.filePosition);

        // recreate chainArray and booleanArray
        List[] chainArray2 = new List[this.filePosition];
        boolean[] booleanArray2 = new boolean[this.filePosition];

        for (i = 0; i < hashValues.size(); i++) {
            res = getResultFromFoldingMethod(hashValues.get(i), this.filePosition);
            if (!booleanArray2[res]) {
                booleanArray2[res] = true;

                chainArray2[res] = new List();
                chainArray2[res].addLong(hashValues.get(i));
            } else {
                this.collisionCount++;
                chainArray2[res].addLong(hashValues.get(i));
            }
        }

        printHashTable(this.filePosition, booleanArray2, chainArray2); //prints the rehashed table
    }
}
