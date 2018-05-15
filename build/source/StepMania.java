import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

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

public void setup(){
  
  s = new GameplayScreen();
  background(255);
}

public void draw(){
  s.render();
}
public class BeatMap{

}
public class GameplayScreen implements Screen{
  public void screenUpdate(){

  }

  public void display(){
    fill(0);
    rect(0, 0, width/6, height);
  }

  public int isDone(){

  }

  public void pressed(char c){

  }
}
public class Note{

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
