public abstract class Button implements GameObject
{
   String text;
   int x;
   int y;
   int boxWidth;
   int boxHeight;
   int fontSize = 24;
   Screen targetScreen;
   PFont f = createFont("AgencyFB-Reg-48.ttf", 30);

   public Button(int x, int y, String text )
   {
     this.x = x;
     this.y = y;
     this.text = text;
     //this.targetScreen = targetScreen;
     calcBoxDimensions();
   }

   void calcBoxDimensions()
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
