import tester.*;
import java.util.Random;
import javalib.worldimages.*;
import javalib.funworld.*;
import java.awt.Color;

//represents a list of wordList
interface ILoWord {

  Color active = Color.red;
  Color inactive = Color.black;
  int height = 500;
  int width = 500;
  int fsize = 50;

  // produces an ILoWord that is like this ILoWord but with the given IWord added at the end
  ILoWord addToEnd(IWord extraWord);

  // draws all of the words in this ILoWord onto the given WorldScene
  WorldScene draw(WorldScene scene);

  // moves every word by moving the y coordinate down
  ILoWord move();

  // returns true if this ILoWord contains a word that starts with the letter match
  boolean hasWord(String match);

  // makes a word active and then removes the first letter
  ILoWord makeAndRemove(String match);
  
  // removes a letter from a word in ILoWord if it starts with the letter match
  ILoWord removeLetter(String match);
 
  // produces an ILoWord with any IWords that have an empty string are filtered out
  ILoWord filterOutEmpties();

  // returns true if this ILoWord has any active word
  boolean activeList();

  // checks to see if there are any words in the wordList that are past the boundaries 
  boolean wordOver();

}

// represents an empty list of wordList
class MtLoWord implements ILoWord {

  /*
   * TEMPLATE:
   * METHODS:
   * ... this.addToEnd(IWord addedWord) ...                    --- ILoWord 
   * ... this.draw(WorldScene scene) ...                       --- WorldScene 
   * ... this.move() ...                                       --- ILoWord
   * ... this.hasWord(String match) ...                        --- Boolean 
   * ... this.filterOutEmpties() ...                           --- ILoWord 
   * ... this.activeList() ...                                 --- boolean
   * ... this.makeAndRemove(String match) ...                  --- boolean 
   * ... this.removeLetter(); ....                             --- ILoWord
   * ... this.wordOver() ...                                   --- boolean
   */

  // In MtLoWord
  // produces an ILoWord that is like this ILoWord but with the given IWord added at the end
  public ILoWord addToEnd(IWord addedWord) {
    return new ConsLoWord(addedWord, new MtLoWord());
  }

  // In MtLoWord
  // draws all of the words in this ILoWord onto the given WorldScene
  public WorldScene draw(WorldScene scene) {
    return scene;
  }

  // In MtLoWord
  // Moves every word by moving the y coordinate down
  public ILoWord move() {
    return this;
  }

  // In MtLoWord
  // returns true if this ILoWord contains a word that starts with the letter match
  public boolean hasWord(String match) {
    return false;
  }

  // In MtLoWord
  // makes a word active and then removes the first letter
  public ILoWord makeAndRemove(String match) {
    return this;
  }
  
  // In MtLoWord
  // removes a letter from a word in ILoWord if it starts with the letter match
  public ILoWord removeLetter(String match) {
    return this;
  }

  // In MtLoWord
  // produces an ILoWord with any IWords that have an empty string are filtered out
  public ILoWord filterOutEmpties() {
    
    return this;
  }

  // In MtLoWord
  // returns true if this ILoWord has any active word
  public boolean activeList() {
    return false;
  }

  // In MtLoWord
  // checks to see if there are any words in the wordList that are past the boundaries 
  public boolean wordOver() {
    return false;
  }
}

// represents a non-empty list of wordList
class ConsLoWord implements ILoWord {
  IWord first;
  ILoWord rest;

