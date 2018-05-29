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
