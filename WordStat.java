import java.util.Collections;
import java.util.ArrayList;

public class WordStat {
  private Tokenizer token;                          //tokenizer of input text
  private HashTable<Integer> numOccurrencesTable;   //holds number of occurrences for each word
  private ArrayList rankedOrderList;                //list of all items in order by number occurrences
  private HashTable<Integer> rankedListHash;        //allows rankedOrerList to store Entries
  private HashTable<Integer> wordRankTable;
  
  
  //constructor where input is text file
  public WordStat(String file) {
    token = new Tokenizer(file);
    constructHelper();
  }
  
  //constructore where input is string array of text
  public WordStat(String[] text) {
    token = new Tokenizer(text);
    constructHelper();
  }
 
  //helper method for constructor
  @SuppressWarnings("unchecked")
  private void constructHelper() {
    //constructs numOccurrencesTable
    numOccurrencesTable = new HashTable<Integer>();
    for (int i = 0; i < token.wordList().size(); i++) {
      numOccurrencesTable.put(token.wordList().get(i), 1);
    }
    
    //constructs rankedOrderList
    rankedOrderList = new ArrayList();
    rankedListHash = new HashTable<Integer>();
    //add entries from numOccurrencesTable to rankedOrderList array
    for (int i = 0; i < token.wordList().size(); i++) {
      rankedOrderList.add(rankedListHash.new Entry<Integer>
                          (token.wordList().get(i), numOccurrencesTable.get(token.wordList().get(i))));
    }
    Collections.sort(rankedOrderList);
        
    //get rid of duplicates in rankedOrderList
    ArrayList temp = new ArrayList();
    for (int i = 0; i < rankedOrderList.size(); i++) {
      HashTable.Entry current = (HashTable.Entry)rankedOrderList.get(i);
      HashTable.Entry prev = null;
      if (!temp.isEmpty())
        prev = (HashTable.Entry)temp.get(temp.size() - 1);
      if (temp.isEmpty() || !prev.getKey().equals(current.getKey()))
        temp.add(current);
    } 
    
    rankedOrderList = temp;
    Collections.reverse(rankedOrderList);      //put in descending order
    
    //construct wordRankTable
    wordRankTable = new HashTable<Integer>();
    int lastIndex = 0;
    Integer lastItem = 0;
    for (int i = 0; i < rankedOrderList.size(); i++) {
      HashTable.Entry e = (HashTable.Entry)rankedOrderList.get(i);     //typecast to type Entry
      int value = 0;                                            //tracks what ranking value of entry should be
      //keep ranks same for equal quantity words
      if (lastItem.equals((Integer)e.getValue()))
        value = lastIndex;
      else
        value = i;   
      wordRankTable.put(e.getKey().toString(), value + 1);
      lastItem = (Integer)e.getValue();
      lastIndex = value;
    }
  }
  
  //return number of times word input appears in text
  public int wordCount(String word) {
    return numOccurrencesTable.get(word);
  }
  
  //returns rank of given word
  public int wordRank(String word) {
    return wordRankTable.get(word);
  }
  
  //return string[] of k most common words
  public String[] mostCommonWords(int k) throws IllegalArgumentException {
    if (k < 0) 
      throw new IllegalArgumentException();
    
    String[] output = new String[k];
    for (int i = 0; i < k && i < rankedOrderList.size(); i++) {
      HashTable.Entry current = (HashTable.Entry)rankedOrderList.get(i);
      output[i] = current.getKey();
    }
    return output;
  }
  
  //return string[] of k least common words
  public String[] leastCommonWords(int k) {
    if (k < 0) 
      throw new IllegalArgumentException();
    
    String[] output = new String[k];
    for (int i = rankedOrderList.size() - 1, index = 0; i > (rankedOrderList.size() - 1 - k) && i > 0; i--, index++) {
      HashTable.Entry current = (HashTable.Entry)rankedOrderList.get(i);
      output[index] = current.getKey();
    }
    return output;
  }
 
  
  //returns k most common words either before or after baseWord depending on whether precede is true or false
  @SuppressWarnings("unchecked")
  public String[] mostCommonCollocations(int k, String baseWord, boolean precede) throws IllegalArgumentException {
    if (!token.wordList().contains(baseWord) || k < 0)
      throw new IllegalArgumentException();
    
    ArrayList tokenCopy = new ArrayList();
    //append all words before baseWord
    if (precede == true) {
      int i = 0;
      while (!token.wordList().get(i).equals(baseWord)) {
        tokenCopy.add(token.wordList().get(i));
        i++;
      }
    }
    //append all words after baseWord
    else {
      for (int i = token.wordList().size() - 1; i > token.wordList().indexOf(baseWord); i--) {
        tokenCopy.add(token.wordList().get(i));
        i--;
      }
    }
    
    //make string to pass into WordStat constructor
    String[] tokenCopyString = new String[tokenCopy.size()];
    for (int i = 0; i < tokenCopyString.length; i++) {
      tokenCopyString[i] = (String)tokenCopy.get(i);
    }
    WordStat mini = new WordStat(tokenCopyString);   //WordStat to compute most common words in tokenCopy
    return mini.mostCommonWords(k);
  }
}