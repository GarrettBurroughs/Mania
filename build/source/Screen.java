import java.util.ArrayList;


public abstract class Screen{
  private ArrayList<GameObject> objects = new ArrayList<>();
  public abstract void screenUpdate();
  public abstract void display();

  public Screen(){
  }

  public void update(){
    screenUpdate();
    for(GameObject o : objects){
      o.update();
    }
  }

  public void renderObjects(){
    for(GameObject o : objects){
      o.render();
    }
  }

  public Screen addObject(GameObject o){
    objects.add(o);
    return this;
  }

  public Screen addObjects(GameObject ... objects){
    for(GameObject o : objects){
      this.objects.add(o);
    }
    return this;
  }

  public ArrayList<GameObject> getObjects(){
    return objects;
  }

  /**
   * @return negative (<0) if not done, and the index of the desired screen if done
   */
  public abstract int isDone();


  public abstract void pressed(char c);
}