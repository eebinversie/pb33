////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Title: p3b
//Due Date: 3/28/19
//Files: DataStructureADTTest.java, HashTableADT.java, HashTable.java, HashTableTest.java,
//       DuplicateKeyException.java, IllegalNullKeyException.java, KeyNotFoundException.java
//Course: CS 400, Spring 2018-2019
//
//Author: Emily Binversie 
//Email: eebinversie@wisc.edu
//Lecturer's Name: Andrew Kuemmel
//Lecture Numebr: Lecture 004
//
////////////////////PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
//Partner Name: NA
//Partner Email: NA
//Partner Lecturer's Name: NA
//
//VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//___ Write-up states that pair programming is allowed for this assignment.
//___ We have both read and understand the course Pair Programming Policy.
//___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
//Students who get help from sources other than their partner must fully
//acknowledge and credit those sources of help here. Instructors and TAs do
//not need to be credited here, but tutors, friends, relatives, room mates,
//strangers, and others do. If you received no outside help from either type
//of source, then please explicitly indicate NONE.
//
//Persons: NONE
//Online Sources: NONE
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;

/**
 * This class represents a HashTable which is a searchable data structure that acheives constant
 * time O(1) for lookup, insert, and delete operations with comparable performance to Java's
 * built-in TreeMap type. Hashing is a technique that is used to uniquely identify a specific 
 * object from a gropup of similar objects. A HashTable employs a simple array like data structure
 * where <key,value> pairs can be stored and quickly accessed. Hashing is meant to help distribute
 * entries evenly across an array. Each <key,value> pair is assigned a converted key that is 
 * computed by the algorithm hash function. The HashTable class is generic so any String, Integer,
 * Float, etc. can be instantiated. This method implements operations described in the 
 * HashTableADT<K,V> interface in an efficient way (0(1)). This HashTable uses an internal 
 * ArrayList data structure. Java's HashMap or TreeMap types are not used. This code is designed
 * to handle edge cases, and collisions. Collisions are dealt with using "chained" buckets were
 * each node in the HashTable array can have a link (pointer) to a next node if two <key,value>
 * pairs have the same resulting index in the Hash ArrayList. The index position for the 
 * <key,value> pair is calculated by (HashCode%ArrayList Size) = index. This HashTable is also 
 * able to handle resizing. Resizing should occur when the load factor (which is defined as 
 * the number of items in the array/table size) is equal to or greater than the default or 
 * specified load factor threshold. The capacity increases to: 2 * capacity + 1, and the 
 * capacity never decreases. When nodes with keys equal to null are attempted to be added, 
 * removed, or searched for in the HashTable IllegalNullKeyException Exceptions are thrown.
 * Attempting to add a node with a key that already is present in the HashTable will throw a 
 * DuplicateKeyException. If the get() method is called with a key that is not in the HashTable a 
 * KeyNotFoundException exception will be thrown. 
 *
 * @author Emily Binversie
 * @version 1.0
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

  // protected data field members 
  private int currentCapacity;
  // this stores the current ArrayList HashTable size in the form of an int, default is 11 if
  // not defined in the constructor
  private double loadFactorThreshold;
  // this stores the loadFactorThreshold, default is 0.75 if not defined in the constructor
  private int numKeys; 
  // this int keeps track of the number of <key,value> pairs that are currently in the HashTable
  private ArrayList<HashTableNode> hashTableArray; 
  // delcares the HashTable ArrayList
  
  /**
   * This private inner class represents a single <key,value> HashTable ArrayList node with a 
   * pointer to the next node to be added to a linked list of nodes if there is a collision at 
   * that index location in the HashTable. When a new node is created the pointer to the next 
   * node is initalized to null. If a collision occurs at that ArrayList index location the new
   * node will become the top most HashNode at that index and its next pointer will point to the 
   * HashNode that is already at that index. For the purposes of "chained" buckets the last node 
   * in the list will point to null, to signify the end of the linked list has been reached.     
   *           
   * @author Emily Binversie
   * @version 1.0
   */
  class HashTableNode{
    K key;
    // the key that is stored in the HashTable ArrayList node
    V value;
    // value that is stored at in the HashTable ArrayList node
    HashTableNode next;
    // the pointer to the next node so a linked nodes of chained buckets can be used for
    // collision repair
    
    /**
     * Constructor that creates a new HashTable node to store the passed parameters comparable 
     * key K, and data/value V. This constructor initalizes the link to the next HashTable node
     * to null. Each node when added will not point to another link unless there is a collision. 
     * If there is a collision the node will be added to the HashTable array and the specified
     * index and change its next point to the node that already exists in that ArrayList index.
     * Nodes can not have keys equal to null and duplicate nodes can not exsit in the HashTable.
     * 
     * @param <key> the key must not be null and must be Comparable
     * @param <value> the data value associated with a given key
     */ 
    private HashTableNode(K key, V value) {
      this.key = key;
      // set the passed parameter key equal to this HashTable nodes key
      this.value = value;
      // set the passed parameter value equal to this HashTable nodes value
      next = null;
      // the pointer to the next node in the "chained" bucket linked list, the last node in the 
      // "chained" bucket list should point to null
    }
  }

  /**
   * No arguement constructor that creates a new HashTable ArrayList with default table size 
   * set to 11 (odd and prime number), and default Load Factor Threshold equal to 0.75. There is
   * another constructor that needs parameters of table size and load factor threshold. 
   * 
   */ 
  public HashTable() {
    this(11, 0.75);
    // call on the arguement constructor to create a HashTable with default ArrayList size 
    // equal to 11 and load factor threshold equal to 0.75
  }
  
  /**
   * Constructor that creates a new HashTable ArrayList with user specified inital capacity 
   * (table size) and load factor threshold. These two values will determine when resizing and 
   * rehashing needs to take place. Number of keys in the HashTable is set to zero. 
   * 
   * @param initialCapacity the int that determines the starting HashTable size
   * @param loadFactorThreshold the user determines the double that will determine how many
   *     HashTable nodes can be stored in the table before a rehashing or resizing needs to occur
   */
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    hashTableArray = new ArrayList<HashTableNode>();
    // hashTableArray = new ArrayList<HashTableNode>();
    // initalize an ArrayList HashTable to store the HashTable nodes 
    this.currentCapacity = initialCapacity;
    this.loadFactorThreshold = loadFactorThreshold;
    numKeys = 0;
    // set the number of nodes the HashTable currently contains to zero 
    
    for (int i =0 ; i<initialCapacity; i++) {
      // use a for loop to loop through the HashTable Array and set the nodes to be empty
      hashTableArray.add(null);
    }
  }

  /**
   * This method creates a new HashTable node with the specified <key,value>, and inserts it into 
   * this HashTable and increments the number of keys in the HashTable by 1. If the key to be 
   * added is null, it will throw an IllegalNullKeyException. If a node with the exact key already 
   * exists within the HashTable a DuplicateKeyException will be thrown. A <key,value> pair is
   * inserted into the HashTable based off of a HashCode generated and modulo the current
   * HashTable size. This HashCode is generated by a private method getHashCodeIndex(). If a 
   * HashNode is already present in the HashTables index location this program will deal with 
   * colisions by a chain buckets approach using linked nodes. The new node will be inserted on 
   * top of the node already present in that array location, the new nodes pointer to the next
   * node will be updated. After each insert is performed the load factor will be calculated 
   * to make sure the HashTable's load factor is less than the threshold. If its equal to or 
   * greater a rehashing will be performed. 
   * 
   * @param K key of the new node to be added to the HashTable
   * @param V value of the new node to be added to the HashTable
   * @throws java.lang.IllegalNullKeyException - This exception will be thrown when the key that
   *     is attempting to be added to the HashTable has a key value equaling null
   * @throws java.lang.DuplicateKeyException - When a node with the specified key already 
   *     exists within this HashTable this exception will be thrown
   * @see DataStructureADT#insert(java.lang.Comparable)
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null) {
      // if the key being inserted into the HashTable is null then throw a IllegalNullKeyException
      throw new IllegalNullKeyException();
    }
    
    int hashIndex = getHashCodeIndex(key);
    // generate a HashCode and HashCode Index to insert this new <key,value> HashTable node
    HashTableNode hashNode = hashTableArray.get(hashIndex);
    // get the HashTable node that is stored at that index in the HashTable ArrayList
    // if no previous <key,value> HashTable node has been stored at that index location this 
    // node should equal null

    while(hashNode != null) {
      // if there already is a HashNode stored there iterate through the chained buckets of 
      // linked nodes to check that a duplicate does not exsit there
      if (hashNode.key.compareTo(key)==0) {
        // if a mathcing key is found in the HashTable throw a DuplicateKeyException
        throw new DuplicateKeyException();
      }
      hashNode = hashNode.next;
      // continue iterating through the linked list of HashTable nodes
    }
    numKeys++;
    // if the key is not null and a duplicate key does not exist add it to the HashTable
    // increment the number of HashTable nodes stored in the HashTable by one
    
    HashTableNode newHashNodeToAdd = new HashTableNode(key, value);
    // create a new HashTable node to add 
    newHashNodeToAdd.next = hashTableArray.get(hashIndex);
    // set the new HashTables node next pointer to the HashTable node that is already present
    // at the same HashTable index
    hashTableArray.set(hashIndex, newHashNodeToAdd);
    
    if ((1.0*numKeys)/currentCapacity >= loadFactorThreshold) {
      // check the load factor (number of items/table size) is not greater or equal to the 
      // load factor threshold after the insert
      rehash();
      // rehash if needed
    }
  }
  
  /**
   * This method is called on if a rehashing is needed, meaning the current load factor (number
   * of stored items/table size) is greater than the HashTable's loadFactorThreshold. This 
   * method will perform a rehashing. First it doubles the ArrayList HashTable size plus 1. Then 
   * it must recalcute HashCode values for the stored items to change their index locations, 
   * otherwise those items will not be able to be retrieved because a new idex will be generated
   * when performing HashCode%TableSize. This method calls on insert to help rehash already 
   * stored item.
   * 
   * @throws java.lang.IllegalNullKeyException - This exception will be thrown when the key that
   *     is attempting to be added to the HashTable has a key value equaling null
   * @throws java.lang.DuplicateKeyException - When a node with the specified key already 
   *     exists within this HashTable this exception will be thrown
   */
  private void rehash() throws IllegalNullKeyException, DuplicateKeyException {
    ArrayList<HashTableNode> temp = hashTableArray;
    // store the current HashTable into a temporary variable
    hashTableArray = new ArrayList<HashTableNode>(currentCapacity * 2);
    // create a new HashTable ArrayList that is double its previous size
    int oldCapacity = currentCapacity;
    this.currentCapacity = (currentCapacity*2) + 1;
    // calculate the new capacity to double what it previously was plus 1 (to keep the table size
    // odd)

    for (int i = 0; i < currentCapacity; i++) {
      // use a for loop to loop through the HashTable Array and set the nodes to be empty
      hashTableArray.add(i, null);
    }
    numKeys = 0;
    // set the new HashTables numbers of stored keys equal to zero

    for (int i = 0; i < oldCapacity; i++) {
      // loop through the old smaller HashTable and retrieve each node
      HashTableNode currentHashNode = temp.get(i);

      while (currentHashNode != null) {
        // loop through the chained buckets for each HashTable ArrayList to re-insert each
        // HashTable node that was previously inserted by creating new HashTable nodes
        K key = currentHashNode.key;
        V value = currentHashNode.value;
        insert(key, value);
        currentHashNode = currentHashNode.next;
      }
    } 
  }
  
  /**
   * This method attempts to remove a HashTable node from the HashTable. If the key it is 
   * searching for is null then an IllegalNullKeyException exception is thrown. 
   * 
   * @param K key of the node to be removed
   * @return boolean return true if a node is successfully removed, false otherwise
   * @throws IllegalNullKeyException this exception will be thrown when the key that
   *     is attempting to be removed from the HashTable has a key value equaling null
   * @see DataStructureADT#remove(java.lang.Comparable)
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    boolean ret = false;
    // create a local boolean to return (true if remove is successfull, false if the key 
    // is not found in the HashTable
    if (key == null) {
      //nif the key trying to be removed is null then throw a IllegalNullKeyException
      throw new IllegalNullKeyException();
    }
    
    int hashIndex = getHashCodeIndex(key);
    // generate a HashCode and HashCode Index check where the HashTable node is expected to be 
    // stored in the ArrayList HashTable
    
    HashTableNode currentHashNode = hashTableArray.get(hashIndex);
    // get the HashTable node that is stored at that index location in the HashTable
    HashTableNode previousHashNode = null;
    
    while (currentHashNode != null) {
      // loop through the chained buckets linked list - looping stops when null is reached
      if (currentHashNode.key.compareTo(key)==0) {
        // if a HashTable node with a matching key is found then return true
        ret = true;
        // decrement the counter of stored HashTable nodes by one
        numKeys--;

        if (previousHashNode != null) {
          // if the node to be removed has another node "above" in in the linked list then 
          // set the above nodes next pointer to the node below the one being removed
          previousHashNode.next = currentHashNode.next;
        }
        else {
          // if the node being removed is at the top of the chained bucket linked list then move 
          // the node below it to the new top node in that index location
          hashTableArray.set(hashIndex, currentHashNode.next);
        }
        break;
      }
      else {
        previousHashNode = currentHashNode;
        // otherwise keep iterating through the chained buckets until null is reached meaning the
        // end of the chained buckets linked list has been reached
        currentHashNode = currentHashNode.next;
      }
    }
    if (currentHashNode == null) {
      // if the node at that location is equal to null, a matching key does not exsit in the 
      // Hashtable, return false
      ret = false;
    }
    return ret;
  }
  
  /**
   * This method computes the HashCode and converts it to a HashCode index for any key value 
   * that is fed into it as a parameter. This method is called by insert() and get() to find
   * the index in the HashTable ArrayList to store or find the item. This method utilizes the 
   * java hashCode() method to calcuate a hash code, then it moduloes by the current HashTable
   * size to calculate an index.  
   * 
   * @param K key of the node that needs a HashCode converted into a HashTable index to put the 
   *    HashTable node or retrieve it from
   * @return return int index location in the HashTable for the key value parameter
   */
  private int getHashCodeIndex(K key) {
    int hashCode = key.hashCode();
    // calculate the HashCode for the key 
    int hashIndex = hashCode%currentCapacity;
     //divide the HashCode by the HashTable size and store the remainder as the index 
    if (hashIndex < 0 ) {
      // if the remainder is less than zero make this value positive by adding the current table
      // size to it so it still falls within the table capacity
      hashIndex = hashIndex + currentCapacity;
    }
    return hashIndex;
  }
  
  /**
   * This method returns the value of the node with a matching key as the passed parameter key. 
   * It searches for the matching key by calling on getHashCodeIndex() to generate the index 
   * location to search. From there this method searches (if any) "chained" buckects of linked
   * list are present. It stops iterating through the linked list when it hits null. This method 
   * does not remove key or decrease number of keys. If key passed as a parameter is equal to
   * null, it throws IllegalNullKeyException. If the key is not found, a KeyNotFoundException() 
   * is thrown.
   * 
   * @param K key to search for in the HashTable and return the associated value
   * @return the value of the node with a matching key
   * @throws java.util.IllegalNullKeyException if the key being searched for is null
   * @throws java.util.KeyNotFoundException if the key is not found
   * @see DataStructureADT#get(java.lang.Comparable)
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    V foundKey = null;
    // create a local instance variable of a value to return, set it equal to null
    if (key == null) {
      // if the key being searched for is null then throw a IllegalNullKeyException
      throw new IllegalNullKeyException();
    }

    int hashIndex = getHashCodeIndex(key);
    // generate the index location where to look for the key in the HashTable

    HashTableNode hashNode = hashTableArray.get(hashIndex);
    // get the HashTable node that is stored at that index location in the HashTable

    if (hashNode == null) {
      throw new KeyNotFoundException();
      // if the HashTable node at that location is null/empty the key does not exsit in the
      // HashTable so throw a KeyNotFoundException
    }
    while (hashNode != null) {
      // if there is a stored HashTable node at that index location start iterating through
      // any other HashTable nodes that are stored there (iterate through the linked list)
      if (hashNode.key.compareTo(key) == 0) {
        // if a matching key is found then access the value and return it
        foundKey = hashNode.value;
        break;
      }
      hashNode = hashNode.next;
      // iterate through the "chained" bucket linked list
    }
    return foundKey;
  }

  /**
   * Getter method that returns the number of HashTable nodes with valid <key,value> pairs within 
   * the HashTable. If the HashTable is empty then number of keys is equal to zero.
   * 
   * @return int value that counts the number of filled HashTaboe nodes in the HashTable
   * @see DataStructureADT#numKeys()
   */
  @Override
  public int numKeys() {
    return numKeys;
  }

  /**
   * Getter method that returns the load factor threshold that was passed into the constructor 
   * when creating the instance of the HashTable. Default load factor threshold if not specified 
   * in the constructor is 0.75. 
   * 
   * @return double that is the loadFactorThreshold for this specific HashTable
   */
  @Override
  public double getLoadFactorThreshold() {
    return loadFactorThreshold;
  }

  /**
   * Getter method that returns the load factor for the HashTable. Load factor is the number of
   * itesm in the HashTable divided by the current HashTable size. 
   * in the constructor is 0.75. 
   * 
   * @return double that is the loadFactor for this specific HashTable
   */
  @Override
  public double getLoadFactor() {
    double loadFactor = ((1.0*numKeys)/currentCapacity);
    // mulitple numerator by 1.0 to make sure the load factor is returned as a double, otherwise
    // int is divided by an int and would return a rounded value
    return loadFactor;
  }

  /**
   * Getter method that returns the current capacity (table size) for the HashTable ArrayList. 
   * The initial capacity must be a positive integer, 1 or greater and is specified in the 
   * constructor. The default table size if not specified in the constructor is 11. 
   * 
   * @return int that is the current capacity (table size) for the HashTable
   */
  @Override
  public int getCapacity() {
    return currentCapacity;
  }

  /**
   * Method that returns the collision resolution scheme used for this hash table. This HashTable
   * uses a system of "chained buckets" array of linked nodes when keys are inserted with the 
   * same insert index location in the HashTable ArrayList. 
   *  
   *  5 CHAINED BUCKET: array of linked nodes
   * 
   * @return int that codes this HashTables implementation of collision resolution
   */
  @Override
  public int getCollisionResolution() {
    // CHAINED BUCKET: array of linked nodes
    return 5;
  }
  
