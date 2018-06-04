public class Note implements GameObject{
  double time; // The time that the note should appear
  int note; // The note variable changes the sound and where the note appears
  double duration; // 1 if single beat, longer for held
  double startTime; // The time that the note
  Samples sample; // Soundset for notes
  float position; // Y coordinate of the note
  float speed; // How fast the note should move down the screen
  int size = 10; // Size of a standard note
  double scrollSpeed;
  boolean enabled = false; // True when on screen
  boolean loaded = false;

  // Load note colors from skin
  color c1 = unhex(skin.getString("noteColor1")) + unhex("FF000000");
  color c2 = unhex(skin.getString("noteColor2")) + unhex("FF000000");
  color c3 = unhex(skin.getString("noteColor3")) + unhex("FF000000");
  color c4 = unhex(skin.getString("noteColor4")) + unhex("FF000000");

  public Note(double time, int note, double duration, double scrollSpeed, Samples sample){
    this.time = time;
    this.note = note;
    this.duration = duration;
    this.startTime = time + duration - 1 / scrollSpeed * 1000;
    this.sample = sample;
    this.scrollSpeed = scrollSpeed;
    if(duration > 1){
      size = (int)duration;
    }
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
      rect(((note) / 6.0) * width, position, width/6, size);
    }
  }

  public double accuracy(){
    return abs(position - (target + width));
  }

  public boolean canClick(){
    return abs(position - (target + width)) < StepMania.judgingScale;
  }
}
