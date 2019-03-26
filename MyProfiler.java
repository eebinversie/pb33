//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: p3b-201901
// Filename: MyProfiler.java
// Due Date: 3/28/19
// Files: DataStructureADTTest.java, HashTableADT.java, HashTable.java, HashTableTest.java,
// DuplicateKeyException.java, IllegalNullKeyException.java, KeyNotFoundException.java
// Course: CS 400, Spring 2019
//
// Author: Emily Binversie
// Email: eebinversie@wisc.edu
// Lecturer's Name: Andrew Kuemmel
// Lecture Numebr: Lecture 004
// Version: 1.0
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: NA
// Partner Email: NA
// Partner Lecturer's Name: NA
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: NONE
// Online Sources: NONE
// Bugs: NONE known
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////



// Used as the data structure to test our hash table against


import java.util.TreeMap;

/**
 * This program determines relative performance between an indiviuals implementation of a HashTable
 * and Java's build in TreeMap structure. A HashTable is a searchable data structure that acheives 
 * constant time O(1) for lookup, insert, and delete operations with comparable performance to 
 * Java's built-in TreeMap type. This program performs several inserts, lookups, and
 * removes to both of the data structures. This class tests to see which data structure performs
 * best. Because modern computers are so fast, lots of functions are performed in order to see
 * enough of a difference in performance. In addition, given the complexity analysis of different
 * lookups (single item vs range of values) for the two data structures is different, this program
 * experiments with different lookup operations. Consider to how to lookup many individual values,
 * and many different ranges of values from each structure.
 *
 * @author Emily Binversie
 * @version 1.0
 */
public class MyProfiler<K extends Comparable<K>, V> {

  HashTableADT<K, V> hashtable;
  // declares an instance of a HashTable
  TreeMap<K, V> treemap;
  // declares an instance of a TreeMap

  
  /**
   * This is a no arguement constructor that instantiates a single instance of an individuals 
   * version of a HashTable and TreeMap to evaluate their performance for numerous inserts, 
   * lookups, and removes. This Profile constructor instantiates an individuals implementation 
   * of their HashTable and Java's TreeMap.     
   */
  public MyProfiler() {
    hashtable = new HashTable<K, V>();
    // instantiate an individuals version of a HashTable and Java's TreeMap
    treemap = new TreeMap<K, V>();
    // instantiate a Java TreeMap
  }

  /**
   * This insert method inserts a K key and V value into both data structures (TreeMap and 
   * HashTable).  
   * 
   * @param <key> a key must not be null and must be Comparable
   * @param <value> the data value associated with a given key
   */
  public void insert(K key, V value) {
    treemap.put(key, value);
    // insert K, V into the TreeMap
    try {
      hashtable.insert(key, value);
      // insert K, V into the HashTable
    } catch (Exception ex) {
    }
  }

 /**
  * This method retrieves the value of the node with a matching key as the passed parameter key. 
  * It searches for the matching parameter key in both data structures (TreeMap and HashTable).
  * 
  * @param K key to search for in both datas structures
  */     
  public void retrieve(K key) {
    treemap.get(key);
    // get value V for key K from the TreeMap
    try {
      hashtable.get(key);
      // get value V for key K from the HashTable
    } catch (Exception ex) {

    }
  }

  /**
   * This is the main method. 
   * 
   * @param String[] args unused
   */ 
  public static void main(String[] args) {
    try {
      int numElements = Integer.parseInt(args[0]);
      MyProfiler<Integer, Integer> profile = new MyProfiler<Integer, Integer>();
      // creates a profile object

      for (int i = 0; i < numElements; i++) {
        // execute the insert method of profile as many times as numElements
        profile.insert(i, i);
      }

      for (int i = 0; i < numElements; i++) {
        // execute the retrieve method of profile as many times as numElements
        profile.retrieve(i);
      }

      // See, ProfileSample.java for example.


      String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
      System.out.println(msg);
    } catch (Exception e) {
      System.out.println("Usage: java MyProfiler <number_of_elements>");
      System.exit(1);
    }
  }
}
