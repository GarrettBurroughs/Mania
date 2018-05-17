/**
  * The object interface outlines the basic activity of an object in the bloxris game,
  * All objects have to be rendered to show up on screen in the </code>draw<code>
  */
interface GameObject{
  public void initialize();
  public void update();
  public void click();
  public void render();
}
