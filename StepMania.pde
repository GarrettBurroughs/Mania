import processing.sound.*;

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
public static int score = 0;

void setup(){
  // Set up processing vars
  size(400, 600);
  background(255);
  frameRate(144);
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


  currentScreen = new StartScreen();
  enabledNotes = new ArrayList();

  // double time, int note, double duration, double scrollSpeed, Samples sample
  // for(int i = 0; i < 100; i++){
  //   currentScreen.addObject(new Note(i * 250, floor(random(1, 5)), 0, 100, sample));
  // }
  currentScreen.initObjects();
  counter = 1;
  startTime = millis();
  println(startTime);
  BeatMap test = new BeatMap(this, "DejaVu", "HD.json");
  for(Note n : test.getNotes()){
    currentScreen.addObject(n);
  }
}

void draw(){
  currentScreen.update();
  currentScreen.display();
  currentScreen.renderObjects();
  println(score);
}

void keyPressed(){
  currentScreen.pressed(key);
}

public void playSong(){
  println("Thread Running");
  currentSong.play();
  while (true){
    //currentSong.amp(1);
  }
}