  // the constructor
  ConsLoWord(IWord first, ILoWord rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * TEMPLATE
   * 
   * FIELDS: 
   * ... this.first ...                                        --- IWord 
   * ... this.rest ...                                         --- ILoWord
   * 
   * METHODS 
   * ... this.addToEnd(IWord addedWord) ...                    --- ILoWord 
   * ... this.draw(WorldScene scene) ...                       --- WorldScene 
   * ... this.move() ...                                       --- ILoWord
   * ... this.hasWord(String match) ...                        --- Boolean 
   * ... this.filterOutEmpties() ...                           --- ILoWord 
   * ... this.activeList() ...                                 --- boolean
   * ... this.makeAndRemove(String match) ...                  --- boolean 
   * ... this.removeLetter(); ....                             --- ILoWord
   * ... this.wordOver() ...                                   --- boolean
   * 
   * METHODS FOR FIELDS:
   * ... this.rest.checkAndReduce(String letter) ...           --- ILoWord
   * ... this.rest.addToEnd(IWord word) ...                    --- ILoWord
   * ... this.rest.filterOutEmpties() ...                      --- ILoWord
   * ... this.rest.draw(WorldScene world) ...                  --- WorldScene
   * ... this.first.letterMatch(String letter) ...             --- boolean
   * ... this.first.deleteWord() ...                           --- IWord
   * ... this.first.emptyCheck() ...                           --- boolean
   * ... this.first.addImage(WorldScene world) ...             --- WorldScene
   */
  

  // In ConsLoWord
  // produces an ILoWord that is like this ILoWord but with the given IWord added at the end
  public ILoWord addToEnd(IWord addedWord) {
    return new ConsLoWord(this.first, this.rest.addToEnd(addedWord));
  }

  // In ConsLoWord
  // draws all of the words in this ILoWord onto the given WorldScene
  public WorldScene draw(WorldScene scene) {
    return this.rest.draw(this.first.placeImage(scene, active, inactive));
  }

  // In ConsLoWord
  // Moves every word by moving the y coordinate down
  public ILoWord move() {
    return new ConsLoWord(this.first.moveWord(), this.rest.move());
  }

  // In ConsLoWord
  // returns true if this ILoWord contains a word that starts with the letter match
  public boolean hasWord(String match) {
    if (this.first.letterMatch(match)) {
      return true;
    }
    else {
      return this.rest.hasWord(match);
    }
  }

  // In ConsLoWord
  // makes a word active and then removes the first letter
  public ILoWord makeAndRemove(String match) {
    if (this.first.letterMatch(match)) {
      return new ConsLoWord(this.first.removeFirst(), this.rest);
    }
    else {
      return new ConsLoWord(this.first, this.rest.makeAndRemove(match));
    }
  }
  
  // In ConsLoWord
  // removes a letter from a word in ILoWord if it starts with the letter match
  public ILoWord removeLetter(String match) {
    if (this.first.isActive() && this.first.letterMatch(match)) {
      return new ConsLoWord(this.first.deleteWord(), this.rest);
    }
    else {
      return new ConsLoWord(this.first, this.rest.removeLetter(match));
    }
  }

  // In ConsLoWord
  // produces an ILoWord with any IWords that have an empty string are filtered out
  public ILoWord filterOutEmpties() {
    if (this.first.emptyCheck()) {
      return this.rest.filterOutEmpties();
    }
    return new ConsLoWord(this.first, this.rest.filterOutEmpties());
  }

  // In ConsLoWord
  // returns true if this ILoWord has any active word
  public boolean activeList() {
    return this.first.isActive() || this.rest.activeList();
  }

  // In ConsLoWord
  // checks to see if there are any words in the wordList that are past the boundaries 
  public boolean wordOver() {
    return this.first.gameOver() || this.rest.wordOver();
  }
}

// represents a word in the ZType game
interface IWord {

  // checks if a word starts with the singleLetter
  boolean letterMatch(String singleLetter);

  // removes the first letter in the word
  IWord deleteWord();

  // places the image into the WorldScene
  WorldScene placeImage(WorldScene scene, Color active, Color inactive);

  // moves the word 10 units down
  IWord moveWord();

  // checks if an element in a list is empty
  boolean emptyCheck();

  // checks to see if the word is an active word
  boolean isActive();

  // makes the word active and removes the first letter
  IWord removeFirst();

  // checks if the word is over the games bottom boundary
  boolean gameOver();

}

// represents an active word in the ZType game
class ActiveWord implements IWord {
  String word;
  int x;
  int y;

  // the constructor
  ActiveWord(String word, int x, int y) {
    this.word = word;
    this.x = x;
    this.y = y;
  }

  /*
   * TEMPLATE:
   * FIELDS:
   * ... this.word ...                                  --- String 
   * ... this.x ...                                     --- int 
   * ... this.y ...                                     --- int
   * 
   * METHODS:
   * ... this.letterMatch(String singleLetter) ...      --- boolean 
   * ... this.deleteWord() ...                          --- IWord 
   * ... this.placeImage(WorldScene scene) ...          --- WorldScene 
   * ... this.moveWord() ...                            --- IWord 
   * ... this.emptyCheck() ...                          --- boolean 
   * ... this.removeFirst() ...                         --- IWord 
   * ... this.isActive() ...                            --- boolean 
   * ... this.gameOver() ...                            --- boolean
   */

