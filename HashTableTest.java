//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: p3b
// Due Date: 3/28/19
// Files: DataStructureADTTest.java, HashTableADT.java, HashTable.java, HashTableTest.java,
// DuplicateKeyException.java, IllegalNullKeyException.java, KeyNotFoundException.java
// Course: CS 400, Spring 2018-2019
//
// Author: Emily Binversie
// Email: eebinversie@wisc.edu
// Lecturer's Name: Andrew Kuemmel
// Lecture Numebr: Lecture 004
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
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*;
import org.junit.jupiter.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.Random;

/**
 * This class tests for correct working implementations of a HashTable. A HashTable is a searchable
 * data structure that acheives constant time O(1) for lookup, insert, and delete. Node keys must 
 * be comparable types. A Hash Table maintains its efficiency by inserting <key,value> pairs based 
 * off of a generated Hash code that is converted into an index location based off of the current
 * HashTable size. The HashTable size will increase when the load factor (number of stored
 * items/table size) is equal to or greater than a determined load factor threshold. Resizing of a
 * Hash Table increases the size by ((size*2)+1) previously stored items will be rehashed to find
 * their new locations in the HashTable. When collisions occur (two items need to be at the same
 * index location) there are different ways to resovle collisions (open addressing or buckets).
 * These are several tests to test specific various methods to specifically classify what methods,
 * or aspects of an implementation of a HashTable is not working properly.
 * 
 * @author Emily Binversie
 * @version 1.0
 */
public class HashTableTest {

  /**
   * Tests that a HashTable returns an integer code indicating which collision resolution strategy
   * is used. REFER TO HashTableADT for valid collision scheme codes.
   */
  @Test
  public void test000_collision_scheme() {
    HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>();
    int scheme = htIntegerKey.getCollisionResolution();
    if (scheme < 1 || scheme > 9)
      fail("collision resolution must be indicated with 1-9");
  }

