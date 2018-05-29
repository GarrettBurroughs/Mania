class SongSelection implements GameObject{
  BeatMap m;
  int index;
  final int size = 150;
  final int selectedSize = 200;
  boolean selected = false;
  final int thickness = 40;
  boolean playing = false;


  public void initialize(){}

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
      rect();
    }else{
        rect(width - size, index * size, size, thickness);
    }
  }
}
