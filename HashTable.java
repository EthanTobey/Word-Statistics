import java.util.NoSuchElementException;

public class HashTable<T> {
  private Entry<T>[] table;
  private int itemCount;
  private double maxLoadFactor = 1;
  //private double loadFactor;   -may or may not need
  
  @SuppressWarnings("unchecked")    //hides unchecked conversion warning
  //constructor that initializes with small capacity
  public HashTable() {
    table = new Entry[5];        //5 is default capacity
    itemCount = 0;
  }
  
  @SuppressWarnings("unchecked")    //hides unchecked conversion warning
  //constructor that initializes with given capacity
  public HashTable(int capacity) {
    table = new Entry[capacity];
    itemCount = 0;
  }
  
  @SuppressWarnings("unchecked")    //hides unchecked conversion warning
  //returns value at input key
  public T get(String key) throws NoSuchElementException {
    try {
      Entry temp = table[hashFunction(key)];
      if (temp.getKey().equals(key))
        return (T)temp.getValue();
      //check down bucket
      while (temp.next() != null) {
        if (temp.next().getKey().equals(key))
          return (T)temp.next().getValue();
        else
          temp = temp.next();
      }
    }
    catch (NullPointerException e) {
    }
    //if key was not found
    throw new NoSuchElementException();
  }
  
  //adds a new entry of input parameters into table
  @SuppressWarnings("unchecked")    //hides unchecked conversion warning
  public void put(String key, T value) {
    //get index given by hash function
    int index = hashFunction(key);
    //1. if index empty, add it there, check load factor, increase itemCount
    if (table[index] == null) {
      table[index] = new Entry(key, value);
      itemCount++;
      checkLoadFactor();
    }
    //if need to go into bucket
    else {
      Entry trav = table[index];
      //iterate through bucket, until end or checking if keys are same
      while (trav.next != null && key.hashCode() != trav.getKey().hashCode()) {
        trav = trav.next();
      }
      //if instance of Integer increase that element's value by input value
      if (trav.getKey().hashCode() == key.hashCode() && trav.getValue() instanceof Integer){
        trav.setValue((Integer)trav.getValue() + (Integer)value); //make new value sum of both integers
      }
      
      //if not integer but same keys
      else if (trav.getKey().hashCode() == key.hashCode()) {
        trav.setValue(value);  //replace old value with new one
      }
      else {
      //otherwise, add it to end, check load factor, increase itemCount
      trav.next = new Entry(key, value);
      itemCount++;
      checkLoadFactor();
      }
    }
  }
  
  @SuppressWarnings("unchecked")    //hides unchecked conversion warning
  //removes an entry from the table
  public T remove(String key) throws NoSuchElementException{
    Entry trav = table[hashFunction(key)];
    //check if entry is in main table
    if (trav.getKey().equals(key)) {
      //delete Entry & return its value
      T temp = (T)trav.getValue();
      table[hashFunction(key)] = trav.next();
      itemCount--;
      return temp;
    }
    
    //if not in main table could be in bucket or not be in hash
    
    //iterate down bucket, storing pointer towards parent on the way
    Entry parent = null;
    //iterate down bucket until find key
    while (trav.next() != null && !(trav.getKey().equals(key))) {
      parent = trav;
      trav = trav.next();
    }
    //when find, make parents next = temps next (take temp out of the chain)
    if (trav.getKey().equals(key)) {
      T temp = (T)trav.getValue();
      parent.setNext(trav.next());              //deleting desired entry
      itemCount--;
      return temp;
    }
    //if not in HashTable
    throw new NoSuchElementException();
  }
  
  //returns number of elements in table
  public int size() {
    return itemCount;
  }
  
  //returns hash function for a given key
  private int hashFunction(String key) {
    return Math.abs(key.hashCode()) % table.length;
  }
  
  //helper to check load factor of table
  private void checkLoadFactor() {
    //calculate load factor
    double loadFactor = (double)itemCount/(double)table.length;
    //check if need to rehash
    if (loadFactor >= maxLoadFactor)
      rehash();
  }
  
  @SuppressWarnings("unchecked")    //hides unchecked conversion warning
  //helper to rehash table 
  private void rehash() {
    //store old table
    Entry[] oldTable = table;
    //set table = new table of twice size
    table = new Entry[oldTable.length * 2];
    itemCount = 0;
    //add everything from old table into new table
    for (int i = 0; i < oldTable.length; i++) {
      Entry trav = oldTable[i];
      if (trav != null) {        //as long as there's something stored at index
        put(trav.getKey(), (T)trav.getValue());
        //iterate through buckets
        while (trav.next != null) {
          trav = trav.next();
          put(trav.getKey(), (T)trav.getValue());
        }
      }
    }
  }
  
  //nested class to hold entries in table
  public class Entry<T> implements Comparable<Entry<Integer>> {
    private String key;
    private T value;
    private Entry next;         //points towards next in bucket - using separate chaining
    
    //constructor for Entry
    public Entry(String key, T value) {
      this.key = key;
      this.value = value;
      this.next = null;
    }
    
    //compareTo method from Comparable
    @Override
    public int compareTo(Entry<Integer> o) {
      if ((Integer)getValue() > o.getValue())
        return 1;
      if ((Integer)getValue() < o.getValue())
        return -1;
      else         //if equal
        return 0;
    }
    
    //returns key
    public String getKey() {
      return key;
    }
    
    //returns value
    public T getValue() {
      return value;
    }
    
    //set value of Entry to given
    private void setValue(T value) {
      this.value = value;
    }
    
    //returns entry field
    private Entry next() {
      return this.next;
    }
    
    //set next pointer of entry
    private void setNext(Entry next) {
      this.next = next;
    }
    
  } 
}