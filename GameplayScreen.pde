public class GameplayScreen extends Screen{

  color outsideColor = unhex(skin.getString("outsideColor")) + unhex("FF000000");
  color laneColor = unhex(skin.getString("laneColor")) + unhex("FF000000");
  color judgingLineColor = unhex(skin.getString("judgingLine")) + unhex("10000000");

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
    rect(1 * width / 6, height - stepmania.target, 4 * width / 6, 10);
    fill(255);
    text(stepmania.score, 10, 10);
  }

  public int isDone(){
    return 1;
  }

  public void pressed(char c){
    for(Note n : stepmania.enabledNotes){
      if(c == stepmania.key1 && n.note == 1 && n.canClick()){
        n.clicked();
      }
      if(c == stepmania.key2 && n.note == 2 && n.canClick()){
        n.clicked();
      }
      if(c == stepmania.key3 && n.note == 3 && n.canClick()){
        n.clicked();
      }
      if(c == stepmania.key4 && n.note == 4 && n.canClick()){
        n.clicked();
      }
    }
  }
}