  // In ActiveWord
  // Checks if this word starts with the singleLetter
  public boolean letterMatch(String singleLetter) {
    return this.word.startsWith(singleLetter);
  }

  // In ActiveWord
  // removes the first letter in the word
  public IWord deleteWord() {
    return new ActiveWord(this.word.substring(1), this.x, this.y);
  }

  // In ActiveWord
  // places the image into the WorldScene
  public WorldScene placeImage(WorldScene scene, Color active, Color inactive) {
    return scene.placeImageXY(new TextImage(this.word, active), this.x, this.y);
  }

  // In ActiveWord
  // moves the word 10 units down
  public IWord moveWord() {
    return new ActiveWord(this.word, this.x, this.y + 10);
  }

  // In ActiveWord
  // checks if an element in a list is empty
  public boolean emptyCheck() {
    return this.word.equals("");
  }

  // In ActiveWord
  // makes the word active and removes the first letter
  public IWord removeFirst() {
    if (this.word.length() <= 1) {
      return this;
    }
    return new ActiveWord(this.word.substring(1), this.x, this.y);
  }

  // In ActiveWord
  // returns true if this word is an active word
  public boolean isActive() {
    return true;
  }

  // In ActiveWord
  // checks if the word is over the games bottom boundary
  public boolean gameOver() {
    return this.y >= 470;
  }
}

// represents an inactive word in the ZType game
class InactiveWord implements IWord {
  String word;
  int x;
  int y;

  // the constructor
  InactiveWord(String word, int x, int y) {
    this.word = word;
    this.x = x;
    this.y = y;
  }

  /*
   * TEMPLATE:
   * FIELDS:
   * ... this.word ...                                  --- String 
   * ... this.x ...                                     --- int 
   * ... this.y ...                                     --- int
   * 
   * METHODS:
   * ... this.letterMatch(String singleLetter) ...      --- boolean 
   * ... this.deleteWord() ...                          --- IWord 
   * ... this.placeImage(WorldScene scene) ...          --- WorldScene 
   * ... this.moveWord() ...                            --- IWord 
   * ... this.emptyCheck() ...                          --- boolean 
   * ... this.removeFirst() ...                         --- IWord 
   * ... this.isActive() ...                            --- boolean 
   * ... this.gameOver() ...                            --- boolean
   */

  // In InactiveWord
  // Checks if this word starts with the singleLetter
  public boolean letterMatch(String singleLetter) {
    return this.word.startsWith(singleLetter);
  }

  // In InactiveWord
  // removes the first letter in the word
  public IWord deleteWord() {
    return new InactiveWord(this.word.substring(1), this.x, this.y);
  }

  // In InactiveWord
  // places the image into the WorldScene
  public WorldScene placeImage(WorldScene scene, Color active, Color inactive) {
    return scene.placeImageXY(new TextImage(this.word, inactive), this.x, this.y);
  }

  // In InactiveWord
  // moves the word 10 units down
  public IWord moveWord() {
    return new InactiveWord(this.word, this.x, this.y + 10);
  }

  // checks if an element in a list is empty
  public boolean emptyCheck() {
    return this.word.equals("");
  }

  // In InactiveWord
  // makes the word active and removes the first letter
  public IWord removeFirst() {
    if (this.word.length() <= 1) {
      return this;
    }
    return new ActiveWord(this.word.substring(1), this.x, this.y);
  }

  // In InactiveWord
  // Returns true if this word is an active word
  public boolean isActive() {
    return false;
  }

