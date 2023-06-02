
import java.util.ArrayList;

public class HashDivisionChainingMethod {

  private ArrayList<String> originalStringArray;
  private ArrayList<Integer> stringToIntegerArray;
  private List[] chainArray;
  private boolean[] booleanArray;
  private ArrayList<String> compressedStringArray;
  private int collisionCount = 0;
  private int filePosition;
  private static final int FNV_32_INIT = 0x811c9dc5;
  private static final int FNV_32_PRIME = 0x01000193;

  public HashDivisionChainingMethod(ArrayList<String> arr, int len) {
    int i, j, res;
    this.filePosition = len;

    // initialize originalStringArray by copying contents of array in parameter
    originalStringArray = new ArrayList<String>();
    for (i = 0; i < arr.size(); i++) {
      originalStringArray.add(arr.get(i));
    }

    // initialize contents of stringToIntegerArray by converting every string from
    // parameter to new int array
    stringToIntegerArray = new ArrayList<Integer>();
    for (i = 0; i < arr.size(); i++) {
      stringToIntegerArray.add(convertStringToIntHash(arr.get(i)));
    }

    // set hash table using chaining method for collisions
    chainArray = new List[getNearestPrime(this.filePosition)];
    booleanArray = new boolean[getNearestPrime(this.filePosition)];

    for (i = 0; i < arr.size(); i++) {
      res = getResultFromDivisionMethod(stringToIntegerArray.get(i), this.filePosition);

      if (!booleanArray[res]) {
        booleanArray[res] = true;

        chainArray[res] = new List();
        chainArray[res].addInt(stringToIntegerArray.get(i));
      } else {
        this.collisionCount++;
        chainArray[res].addInt(stringToIntegerArray.get(i));
      }
    }

    // initialize compressed string array by removing repeating strings from
    // original string array
    compressedStringArray = new ArrayList<String>();
    compressedStringArray.add(originalStringArray.get(0));
    for (i = 0; i < originalStringArray.size(); i++) {
      compressedStringArray.add(originalStringArray.get(i));
    }
    for (i = 0; i < compressedStringArray.size(); i++) {
      for (j = i + 1; j < compressedStringArray.size(); j++) {
        if (compressedStringArray.get(i).equals(compressedStringArray.get(j)))
          compressedStringArray.remove(j);
      }
    }

  }

  /**
   * This converts given string to an integer hash
   * reference : http://www.java2s.com/Code/Java/Development-Class/FNVHash.htm
   *
   * @param s - string to be converted
   * @return - the converted string
   */
  public int convertStringToIntHash(String s) {
    int hash = FNV_32_INIT;
        
        for (int i = 0; i < s.length(); i++) {
            hash ^= s.charAt(i);
            hash *= FNV_32_PRIME;
        }
        
        if (hash < 0)
            return hash & 0x7FFFFFFF;
        else
          return hash;
  }

  /**
   * Gets the nearest prime number less than the given number
   *
   * @param num - the given number
   * @return - the nearest prime number less than the given number
   * @author - Martin
   */
  public int getNearestPrime(int num) {
    if (num % 2 == 0) // checks if given number is odd
      num--; // if even number, subtract 1 from num

    int i, j;

    for (i = num; i >= 2; i -= 2) {
      if (i % 2 == 0)
        continue; // if i is even number, continue iterating
      for (j = 3; j <= Math.sqrt(i); j += 2) {
        if (i % j == 0)
          break;
      }
      if (j > Math.sqrt(i))
        return i; // return the nearest prime number stored in i
    }

    return 2; // smallest prime number, will return if given num is 3
  }

  public int getResultFromDivisionMethod(int num, int size) {
    int prime = getNearestPrime(size);

    return num % prime;
  }

  public int getFrequency(List[] cArr, int num) {
    int i;
    int frequency = 0;
    int index = getResultFromDivisionMethod(num, this.filePosition);

    for (i = 0; i < cArr[index].getIntSize(); i++) {
      if (num == cArr[index].getIntElement(i))
        frequency++;
    }

    return frequency;
  }

  public void printHashTable(int num) {
    int i, j;

    num = getNearestPrime(num);
    System.out.println("Hash Table");
    System.out.println("index | k % " + getNearestPrime(num) + " | h(k)");

    if (!checkIfLoaded(this.booleanArray)) {
      for (i = 0; i < num; i++) {
        System.out.print(i + " | ");

        if (booleanArray[i]) {
          System.out.print("occupied");
          System.out.print(" | ");

          for (j = 0; j < chainArray[i].getIntSize(); j++) {
            System.out.print(chainArray[i].getIntElement(j) + " - ");
          }

          System.out.println();
        } else
          System.out.println("empty");
      }

      printFrequencyTable(chainArray);
    } else {
      rehash(num);
    }

  }

  public void printHashTable(int num, boolean[] boolArr, List[] cArr) {
    int i, j;

    num = getNearestPrime(num);
    System.out.println("Hash Table");
    System.out.println("index | k % " + getNearestPrime(num) + " | h(k)");

    if (!checkIfLoaded(boolArr)) {
      for (i = 0; i < num; i++) {
        System.out.print(i + " | ");

        if (boolArr[i]) {
          System.out.print("occupied");
          System.out.print(" | ");

          for (j = 0; j < cArr[i].getLongSize(); j++) {
            System.out.print(cArr[i].getLongElement(j) + " - ");
          }

          System.out.println();
        } else
          System.out.println("empty");
      }

      printFrequencyTable(cArr);
    } else {
      rehash(num);
    }

  }

  public void printFrequencyTable(List[] cArr) {
    int i;

    System.out.println("k-mer | no. of occurrences");

    for (i = 0; i < compressedStringArray.size(); i++) {
      System.out.println(compressedStringArray.get(i) + ": " +
          getFrequency(cArr, convertStringToIntHash(compressedStringArray.get(i))));
    }
  }

  public int getCollisionCount() {
    return this.collisionCount;
  }

  public boolean checkIfLoaded(boolean[] boolArr) {
    int i;
    boolean res = true;

    for (i = 0; i < boolArr.length; i++) {
      if (!boolArr[i])
        res = false;
    }

    return res;
  }

  public void rehash(int num) {
    int i, res;
    // reset collisionCount
    this.collisionCount = 0;
    // double the file position
    this.filePosition = num * 2;

    // recreate chainArray and booleanArray
    List[] chainArray2 = new List[getNearestPrime(this.filePosition)];
    boolean[] booleanArray2 = new boolean[getNearestPrime(this.filePosition)];

    for (i = 0; i < stringToIntegerArray.size(); i++) {
      res = getResultFromDivisionMethod(stringToIntegerArray.get(i), this.filePosition);

      if (!booleanArray2[res]) {
        booleanArray2[res] = true;

        chainArray2[res] = new List();
        chainArray2[res].addInt(stringToIntegerArray.get(i));
      } else {
        this.collisionCount++;
        chainArray2[res].addInt(stringToIntegerArray.get(i));
      }
    }

    System.out.println("\nRehashing with size " + getNearestPrime(this.filePosition));
    printHashTable(this.filePosition, booleanArray2, chainArray2);
  }
}
