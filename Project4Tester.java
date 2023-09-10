import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Project4Tester {
  
  //Tests for Tokenizer class
  
  //test wordList() for file read constructor
  @Test
  public void testWordListFileRead() {
    //empty file
    Tokenizer empty = new Tokenizer("./JUnitTestingTxt/EmptyFile.txt");
    assertEquals(true, empty.wordList().isEmpty());
    //one word file
    Tokenizer oneWord = new Tokenizer("./JUnitTestingTxt/OneWordFile.txt");
    assertEquals("hello", oneWord.wordList().get(0));
    //many words file
    Tokenizer manyWord = new Tokenizer("./JUnitTestingTxt/ManyWordFile.txt");
    StringBuilder manyWordString = new StringBuilder();
    for (int i = 0; i < manyWord.wordList().size(); i++) {
      manyWordString.append(manyWord.wordList().get(i));
    }
    assertEquals("hellomynameisindigomontoyayoukilledmyfatherpreparetodie", 
                 manyWordString.toString());
    //many lines file
    Tokenizer manyLine = new Tokenizer("./JUnitTestingTxt/ManyLineFile.txt");
    StringBuilder manyLineString = new StringBuilder();
    for (int i = 0; i < manyLine.wordList().size(); i++) {
      manyLineString.append(manyLine.wordList().get(i));
    }
    assertEquals("hellomynameisindigomontoyayoukilledmyfatherpreparetodieifyoubreatheaword" +
                   "ortheedgeofawordiwillbringwithmeapointyreckoningthatwillshudderyou" +
                   "tznaaacealfredbatmansloyalbatlerenterscarryingatrayofgothham" +
                 "eatadinnermattresswayne",
                 manyLineString.toString());
  }
  //test wordList() for String[] read constructor
  @Test
  public void testWordListStringRead() {
    //empty String[]
    String[] emptyArray = new String[0];
    Tokenizer emptyToken = new Tokenizer(emptyArray);
    assertEquals(true, emptyToken.wordList().isEmpty());
    //one word String[]
    String[] oneWordArray = {"Hello."};
    Tokenizer oneWordToken = new Tokenizer(oneWordArray);
    assertEquals("hello", oneWordToken.wordList().get(0));
    //many words in one String String[]
    String[] manyWordArray = {"Hello. My name is Indigo Montoya. You killed my father. Prepare to die."};
    Tokenizer manyWordToken = new Tokenizer(manyWordArray);
    StringBuilder manyWordString = new StringBuilder();
    for (int i = 0; i < manyWordToken.wordList().size(); i++) {
      manyWordString.append(manyWordToken.wordList().get(i));
    }
    assertEquals("hellomynameisindigomontoyayoukilledmyfatherpreparetodie", 
                 manyWordString.toString());
    //many words in many Strings String[]
    String[] manyStringArray = {"Hello. My name is Indigo Montoya. You killed my father. Prepare to die.",
      "\n", 
      "If you breathe a word, or the edge of a word, I will bring with me a pointy reckoning that will shudder you.",
      "\t", "'Tz naaace", 
      "Alfred, Batman's loyal Batler, enters carrying a tray of goth ham. Eat a dinner, mattress Wayne."};
    Tokenizer manyStringToken = new Tokenizer(manyStringArray);
    StringBuilder manyStringsString = new StringBuilder();
    for (int i = 0; i < manyStringToken.wordList().size(); i++) {
      manyStringsString.append(manyStringToken.wordList().get(i));
    }
    assertEquals("hellomynameisindigomontoyayoukilledmyfatherpreparetodieifyoubreatheaword" +
                   "ortheedgeofawordiwillbringwithmeapointyreckoningthatwillshudderyou" +
                   "tznaaacealfredbatmansloyalbatlerenterscarryingatrayofgothham" +
                 "eatadinnermattresswayne",
                 manyStringsString.toString());
  }
  
  //Tests for HashTable class
  
 //test get()
  @Test
  public void testGet() {
    HashTable<String> table = new HashTable<String>(3);
    //test direct access
    table.put("a", "hello");
    assertEquals("hello", table.get("a"));
    //test in bucket
    table.put("f", "hi");               //by hash formula, "f" will hash to bucket of "a"
    assertEquals("hi", table.get("f"));
    //test after adding many new entries
    table.put("qwerty", "dvorak");
    table.put("wasd", "updownleftright");
    table.put("onetwothreefour", "unodostrescuatro");
    table.put("test", "testing");
    assertEquals("hi", table.get("f"));
  }
  
  //test put()
  @Test
  public void testPut() {
    HashTable<String> table = new HashTable<String>(3);
    //test put first
    table.put("a", "hello");
    assertEquals("hello", table.get("a"));
    //test put in bucket
    table.put("f", "hi");               //by hash formula, "f" will hash to bucket of "a"
    assertEquals("hi", table.get("f"));
    //test put after adding many entries
    table.put("qwerty", "dvorak");
    table.put("wasd", "updownleftright");
    table.put("onetwothreefour", "unodostrescuatro");
    table.put("test", "testing");
    assertEquals("testing", table.get("test"));
  }
  
  //test remove()
  @Test
  public void testRemove() {
    HashTable<String> table = new HashTable<String>();
    //test remove only element
    table.put("a", "hello");
    assertEquals("hello", table.remove("a"));
    assertEquals(0, table.size());
    try {
      table.get("a");                   //check that entry is gone
    }
    catch (NoSuchElementException e) {}
    //test remove with more than one element
    table.put("a", "hello");
    table.put("b", "hola");
    assertEquals("hello", table.remove("a"));
    assertEquals(1, table.size());
    int x = 0;
    try {
      table.get("a");                   
    }
    catch (NoSuchElementException e) {
      x = 1;                       //check that entry is gone
    }
    assertEquals(1, x);
    //test remove from bucket
    table.put("a", "hello");
    table.put("f", "hi");            //by hash formula, "f" will hash to bucket of "a"
    assertEquals("hi", table.remove("f"));
    assertEquals(2, table.size());
    x = 0;
    try {
      table.get("f");                  
    }
    catch (NoSuchElementException e) {
      x = 1;                          //check that entry is gone
    }
    assertEquals(1, x);
    //test remove bucket header
    table.put("f", "hi");
    assertEquals("hello", table.remove("a"));
    assertEquals(2, table.size());
    x = 0;
    try {
      table.get("a");                  
    }
    catch (NoSuchElementException e) {
      x = 1;
    }
    assertEquals(1, x);
  }
  
  //test size()
  @Test
  public void testSize() {
    HashTable<String> table = new HashTable<String>(3);
    //test empty table
    assertEquals(0, table.size());
    //test one length
    table.put("f", "hi");
    assertEquals(1, table.size());
    //test multiple length
    table.put("qwerty", "dvorak");
    table.put("wasd", "updownleftright");
    table.put("onetwothreefour", "unodostrescuatro");
    table.put("test", "testing");
    assertEquals(5, table.size());
    //test after remove
    table.remove("qwerty");
    assertEquals(4, table.size());
  }
  
  //Test for WordStat class
  
  //test wordCount()
  @Test
  public void testWordCount() {
    WordStat emptyStat = new WordStat("./JUnitTestingTxt/EmptyFile.txt");
    //empty text
    int x = 0;
    try {
      emptyStat.wordCount("hi");
    }
    catch (NoSuchElementException e) {
      x = 1;
    }
    assertEquals(1, x);         //check if exception was thrown
    //word appears 0 times
    WordStat statFile = new WordStat("./JUnitTestingTxt/ManyLineFile.txt");
    x = 0;
    try {
      statFile.wordCount("hola");
    }
    catch (NoSuchElementException e) {
      x = 1;
    }
    assertEquals(1, x);         //check if exception was thrown
    //word appears 1 time
    assertEquals(1, statFile.wordCount("montoya"));
    //word appears many times
    assertEquals(3, statFile.wordCount("you"));
    //word at front of text
    assertEquals(1, statFile.wordCount("hello"));
    //word at end of text
    assertEquals(1, statFile.wordCount("wayne"));
  }
  
  //test wordRank()
  @Test
  public void testWordRank() {
    //empty file
    WordStat emptyStat = new WordStat("./JUnitTestingTxt/EmptyFile.txt");
    int x = 0;
    try {
      emptyStat.wordRank("hi");
    }
    catch (NoSuchElementException e) {
      x = 1;
    }
    assertEquals(1, x);         //check if exception was thrown
    //first word in text
    WordStat statFile = new WordStat("./JUnitTestingTxt/NumbersFile.txt");
    assertEquals(4, statFile.wordRank("four"));
    //last word in text
    assertEquals(3, statFile.wordRank("five"));
    //most common word
    assertEquals(1, statFile.wordRank("twenty"));
    //least common word
    assertEquals(6, statFile.wordRank("three"));
    //tied words
    assertEquals(statFile.wordRank("not4"), statFile.wordRank("four"));
  }
  
  //test mostCommonWords()
  @Test
  public void testMostCommonWords() {
    WordStat statFile = new WordStat("./JUnitTestingTxt/NumbersFile.txt");
    //k == 0
    assertEquals(0, statFile.mostCommonWords(0).length);
    //k == 1
    assertEquals("twenty", statFile.mostCommonWords(3)[0]);
    //k > 1
    assertEquals("twenty", statFile.mostCommonWords(3)[0]);
    assertEquals("ten", statFile.mostCommonWords(3)[1]);
    assertEquals("five", statFile.mostCommonWords(3)[2]);
    //k < 1
    int x = 0;
    try {
      statFile.mostCommonWords(-1);
    }
    catch (IllegalArgumentException e) {
      x = 1;
    }
    assertEquals(1, x);      //check if exception was thrown
  }
  
  //test leastCommonWords()
  @Test
  public void testLeastCommonWords() {
    WordStat statFile = new WordStat("./JUnitTestingTxt/NumbersFile.txt");
    //k == 0
    assertEquals(0, statFile.leastCommonWords(0).length);
    //k == 1
    assertEquals("three", statFile.leastCommonWords(3)[0]);
    //k > 1
    assertEquals("three", statFile.leastCommonWords(4)[0]);
    assertEquals("not3", statFile.leastCommonWords(4)[1]);
    assertEquals("four", statFile.leastCommonWords(4)[2]);
    assertEquals("not4", statFile.leastCommonWords(4)[3]);
    //k < 1
    int x = 0;
    try {
      statFile.leastCommonWords(-1);
    }
    catch (IllegalArgumentException e) {
      x = 1;
    }
    assertEquals(1, x);      //check if exception was thrown
  }
  
  //test mostCommonCollocations()
  @Test
  public void testMostCommonCollocations() {
    WordStat statFile = new WordStat("./JUnitTestingTxt/NumbersFile.txt");
    //k == 0
    assertEquals(0, statFile.mostCommonCollocations(0, "ten", true).length);
    //baseword at front precede true
    assertEquals(null, statFile.mostCommonCollocations(1, "four", true)[0]);
    //baseword at front precede false
    assertEquals("twenty", statFile.mostCommonCollocations(1, "four", false)[0]);
    //baseword at end precede true
    assertEquals("twenty", statFile.mostCommonCollocations(1, "five", true)[0]);
    //baseword at end precede false
    assertEquals("five", statFile.mostCommonCollocations(1, "five", false)[0]);
    //baseword in middle precede true
    assertEquals("ten", statFile.mostCommonCollocations(1, "twenty", true)[0]);
    //baseword in middle precede false
    assertEquals("five", statFile.mostCommonCollocations(1, "not4", false)[0]);
    //k > 1 precede true
    assertEquals("twenty", statFile.mostCommonCollocations(3, "not4", true)[0]);
    assertEquals("ten", statFile.mostCommonCollocations(3, "not4", true)[1]);
    assertEquals("four", statFile.mostCommonCollocations(3, "not4", true)[2]);
    //k > 1 precede false
    assertEquals("twenty", statFile.mostCommonCollocations(3, "twenty", false)[0]);
    assertEquals("five", statFile.mostCommonCollocations(3, "twenty", false)[1]);
    assertEquals("not4", statFile.mostCommonCollocations(3, "twenty", false)[2]);
  }
}