  // In InactiveWord
  // checks if the word is over the games bottom boundary
  public boolean gameOver() {
    return this.y >= 470;
  }
}

// represents the ZTypeWorld
class ZTypeWorld extends World {
  ILoWord wordList;
  int tickCounter;
  Random rand;
  int score;
  /*
   * TEMPLATE
   * 
   * FIELDS: 
   * ... this.listOfWords ...                                 --- ILoWord
   * ... this.tickCounter ...                                 --- int
   * ... this.rand ...                                        --- Random
   * ... this.score ...                                       --- int
   * 
   * METHODS 
   * ... this.makeScene() ...                                 --- WorldScene 
   * ... this.onTick() ...                                    --- World
   * ... this.onKeyEvent(String match) ...                    --- World 
   * ... this.worldEnds() ...                                 --- WorldEnd 
   * ... this.makeEndGameScene() ...                          --- WorldScene
   * 
   * METHODS FOR FIELDS:
   * ... this.wordList.checkAndReduce(String letter) ...      --- ILoWord
   * ... this.wordList.addToEnd(IWord word) ...               --- ILoWord
   * ... this.wordList.filterOutEmpties() ...                 --- ILoWord
   * ... this.wordList.draw(WorldScene world) ...             --- WorldScene  
   */

  // In ZTypeWorld
  // the constructor
  ZTypeWorld(ILoWord wordList, int tickCounter, int score) {
    this.wordList = wordList;
    this.tickCounter = tickCounter;
    this.rand = new Random();
    this.score = score;
  }
  
  // In ZTypeWorld
  // creates a WorldScene with the list of wordList
  public WorldScene makeScene() {
    WorldImage scoreTxt = new TextImage("SCORE: " + Integer.toString(this.score), 18, Color.green);
    WorldImage levelTxt = new TextImage("Level " + Integer.toString((this.score / 25) % 10), 12,
        Color.red);
    WorldScene world = new WorldScene(ILoWord.width, ILoWord.height)
        .placeImageXY(new RectangleImage(ILoWord.width * 2, ILoWord.height * 2, OutlineMode.SOLID,
            Color.white), 0, 0)
        .placeImageXY(scoreTxt, ILoWord.width / 2, (int) (ILoWord.height * .9))
        .placeImageXY(levelTxt, (int) (ILoWord.width * .9), (int) (ILoWord.height * .9));
    return this.wordList.draw(world);
  }

  // In ZTypeWorld
  // every tick, move the words down one unit and for every 2 ticks, adds a new
  // word to the game
  public World onTick() {
    this.wordList = this.wordList.filterOutEmpties();
    if (tickCounter == 2) {
      Utils u1 = new Utils(this.rand);
      IWord newWord = new InactiveWord(u1.randomWord(),
          new Random().nextInt(ILoWord.width - 6 * ILoWord.fsize) + 3 * ILoWord.fsize, 0);
      ILoWord newList = this.wordList.addToEnd(newWord);
      return new ZTypeWorld(newList, 0, this.score);
    }
    else {
      ILoWord newList = this.wordList.move();
      return new ZTypeWorld(newList, this.tickCounter + 1, this.score);

    }
  }

  // In ZTypeWorld
  // checks when the user presses a key and if it corresponds to the match string
  public World onKeyEvent(String match) {
    if (this.wordList.wordOver()) {
      return this.endOfWorld("GAME OVER");
    }
    if (this.wordList.activeList()) {
      ILoWord newList = this.wordList.removeLetter(match);
      this.score++;
      return new ZTypeWorld(newList, this.tickCounter, this.score);
    }
    else {
      if (this.wordList.hasWord(match)) {
        ILoWord newList = this.wordList.makeAndRemove(match);
        score++;
        return new ZTypeWorld(newList, this.tickCounter, this.score);
      }
      else {
        return this;
      }
    }
  }

  // In ZTypeWorld
  // ends the game if there is a word over the boundary
  public WorldEnd worldEnds() {
    if (this.wordList.wordOver()) {
      return new WorldEnd(true, this.makeEndGameScene());
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }

  // In ZTypeWorld
  // draws the final scene
  public WorldScene makeEndGameScene() {
    WorldImage endImg = new TextImage("GAME OVER!\nPRESS \"R\" TO RESTART", 36, Color.blue);
    return new WorldScene(ILoWord.width, ILoWord.height).placeImageXY(endImg, ILoWord.width / 2,
        ILoWord.height / 2);    
  }
}

class Utils {
  Random rand;

  /*
   * TEMPLATE
   * 
   * FIELDS: 
   * ... this.seed ...                                                     --- Random
   * 
   * METHODS 
   * ... this.randomWord() ...                                             --- String 
   * ... this.addLetter(String current, int count, Random seed) ...        --- String
   */
  
  
  Utils(Random seed) {
    this.rand = seed;
  }

