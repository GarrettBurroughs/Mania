class StartScreen extends Screen{

  public StartScreen(){
    addObjects(
        new ScreenSwitchButton(width/2, height/2, "Start", new GameplayScreen())
      );
  }

  public void screenUpdate(){
    fill(38, 10, 255);
  }

  public void display(){

  }

  public int isDone(){
    return -1;
  }

  public void pressed(char c){

  }
}
