import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class StepMania extends PApplet {



// STEP MANIA REPLICA

static Screen currentScreen;
JSONObject config;
JSONObject skin;
int counter;
static double startTime;
static char key1;
static char key2;
static char key3;
static char key4;
static final float target = 100;
static double judgingScale = 100;
static ArrayList<Note> enabledNotes;
public Samples sample;
public static SoundFile currentSong;

public void setup(){
  // Set up processing vars
  
  background(255);
  SoundFile soundSample1 = new SoundFile(this, "soft-hitwhistle.mp3");
  SoundFile soundSample2 = new SoundFile(this, "soft-hitfinish.wav");
  //SoundFile song = new SoundFile(this, "beatmaps/TestBeatmap/TestBeatmapSong.mp3");
  // SoundFile soundSample2 = new SoundFile(this, "d5.mp3");
  // SoundFile soundSample3 = new SoundFile(this, "e5.mp3");
  // SoundFile soundSample4 = new SoundFile(this, "f5.mp3");
  sample = new Samples(soundSample2, soundSample1, soundSample1, soundSample2);
  // sample.hitSound1.play();
  // Load skin and configuation files
  config = loadJSONObject("config/config.json");
  String skinFile = config.getString("skin");
  skin = loadJSONObject(skinFile);


  // Load keys
  String keyConfig = config.getString("keyConfig");
  key1 = keyConfig.charAt(0);
  key2 = keyConfig.charAt(1);
  key3 = keyConfig.charAt(2);
  key4 = keyConfig.charAt(3);


  currentScreen = new GameplayScreen();
  enabledNotes = new ArrayList();

  // double time, int note, double duration, double scrollSpeed, Samples sample
  // for(int i = 0; i < 100; i++){
  //   currentScreen.addObject(new Note(i * 250, floor(random(1, 5)), 0, 100, sample));
  // }
  currentScreen.initObjects();
  counter = 1;
  startTime = millis();
  BeatMap test = new BeatMap(this, "TestBeatmap", "hard.json");
  for(Note n : test.getNotes()){
    currentScreen.addObject(n);
  }
}

public void draw(){
  currentScreen.update();
  currentScreen.display();
  currentScreen.renderObjects();
}

public void keyPressed(){
  currentScreen.pressed(key);
}

public void playSong(){
  println("Thread Running");
  currentSong.play();
}
public class BeatMap{
  SoundFile song;
  ArrayList<Note> notes;
  String name;
  String artist;
  int previewPoint;
  int scrollSpeed = 100;

  public BeatMap(StepMania parent, String name, String difficulty){
    notes = new ArrayList();
    String path = "beatmaps/" + name + "/";
    JSONObject mapReader = loadJSONObject(path + "beatmap.json");
    JSONObject diffReader = loadJSONObject(path + difficulty);
    song = new SoundFile(parent, path + mapReader.getString("SoundFile"));
    StepMania.currentSong = song;
    thread("playSong");
    println(path + mapReader.getString("SoundFile"));
    name = mapReader.getString("SongName");
    artist = mapReader.getString("Artist");
    previewPoint = mapReader.getInt("previewPoint");
    Samples s = new Samples(
      new SoundFile(parent, path + mapReader.getString("Hitsound1")),
      new SoundFile(parent, path + mapReader.getString("Hitsound2")),
      new SoundFile(parent, path + mapReader.getString("Hitsound3")),
      new SoundFile(parent, path + mapReader.getString("Hitsound4"))
    );
    JSONArray beatmapNotes = diffReader.getJSONArray("notes");
    for (int i = 0; i < beatmapNotes.size(); i++) {
      JSONObject beatmapNote = beatmapNotes.getJSONObject(i);
      // double time, int note, double duration, double scrollSpeed, Samples sample
      notes.add(new Note(
        beatmapNote.getInt("offset"),
        beatmapNote.getInt("key"),
        0, // TODO : IMPLEMENT DURATION
        scrollSpeed,
        s
        ));
    }
  }

  public Note[] getNotes(){
    return notes.toArray(new Note[notes.size()]);
  }
}
public abstract class Button implements GameObject
{
   String text;
   int x;
   int y;
   int boxWidth;
   int boxHeight;
   int fontSize = 24;
   Screen targetScreen;
   PFont f;

   public Button(int x, int y, String text )
   {
     this.x = x;
     this.y = y;
     this.text = text;
     //this.targetScreen = targetScreen;
     calcBoxDimensions();
   }

   public void calcBoxDimensions()
   {
     textFont(f, fontSize);
     boxWidth = (int) textWidth(text) + 20;
     boxHeight = fontSize + 16 ;
   }

   public void initialize()
   {

   }

  public void update()
  {

  }

  public void click()
  {
     //<>//
  }

  @Override
  public void render(){
    strokeWeight(1);
    stroke(0);
    rectMode(CENTER);
    textAlign(CENTER);
    textFont(f, fontSize);
    if(mouseX<=x+boxWidth/2 && mouseX>=x-boxWidth/2 && mouseY<=y+boxHeight/2 && mouseY>=y-boxHeight/2)
    {
      fill(50);
    }
    else
    {
        fill(127);
    }
    rect(x, y, boxWidth, boxHeight);
    fill(255);
    text(text, x, y + 8);
  }

}
public class GameplayScreen extends Screen{

  int outsideColor = unhex(skin.getString("outsideColor")) + unhex("FF000000");
  int laneColor = unhex(skin.getString("laneColor")) + unhex("FF000000");
  int judgingLineColor = unhex(skin.getString("judgingLine")) + unhex("10000000");

  public void screenUpdate(){

  }

  public void display(){
    fill(outsideColor);
    rect(0 * width / 6, 0, width / 6, height);
    fill(laneColor);
    rect(1 * width / 6, 0, width / 6, height);
    rect(2 * width / 6, 0, width / 6, height);
    rect(3 * width / 6, 0, width / 6, height);
    rect(4 * width / 6, 0, width / 6, height);
    fill(outsideColor);
    rect(5 * width / 6, 0, width / 6, height);
    fill(judgingLineColor);
    rect(1 * width / 6, height - StepMania.target, 4 * width / 6, 10);
  }

  public int isDone(){
    return 1;
  }

  public void pressed(char c){
    for(Note n : StepMania.enabledNotes){
      if(c == StepMania.key1 && n.note == 1 && n.canClick()){
        n.clicked();
      }
      if(c == StepMania.key2 && n.note == 2 && n.canClick()){
        n.clicked();
      }
      if(c == StepMania.key3 && n.note == 3 && n.canClick()){
        n.clicked();
      }
      if(c == StepMania.key4 && n.note == 4 && n.canClick()){
        n.clicked();
      }
    }
  }
}
public class Note implements GameObject{
  double time; // The time that the note should appear
  int note; // The note variable changes the sound and where the note appears
  double duration; // 1 if single beat, longer for held
  double startTime; // The time that the note
  Samples sample; // Soundset for notes
  float position; // Y coordinate of the note
  float speed; // How fast the note should move down the screen
  final int size = 10; // Size of a standard note
  double scrollSpeed;
  boolean enabled = false; // True when on screen
  boolean loaded = false;

  // Load note colors from skin
  int c1 = unhex(skin.getString("noteColor1")) + unhex("FF000000");
  int c2 = unhex(skin.getString("noteColor2")) + unhex("FF000000");
  int c3 = unhex(skin.getString("noteColor3")) + unhex("FF000000");
  int c4 = unhex(skin.getString("noteColor4")) + unhex("FF000000");

  public Note(double time, int note, double duration, double scrollSpeed, Samples sample){
    this.time = time;
    this.note = note;
    this.duration = duration;
    this.startTime = time - 1 / scrollSpeed * 1000;
    this.sample = sample;
    this.scrollSpeed = scrollSpeed;
    initialize();
  }

  public void initialize(){
    speed = (float)(scrollSpeed / frameRate);
    println(speed);
    position = 0;

  }

  public void update(){
    if(millis() - StepMania.startTime > this.startTime && !loaded){
      enabled = true;
      position = 0;
      loaded = true;
      StepMania.enabledNotes.add(this);
    }
    if(enabled){
      position += speed;
    }
    if(position > height){
      enabled = false;
      enabledNotes.remove(this);
    }
  }

  public void click(){

  }

  public void clicked(){
    enabled = false;
    if(sample != null){
        switch(note){
          case 1:
            sample.hitSound1.play();
            break;
          case 2:
            sample.hitSound2.play();
            break;
          case 3:
            sample.hitSound3.play();
            break;
          case 4:
            sample.hitSound4.play();
            break;
          default:
            break;
      }
    }
  }

  public void render(){
    if(enabled){
      switch(note){
        case 1:
          fill(c1);
          break;
        case 2:
          fill(c2);
          break;
        case 3:
          fill(c3);
          break;
        case 4:
          fill(c4);
          break;
        default:
          fill(255);
          break;
      }
      if(canClick()){
        fill(255, 255, 0);
      }
      rect(((note) / 6.0f) * width, position, width/6, size);
    }
  }

  public double accuracy(){
    return abs(position - (target + width));
  }

  public boolean canClick(){
    return abs(position - (target + width)) < StepMania.judgingScale;
  }
}
public class Samples{
  public SoundFile hitSound1;
  public SoundFile hitSound2;
  public SoundFile hitSound3;
  public SoundFile hitSound4;
  public Samples(SoundFile hitSound1, SoundFile hitSound2, SoundFile hitSound3, SoundFile hitSound4){
    this.hitSound1 = hitSound1;
    this.hitSound2 = hitSound2;
    this.hitSound3 = hitSound3;
    this.hitSound4 = hitSound4;
  }
}
public class screenSwitchButton extends Button
{
  Screen targetScreen;
  public screenSwitchButton(int x, int y, String text, Screen targetScreen )
  {
    super( x,  y,  text );
    this.targetScreen = targetScreen;
  }
  public void click()
  {
    if(mouseX<=x+boxWidth/2 && mouseX>=x-boxWidth/2 && mouseY<=y+boxHeight/2 && mouseY>=y-boxHeight/2)
    {
        StepMania.currentScreen = targetScreen;
    }
  }
}
class SongSelectScreen extends Screen{

  public void screenUpdate(){

  }

  public void display(){

  }

  public int isDone(){
    return -1;
  }

  public void pressed(char c){

  }
}
class SongSelection implements GameObject{
  BeatMap m;
  int index;
  final int size = 150;
  final int selectedSize = 200;
  boolean selected = false;
  final int thickness = 40;
  boolean playing = false;


  public void initialize(){

  }

  public void update(){
    if(selected && !playing){
      playing = true;

    }
    if(!selected){
      playing = false;
      m.song.stop();
    }
  }

  public void click(){}

  public void render(){
    if(selected){
      rect(width - selectedSize, index * thickness, selectedSize, thickness);
    }else{
      rect(width - size, index * size, size, thickness);
    }
  }
}
class StartScreen extends Screen{

  public void screenUpdate(){

  }

  public void display(){
    
  }

  public int isDone(){
    return -1;
  }

  public void pressed(char c){

  }
}
  public void settings() {  size(400, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "StepMania" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