  /**
   * IMPLEMENTED AS EXAMPLE FOR YOU Tests that insert(null,null) throws IllegalNullKeyException
   */
  @Test
  public void test001_IllegalNullKey() {
    try {
      HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>();
      htIntegerKey.insert(null, null);
      fail("should not be able to insert null key");
    } catch (IllegalNullKeyException e) {
      // expected that an IllegalNullKeyException is throw when null is inserted
    } catch (Exception e) {
      fail("insert null key should not throw exception " + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that a newly created empty Hash Table has a numkey() return equal to zero. 
   * Meaning there are no elements in an empty table and it starts out at zero. 
   */
  @Test
  public void test002_numKeys_of_empty_hashtable_is_zero() {
    try {
      HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>();
      assertEquals(0, htIntegerKey.numKeys());
      // check the number of keys stored in an empty HashTable
    } catch (Exception e) {
      fail("calling numKeys() on an empty Hash table should not throw an "
          + "exception " + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that a newly created empty Hash Table has the correct initialCapacity when 
   * clarified by the constructor. 
   */
  @Test
  public void test003_checks_table_size_can_correctly_be_set_by_constructor() {
    try {
      HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>(10, 0.45);
      assertEquals(10, htIntegerKey.getCapacity());
      // check the capacity was correctly set by the constructor
    } catch (Exception e) {
      fail("calling getCapacity() on an empty Hash table should not throw an "
          + "exception " + e.getClass().getName());
      // print off a helpful message to the user/programer 
    }
  }
  
  /**
   * This checks that a newly created empty Hash Table has the correct load factor threshold when 
   * clarified by the constructor. 
   */
  @Test
  public void test004_checks_load_factor_threshold_can_correctly_be_set_by_constructor() {
    try {
      HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>(10, 0.45);
      if (htIntegerKey.getLoadFactorThreshold()!=0.45) {
        fail("incorrect load factor was returned that was set from the constructor");
      }
      // check the load factor threshold was correctly set by the constructor
    } catch (Exception e) {
      fail("calling getLoadFactor() on an empty Hash table should not throw an "
          + "exception " + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that an IllegalNullKeyException is correclty thrown when get() is called 
   * on a null key.
   */
  @Test
  public void test005_get_method_throw_IllegalNullKeyException_when_called_on_null() {
    try {
      HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>(10, 0.45);
      htIntegerKey.get(null);
      fail("should not be able to get a null key");
    } catch (IllegalNullKeyException e) {
      // expected that a IllegalNullKeyException is thrown when null is called on get()
    } catch (Exception e) {
      fail("getting a null key should not throw exception " + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that an KeyNotFoundException is correclty thrown when get() is called on a key
   * that is not in the HashTable. 
   */
  @Test
  public void test006_throw_KeyNotFoundException_when_get_called_on_a_key_not_in_the_hashTable() {
    try {
      HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>(10, 0.45);
      htIntegerKey.get(24);
      fail("should not be able to get a key that does not exsit in the hash table");
    } catch (KeyNotFoundException e) {
      // expected KeyNotFoundException is thrown when a key does not exsit in a HashTable
    } catch (Exception e) {
      fail("getting a key not in the hash table should not throw exception"
          + " " + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that an IllegalNullKeyException is correclty thrown when remove() is called 
   * on a null key.
   */
  @Test
  public void test007_remove_method_throw_IllegalNullKeyException_when_called_on_null() {
    try {
      HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>(10, 0.45);
      htIntegerKey.remove(null);
      fail("should not be able to remove a null key");
    } catch (IllegalNullKeyException e) {
      // expected IllegalNullKeyException when remove() is called on null
    } catch (Exception e) {
      fail("removing a null key should not throw exception " + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that an DuplicateKeyException is correclty thrown when insert() attempts to 
   * insert duplicate keys into a Hash Table. 
   */
  @Test
  public void test008_insert_method_throw_DuplicateKeyException_after_inserting_duplicate_keys() {
    try {
      HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>(10, 0.45);
      htIntegerKey.insert(24, "hello");
      htIntegerKey.insert(24, "world");
      fail("should not be able to insert a duplicate key");
    } catch (DuplicateKeyException e) {
      // expected that a DuplicateKeyException is thrown when duplicate keys are inserted
    } catch (Exception e) {
      fail("inserting duplicate keys should not throw exception " + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that remove() can correctly remove an exsiting <key,value> pair that is 
   * inserted into the HashTable.
   */
  @Test
  public void test009_remove_correctly_removes_an_exsiting_key() {
    try {
      HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>(10, 0.45);
      htIntegerKey.insert(24, "hello");
      htIntegerKey.insert(14, "world");
      htIntegerKey.remove(24);
      htIntegerKey.get(24);
      
    } catch (KeyNotFoundException e) {
      // expected KeyNotFoundException is thrown when a key has been removed from a hashTable
    } catch (Exception e) {
      fail("getting a key that has been removed not throw exception " + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that remove() can correctly remove an exsiting <key,value> pair that is 
   * inserted into the HashTable and the numKey is properly decremented by 1.
   */
  @Test
  public void test010_remove_correctly_removes_an_exsiting_key_and_numKey_decreases() {
    try {
      HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>(10, 0.45);
      htIntegerKey.insert(24, "hello");
      htIntegerKey.insert(14, "world");
      htIntegerKey.remove(24);
      
      // check that the number of keys is correct after 2 inserts and 1 removal
      if (htIntegerKey.numKeys()!=1) {
        fail("numKeys was not properly 1 after 2 inserts and a remove");
      }
    } catch (Exception e) {
      fail("getting a key that has been removed not throw exception " + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that get() can correctly retrieve an exsiting <key,value> pair that is 
   * inserted into the HashTable and return the correct value. 
   */
  @Test
  public void test011_get_correctly_retreives_the_correct_value_for_the_key() {
    try {
      HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>(10, 0.45);
      htIntegerKey.insert(24, "hello");
      htIntegerKey.insert(14, "world");
      htIntegerKey.insert(1, "what");
      htIntegerKey.insert(4, "is");
      htIntegerKey.insert(32, "the");
      htIntegerKey.insert(23, "weather");
      htIntegerKey.insert(88, "like");
      
      // check the correct value is returned when called get() on the values key
      if (htIntegerKey.get(4) != "is") {
        fail("the correct value was not returned from get() after several inserts");
      }
    } catch (Exception e) {
      fail("getting a value of a valid get should not throw exception " + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that resizing works to increase hash table size by ((currentsize*2)+1) when
   * the load factor is equal to or greater than the load factor threshold. 
   */
  @Test
  public void test012_check_resizing_works() {
    try {
      HashTableADT<String, String> htStringKey = new HashTable<String, String>(2, 0.45);
      htStringKey.insert("test", "1");
      // check that capacity is not changed after an insertion
      if (htStringKey.getCapacity()!=5) {
        fail("hash table did not appropriately increase in size when load factor became equal to"
            + "or greater than load factor threshold");
      }
    } catch (Exception e) {
      fail("getting a null key should not throw exception " + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that getLoadFactor() can correctly calculate the load factor after several 
   * inserts and a resize and rehash; 
   */
  @Test
  public void test013_get_load_factor_after_resize() {
    try {
      HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>(1, 0.45);
      htIntegerKey.insert(24, "hello");
      // load factor after insert (1/1) = 1 which 1 > 0.45         -->resize (1*2)+1=3 
      htIntegerKey.insert(14, "world");
      // load factor after insert (2/3) = 0.67 which 0.67 > 0.45   -->resize (3*2)+1=7 
      htIntegerKey.insert(1, "what");
      // load factor after insert (3/7) = 0.42 which 0.42 < 0.45 
      htIntegerKey.insert(4, "is");
      // load factor after insert (4/7) = 0.57 which 0.77 > 0.45   -->resize (7*2)+1=15   
      htIntegerKey.insert(32, "the");
      // load factor after insert (5/15) = 0.33 which 0.33 < 0.45 
      htIntegerKey.insert(23, "weather");
      // load factor after insert (6/17) = 0.4 which 0.4 < 0.45 
      htIntegerKey.insert(88, "like");
      // load factor after insert (7/17) = 0.46 which 0.46 > 0.45  -->resize (15*2)+1=31 
      
      // check the load factor is correctly calculated after serveral inserts/resizes/rehashs
      if (htIntegerKey.getLoadFactor() != (7.0/31.0)) {
        fail("the incorrect load factor was not calculated after 4 resizes, load factor equals "
            + "" + htIntegerKey.getLoadFactor() + " when it should be (7.0/31.0) instead "
                + "numKeys equals " +htIntegerKey.numKeys() +" and table size equals "
                    + "" +htIntegerKey.getCapacity());
      }
    } catch (Exception e) {
      fail("calculating a load factor after 4 resizes should not throw exception " 
          + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that calling get() does not remove any <key,value> pairs or decrease the number
   * of numKeys in the HashTable. 
   */
  @Test
  public void test014_check_get_does_not_decrease_numKey() {
    try {
      HashTableADT<Integer, String> htIntegerKey = new HashTable<Integer, String>(1, 0.45);
      htIntegerKey.insert(24, "hello");
      // load factor after insert (1/1) = 1 which 1 > 0.45         -->resize (1*2)+1=3 
      htIntegerKey.insert(14, "world");
      // load factor after insert (2/3) = 0.67 which 0.67 > 0.45   -->resize (3*2)+1=7 
      htIntegerKey.insert(1, "what");
      // load factor after insert (3/7) = 0.42 which 0.42 < 0.45 
      htIntegerKey.insert(4, "is");
      // load factor after insert (4/7) = 0.57 which 0.77 > 0.45   -->resize (7*2)+1=15   
      htIntegerKey.insert(32, "the");
      // load factor after insert (5/15) = 0.33 which 0.33 < 0.45 
      htIntegerKey.insert(23, "weather");
      // load factor after insert (6/17) = 0.4 which 0.4 < 0.45 
      htIntegerKey.insert(88, "like");
      // load factor after insert (7/17) = 0.46 which 0.46 > 0.45  -->resize (15*2)+1=31 
      htIntegerKey.get(14);
      htIntegerKey.get(1);
      htIntegerKey.get(88);
      htIntegerKey.get(32);
      htIntegerKey.get(4);
      
      assertEquals(7,htIntegerKey.numKeys());
      // verify that calling get() does not remove any <key,value> pairs from the HashTable
    } catch (Exception e) {
      fail("inserting and getting valid <key,value> pairs should not throw exception " 
         + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that calling get() returns the correct values after resizing and rehashing. 
   */
  @Test
  public void test015_check_get_returns_the_correct_value_after_resize_and_rehashing() {
    try {
      HashTableADT<Integer, Integer> htIntegerKey = new HashTable<Integer, Integer>(1, 0.45);
      htIntegerKey.insert(24, 1);
      // load factor after insert (1/1) = 1 which 1 > 0.45         -->resize (1*2)+1=3 
      htIntegerKey.insert(14, 2);
      // load factor after insert (2/3) = 0.67 which 0.67 > 0.45   -->resize (3*2)+1=7 
      htIntegerKey.insert(1, 3);
      // load factor after insert (3/7) = 0.42 which 0.42 < 0.45 
      htIntegerKey.insert(4, 4);
      // load factor after insert (4/7) = 0.57 which 0.77 > 0.45   -->resize (7*2)+1=15   
      htIntegerKey.insert(32, 5);
      // load factor after insert (5/15) = 0.33 which 0.33 < 0.45 
      htIntegerKey.insert(23, 6);
      // load factor after insert (6/17) = 0.4 which 0.4 < 0.45 
      htIntegerKey.insert(88, 7);
      // load factor after insert (7/17) = 0.46 which 0.46 > 0.45  -->resize (15*2)+1=31 
      if (!htIntegerKey.get(14).equals(2)) {
        fail("returned the wrong value of a <key,value> pair after a resize/rehash");
      }
      if (!htIntegerKey.get(1).equals(3)) {
        fail("returned the wrong value of a <key,value> pair after a resize/rehash");
      }
      if (!htIntegerKey.get(88).equals(7)) {
        fail("returned the wrong value of a <key,value> pair after a resize/rehash");
      }
      if (!htIntegerKey.get(32).equals(5)) {
        fail("returned the wrong value of a <key,value> pair after a resize/rehash");
      }
      if (!htIntegerKey.get(4).equals(4)) {
        fail("returned the wrong value of a <key,value> pair after a resize/rehash");
      }
    } catch (Exception e) {
      fail("inserting and getting valid <key,value> pairs should not throw exceptions after "
          + "rehashing and resizing " + e.getClass().getName());
       // print off a helpful message to the user/programer
    }
  }
  
  /**
   * This checks that HashTable<K,V> is generic and can be instantiated with Strings, Integers,
   * and Floats. 
   */
  @Test
  public void test016_check_generic_type_can_instantiate_string_integer_float() {
    try {
      // create a HashTable <Integer,String>
      HashTable<Integer, String> testIntegerString = new HashTable<Integer, String>(2, 0.55);
      testIntegerString.insert(2, "Lets");
      testIntegerString.insert(4, "Go");
      testIntegerString.insert(8, "Bucky");
      
      // create a HashTable of <String,String>
      HashTable<String, String> testStringString = new HashTable<String, String>(3, 0.55);
      testStringString.insert("Hello", "World");
      testStringString.insert("hello", "world");
      testStringString.insert("HELLO", "WORLD");
      
      // create a HashTable of <Float,String>
      HashTable<Float, String> testFloatString = new HashTable<Float, String>(5, 0.55);
      testFloatString.insert((float) 9.9, "tim");
      testFloatString.insert((float) 2.2, "zach");
      testFloatString.insert((float) 4.2, "oliver");
     
    } catch (Exception e) {
      fail("creating instances of Hash Table with Integers, Strings, and Floats should not "
          + "cause an " + e.getClass().getName());
      // print off a helpful message to the user/programer
    }
  }  
}