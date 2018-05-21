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

Screen s;
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

public void setup(){
  // Set up processing vars
  
  background(255);
  SoundFile soundSample1 = new SoundFile(this, "c5.mp3");
  SoundFile soundSample2 = new SoundFile(this, "d5.mp3");
  SoundFile soundSample3 = new SoundFile(this, "e5.mp3");
  SoundFile soundSample4 = new SoundFile(this, "f5.mp3");
  sample = new Samples(soundSample1, soundSample2, soundSample3, soundSample4);
  sample.hitSound1.play();
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


  s = new GameplayScreen();
  enabledNotes = new ArrayList();

  // double time, int note, double duration, double scrollSpeed, Samples sample
  for(int i = 0; i < 100; i++){
    s.addObject(new Note(i * 250, floor(random(1, 5)), 0, 100, sample));
  }
  s.initObjects();
  counter = 1;
  startTime = millis();
}

public void draw(){
  s.update();
  s.display();
  s.renderObjects();
}

public void keyPressed(){
  s.pressed(key);
}
public class BeatMap{
  Note[] notes;
  
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
