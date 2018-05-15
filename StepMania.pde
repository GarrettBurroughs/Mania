// STEP MANIA REPLICA

Screen s;

void setup(){
  size(400, 600);
  s = new GameplayScreen();
  background(255);
}

void draw(){
  s.render();
}