  public String randomWord() {
    return addLetter("", (int) (Math.random() * 5) + 2, rand);
  }

  // adds a random letter to the current string
  public String addLetter(String word, int count, Random rand) {
    if (count == 0) {
      return word;
    }
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    return addLetter(word + alphabet.charAt((int) (rand.nextInt(26))), count - 1, rand);
  }
}

// examples of ZTYpeWorlds
class ExamplesZTypeWorld {
  
  /*
   * exampleWordA is the word "hello" 
   * exampleWordB is the word "world"
   * exampleWordC is the word "computer" 
   * exampleWordD is the word "ball"
   * exampleWordE is the word "rain" 
   * exampleWordF is an empty word ""
   * 
   * emptyList is an empty list of words 
   * listExampleA is a list of words containing just exampleWordA 
   * listExampleB is a list of words containing just exampleWordB 
   * listExampleC is a list of words containing exampleWordC, exampleWordD, and listExampleB 
   * listExampleD is a list of words containing exampleWordE, exampleWordA, and listExampleC 
   * listExampleE is a list of words containing exampleWordA, exampleWordB, and list ExampleD 
   * listExampleF is a list of words containing exampleWordA, exampleWordA, and listExampleE
   * listExampleG is a list of words containing just exampleWordC and exampleWordF
   * listExampleH is a list of words containing just exampleWordD and exampleWordC
   * listExampleI is a list of words containing just exampleWordE and exampleWordB
   * listExampleJ is a list of words containing just exampleWordD and exampleWordE
   * listExampleK is a list of words containing just exampleWordC and exampleWordB
   * listExampleL is a list of words containing just exampleWordD and exampleWordC
   * listExampleM is a list of words containing exampleWordA, exampleWordF, and exampleWordF
   * 
   * emptyImage is a TextImage with a bland word
   * exampleRing is a TextImage with the word "ring"
   * exampleBottle is a TextImage with the word "bottle"
   * exampleZebra is a TextImage with the word "zebra"
   * 
   * world1 is a ZTypeWorld with wordList of listExampleA
   * world2 is a ZTypeWorld with wordList of listExampleC
   * world1 is a ZTypeWorld with wordList of listExampleI
   */

  IWord exampleWordA = new ActiveWord("Hello", 20, 40);
  IWord exampleWordB = new InactiveWord("world", 10, 10);
  IWord exampleWordC = new ActiveWord("Computer", 0, 0);
  IWord exampleWordD = new InactiveWord("ball", 0, 0);
  IWord exampleWordE = new ActiveWord("rain", 30, 30);
  IWord exampleWordF = new InactiveWord("", 0, 0);

  ILoWord emptyList = new MtLoWord();
  ILoWord listExampleA = new ConsLoWord(this.exampleWordA, this.emptyList);
  ILoWord listExampleB = new ConsLoWord(this.exampleWordB, this.emptyList);
  ILoWord listExampleC = new ConsLoWord(this.exampleWordC,
      new ConsLoWord(this.exampleWordD, this.listExampleB));
  ILoWord listExampleD = new ConsLoWord(this.exampleWordE,
      new ConsLoWord(this.exampleWordA, this.listExampleC));
  ILoWord listExampleE = new ConsLoWord(this.exampleWordA,
      new ConsLoWord(this.exampleWordB, this.listExampleD));
  ILoWord listExampleF = new ConsLoWord(this.exampleWordA,
      new ConsLoWord(this.exampleWordA, this.listExampleE));
  ILoWord listExampleG = new ConsLoWord(this.exampleWordC,
      new ConsLoWord(this.exampleWordF, this.emptyList));
  ILoWord listExampleH = new ConsLoWord(this.exampleWordD,
      new ConsLoWord(this.exampleWordC, this.emptyList));
  ILoWord listExampleI = new ConsLoWord(this.exampleWordE,
      new ConsLoWord(this.exampleWordB, this.emptyList));
  ILoWord listExampleJ = new ConsLoWord(this.exampleWordD,
      new ConsLoWord(this. , this.emptyList));
  ILoWord listExampleK = new ConsLoWord(this.exampleWordC,
      new ConsLoWord(this.exampleWordB, this.emptyList));
  ILoWord listExampleL = new ConsLoWord(this.exampleWordD,
      new ConsLoWord(this.exampleWordC, this.emptyList));
  ILoWord listExampleM = new ConsLoWord(this.exampleWordA,
      new ConsLoWord(this.exampleWordF, new ConsLoWord(this.exampleWordF, this.emptyList)));
  