//  /**
//   * Method that is used for debugging. It allows a user to call this method from the main
//   * to print out the HashTable to the console so a user can see where <key, value> pairs 
//   * are being stored, how resizing and rehashing is working, along with collision resoltion.
//   * 
//   */
//  private void printHashTable() {
//    for(int i =0; i<currentCapacity; i++) {
//      // loop through the HashTable the number of times the size capacity is so all HashTable 
//      // nodes are visited
//      System.out.print("\nHashTableNode " + (i+1) + " : ");
//      // print to the console so the programer knows which node/idex an item is stored at
//      HashTableNode hashNode = hashTableArray.get(i);
//      
//      while(hashNode != null) {
//        // loop through all the linked HashTable nodes stored at that index (chained buckets)
//        System.out.print(hashNode.value + " ");
//        hashNode=hashNode.next;
//      } 
//    }
//    System.out.println(" ");
//    // print out an empty line at the end of each call to this method
//  }
//  
//  /**
//   * This main method is only used for debugging purposes. It creates 3 different instances
//   * of HashTables that accept <Float,String> , <String,String> , and <Integer,String>. 
//   * It inserts several items and calls a method to print the HashTable to the screed so a user
//   * can see where items are being stored and how linked node "chain buckets" work, along with 
//   * resizing and rehashing. 
//   * 
//   * @param String[] args unused
//   * @throws IllegalNullKeyException
//   * @throws DuplicateKeyException
//   */
//  public static void main(String[] args) throws IllegalNullKeyException, DuplicateKeyException {
//
//    // create a HashTable of <Float,String>
//    HashTable<Float, String> testFloatString = new HashTable<Float, String>(5, 0.55);
//    testFloatString.insert((float) 9.9, "tim");
//    testFloatString.insert((float) 2.2, "zach");
//    testFloatString.insert((float) 4.2, "oliver");
//    testFloatString.insert((float) 5.2, "wendy");
//    testFloatString.printHashTable();
//    // print out this HashTable
//    
//    // create a HashTable of <String,String>
//    HashTable<String, String> testStringString = new HashTable<String, String>(3, 0.55);
//    testStringString.insert("please", "please");
//    testStringString.insert("help", "help");
//    testStringString.insert("me", "me");
//    testStringString.insert("debug", "debug");
//    testStringString.insert("emily", "emily");
//    testStringString.insert("is", "is");
//    testStringString.insert("not", "not");
//    testStringString.insert("smart", "smart");
//    testStringString.insert("enough", "enough");
//    testStringString.insert("how", "how");
//    testStringString.insert("now", "now");
//    testStringString.insert("brown", "brown");
//    testStringString.insert("cow", "cow");
//    testStringString.printHashTable();
//    // print out this HashTable
//    
//    // create a HashTable <Integer,String>
//    HashTable<Integer, String> testIntegerString = new HashTable<Integer, String>(2, 0.55);
//    testIntegerString.insert(2, "Lets");
//    testIntegerString.insert(4, "Go");
//    testIntegerString.insert(8, "Bucky");
//    testIntegerString.insert(10, "On");
//    testIntegerString.insert(3, "Wisconsin");
//    testIntegerString.insert(5, "HooH");
//    testIntegerString.insert(7, "Raaa");
//    testIntegerString.printHashTable();
//    // print out this HashTable
//  }
}