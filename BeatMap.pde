public class BeatMap{
  SoundFile song;
  Note[] notes;
  String name;
  String artist;
  int previewPoint;

  public BeatMap(StepMania parent, String name, String difficulty){
    String path = "beatmaps/" + name + "/";
    JSONObject mapReader = loadJSONObject(path + "beatmap.json");
    JSONObject diffReader = loadJSONObject(path + difficulty);
    song = new SoundFile(parent, path + mapReader.getString("SoundFile"));
    StepMania.currentSong = song;
    thread("playSong");
    println(path + mapReader.getString("SoundFile"));
    name = mapReader.getString("SongName");
    artist = mapReader.getString("Artist");
    previewPoint = mapReader.getInt("previewPoint");
    /*Samples s = new Samples(
      mapReader.getString("soundSample1"),
      mapReader.getString("soundSample2"),
      mapReader.getString("soundSample3"),
      mapReader.getString("soundSample4"),
    );*/
  }
}