  ZTypeWorld world1 = new ZTypeWorld(this.listExampleA, 0, 0);
  ZTypeWorld world2 = new ZTypeWorld(this.listExampleC, 0, 0);
  ZTypeWorld world3 = new ZTypeWorld(this.listExampleI, 0, 0);
  
  Color active = Color.red;
  Color inactive = Color.black;
  Random rand = new Random(123);
  Utils u1 = new Utils(rand);
  int height = 500;
  int SCREEN_WIDTH = 500;

  // tests the method addToEnd in the classes MtLoWord and ConsLoWord
  boolean testAddToEnd(Tester t) {
    return t.checkExpect(this.emptyList.addToEnd(this.exampleWordA),
        new ConsLoWord(this.exampleWordA, this.emptyList))
        && t.checkExpect(this.listExampleA.addToEnd(this.exampleWordB),
            new ConsLoWord(this.exampleWordA, new ConsLoWord(this.exampleWordB, this.emptyList)))
        && t.checkExpect(this.listExampleC.addToEnd(this.exampleWordC),
            new ConsLoWord(this.exampleWordC,
                new ConsLoWord(this.exampleWordD,
                    new ConsLoWord(this.exampleWordB,
                        new ConsLoWord(this.exampleWordC, this.emptyList)))))
        && t.checkExpect(this.listExampleE.addToEnd(this.exampleWordE),
            new ConsLoWord(this.exampleWordA,
                new ConsLoWord(this.exampleWordB,
                    new ConsLoWord(this.exampleWordE,
                        new ConsLoWord(this.exampleWordA,
                            new ConsLoWord(this.exampleWordC,
                                new ConsLoWord(this.exampleWordD, new ConsLoWord(this.exampleWordB,
                                    new ConsLoWord(this.exampleWordE, this.emptyList)))))))))
        && t.checkExpect(this.listExampleD.addToEnd(this.exampleWordD),
            new ConsLoWord(this.exampleWordE,
                new ConsLoWord(this.exampleWordA,
                    new ConsLoWord(this.exampleWordC,
                        new ConsLoWord(this.exampleWordD,
                            new ConsLoWord(this.exampleWordB,
                                new ConsLoWord(this.exampleWordD, this.emptyList)))))));
  }


  // tests the method draw in the classes MtLoWord and ConsLoWord
  boolean testDraw(Tester t) {
    WorldScene scene = new WorldScene(100, 100);

    return t.checkExpect(emptyList.draw(scene), emptyList.draw(scene))
        && t.checkExpect(listExampleA.draw(scene),
            emptyList.draw(scene).placeImageXY(new TextImage("Hello", Color.black), 20, 40))
        && t.checkExpect(listExampleB.draw(scene),
            emptyList.draw(scene).placeImageXY(new TextImage("world", Color.black), 10, 10))
        && t.checkExpect(listExampleF.draw(scene),
            emptyList.draw(scene).placeImageXY(new TextImage("Hello", Color.black), 20, 40)
                .placeImageXY(new TextImage("Hello", Color.black), 20, 40)
                .placeImageXY(new TextImage("Hello", Color.black), 20, 40)
                .placeImageXY(new TextImage("world", Color.black), 10, 10)
                .placeImageXY(new TextImage("rain", Color.black), 30, 30)
                .placeImageXY(new TextImage("Hello", Color.black), 20, 40)
                .placeImageXY(new TextImage("Computer", Color.black), 0, 0)
                .placeImageXY(new TextImage("ball", Color.black), 0, 0)
                .placeImageXY(new TextImage("world", Color.black), 10, 10))
        && t.checkExpect(listExampleD.draw(scene),
            emptyList.draw(scene).placeImageXY(new TextImage("rain", Color.black), 30, 30)
                .placeImageXY(new TextImage("Hello", Color.black), 20, 40)
                .placeImageXY(new TextImage("Computer", Color.black), 0, 0)
                .placeImageXY(new TextImage("ball", Color.black), 0, 0)
                .placeImageXY(new TextImage("world", Color.black), 10, 10));
  }

