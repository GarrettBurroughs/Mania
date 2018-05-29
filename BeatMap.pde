public class BeatMap{
  SoundFile song;
  Note[] notes;
  String name;
  String artist;
  int previewPoint;

  public BeatMap(String beatmapFile, String difficulty){
    JSONObject mapReader = loadJSONObject(beatmapFile);
    JSONObject diffReader = loadJSONObject(difficulty);
    String filePath = "beatmaps/" + mapReader.getString("BeatMapName");
    song = new SoundFile(new StepMania(), filePath + "/" + mapReader.getString("SoundFile"));
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
