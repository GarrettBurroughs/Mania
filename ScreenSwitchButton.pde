public class ScreenSwitchButton extends Button
{
  Screen targetScreen;
  public ScreenSwitchButton(int x, int y, String text, Screen targetScreen )
  {
    super( x,  y,  text );
    this.targetScreen = targetScreen;
  }
  public void click()
  {
    if(mouseX<=x+boxWidth/2 && mouseX>=x-boxWidth/2 && mouseY<=y+boxHeight/2 && mouseY>=y-boxHeight/2)
    {
        stepmania.currentScreen = targetScreen;
    }
  }
}