  // tests the placeImage function in the classes ActiveWord and InactiveWord
  boolean testPlaceImage(Tester t) {
    WorldScene scene = new WorldScene(100, 100);
    return t.checkExpect(this.exampleWordA.placeImage(scene, active, inactive),
        new WorldScene(scene.width, scene.height).placeImageXY(new TextImage("Hello", Color.red),
            20, 40))
        && t.checkExpect(this.exampleWordF.placeImage(scene, active, inactive),
            new WorldScene(scene.width, scene.height).placeImageXY(new TextImage(" ", Color.red),
                30, 30))
        && t.checkExpect(this.exampleWordC.placeImage(scene, active, inactive),
            new WorldScene(scene.width, scene.height)
                .placeImageXY(new TextImage("Computer", Color.black), 0, 0));
  }

  // tests the letterMatch function in the classes MtLoWord and ConsLoWord
  boolean testLetterMatch(Tester t) {
    return t.checkExpect(this.exampleWordA.letterMatch("h"), false)
        && t.checkExpect(this.exampleWordC.letterMatch("c"), false)
        && t.checkExpect(this.exampleWordC.letterMatch("u"), false)
        && t.checkExpect(this.exampleWordA.letterMatch("e"), false)
        && t.checkExpect(this.exampleWordF.letterMatch(""), true);
  }

  // tests the deleteWord function in the classes MtLoWord and ConsLoWord
  boolean testDeleteWord(Tester t) {
    return t.checkExpect(this.exampleWordA.deleteWord(), new ActiveWord("ello", 20, 40))
        && t.checkExpect(this.exampleWordC.deleteWord(), new ActiveWord("omputer", 0, 0))
        && t.checkExpect(this.exampleWordD.deleteWord(), new InactiveWord("all", 0, 0));
  }

  // tests the makeScene function in the ZTypeWorld Class
  boolean testMakeScene(Tester t) {
    return t.checkExpect(this.world1.makeScene(), listExampleA.draw(new WorldScene(500, 500)))
        && t.checkExpect(this.world2.makeScene(), listExampleC.draw(new WorldScene(500, 500)))
        && t.checkExpect(this.world3.makeScene(), listExampleI.draw(new WorldScene(500, 500)));
  }

  // tests the generate random word function in the Utils Class
  boolean testUtilRandomWord(Tester t) {
    return t.checkExpect(u1.randomWord(), "eomtth");
  }

  // tests the moveWord function in the classes MtLoWord and ConsLoWord
  boolean testMoveWord(Tester t) {
    return t.checkExpect(exampleWordA.moveWord(), new ActiveWord("Hello", 20, 50))
        && t.checkExpect(exampleWordB.moveWord(), new InactiveWord("world", 10, 20))
        && t.checkExpect(exampleWordD.moveWord(), new InactiveWord("ball", 0, 10));
  }

  // tests the move function in the classes MtLoWord and ConsLoWord
  boolean testMove(Tester t) {
    return t.checkExpect(listExampleA.move(),
        new ConsLoWord(new ActiveWord("Hello", 20, 50), this.emptyList))
        && t.checkExpect(listExampleE.move(), new ConsLoWord(new ActiveWord("Hello", 20, 50),
            new ConsLoWord(new InactiveWord("world", 10, 20), new ConsLoWord(
                new ActiveWord("rain", 30, 40),
                new ConsLoWord(new ActiveWord("Hello", 20, 50),
                    new ConsLoWord(new ActiveWord("Computer", 0, 10),
                        new ConsLoWord(new InactiveWord("ball", 0, 10),
                            new ConsLoWord(new InactiveWord("world", 10, 20), this.emptyList))))))))
        && t.checkExpect(listExampleG.move(), new ConsLoWord(new ActiveWord("Computer", 0, 10),
            new ConsLoWord(new InactiveWord("", 0, 10), this.emptyList)));
  }

  // tests the method filterOutEmpties in the classes MtLoWord and ConsLoWord
  boolean testFilterOutEmpties(Tester t) {
    return t.checkExpect(this.emptyList.filterOutEmpties(), this.emptyList)
        && t.checkExpect(this.listExampleA.filterOutEmpties(), this.listExampleA)
        && t.checkExpect(this.listExampleB.filterOutEmpties(),
            new ConsLoWord(this.exampleWordB, this.emptyList))
        && t.checkExpect(this.listExampleC.filterOutEmpties(), this.listExampleC)
        && t.checkExpect(this.listExampleD.filterOutEmpties(), this.listExampleD)
        && t.checkExpect(this.listExampleG.filterOutEmpties(),
            new ConsLoWord(this.exampleWordC, this.emptyList))
        && t.checkExpect(this.listExampleM.filterOutEmpties(),
            new ConsLoWord(this.exampleWordA, this.emptyList));
  }

  // tests the emptyCheck function in the classes MtLoWord and ConsLoWord
  boolean testEmptyCheck(Tester t) {
    return t.checkExpect(this.exampleWordA.emptyCheck(), false)
        && t.checkExpect(this.exampleWordF.emptyCheck(), true)
        && t.checkExpect(this.exampleWordC.emptyCheck(), false);
  }

  // tests the makeAndRemove function in the classes MtLoWord and ConsLoWord
  boolean testmakeAndRemove(Tester t) {
    return t.checkExpect(this.listExampleA.makeAndRemove("ello"),
        new ConsLoWord(new ActiveWord("Hello", 20, 40), this.emptyList))
        && t.checkExpect(this.listExampleA.makeAndRemove("H"),
            new ConsLoWord(new ActiveWord("ello", 20, 40), this.emptyList))
        && t.checkExpect(this.listExampleB.makeAndRemove("W"),
            new ConsLoWord(new InactiveWord("world", 10, 10), this.emptyList))
        && t.checkExpect(this.listExampleB.makeAndRemove("t"),
            new ConsLoWord(new InactiveWord("world", 10, 10), this.emptyList))
        && t.checkExpect(this.listExampleD.makeAndRemove("c"),
            new ConsLoWord(new ActiveWord("ball", 50, 20),
                new ConsLoWord(this.exampleWordD, this.listExampleB)))
        && t.checkExpect(this.listExampleD.makeAndRemove("w"), new ConsLoWord(
            new ActiveWord("world", 50, 20), new ConsLoWord(this.exampleWordD, this.listExampleB)));
  }

  // tests the removeFirst function in the classes MtLoWord and ConsLoWord
  boolean testRemoveFirst(Tester t) {
    return t.checkExpect(this.exampleWordA.removeFirst(), new ActiveWord("ello", 20, 40))
        && t.checkExpect(this.exampleWordA.removeFirst(), new ActiveWord("ello", 20, 40))
        && t.checkExpect(this.exampleWordB.removeFirst(), new ActiveWord("orld", 10, 10));
  }

  // tests the isActive function in the classes Active and Inactive Word
  boolean testIsActive(Tester t) {
    return t.checkExpect(this.exampleWordF.isActive(), false)
        && t.checkExpect(this.exampleWordA.isActive(), true)
        && t.checkExpect(this.exampleWordB.isActive(), false);
  }

  // tests the activeList function in the classes MtLoWord and ConsLoWord
  boolean testActiveList(Tester t) {
    return t.checkExpect(this.listExampleA.activeList(), true)
        && t.checkExpect(this.listExampleB.activeList(), false)
        && t.checkExpect(this.emptyList.activeList(), false);
  }

  // tests the wordOver function in the classes MtLoWord and ConsLoWord
  boolean testWordOver(Tester t) {
    return t.checkExpect(this.listExampleF.wordOver(), false)
        && t.checkExpect(this.listExampleA.wordOver(), false)
        && t.checkExpect(this.listExampleD.wordOver(), false);
  }

  // tests the gameOver function in the classes ActiveWord and InactiveWord
  boolean testGameOver(Tester t) {
    return t.checkExpect(this.exampleWordD.gameOver(), false)
        && t.checkExpect(this.exampleWordB.gameOver(), false);
  }

  // tests the makeWorldScene function
  boolean testMakeWorldScene(Tester t) {
    return world3.bigBang(height, SCREEN_WIDTH, .5);
  }
}