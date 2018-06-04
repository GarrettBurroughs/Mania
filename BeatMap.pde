public class BeatMap{
  SoundFile song;
  ArrayList<Note> notes;
  String name;
  String artist;
  int previewPoint;
  int scrollSpeed = 100;

  public BeatMap(StepMania parent, String name, String difficulty){
    notes = new ArrayList();
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
    Samples s = new Samples(
      new SoundFile(parent, path + mapReader.getString("Hitsound1")),
      new SoundFile(parent, path + mapReader.getString("Hitsound2")),
      new SoundFile(parent, path + mapReader.getString("Hitsound3")),
      new SoundFile(parent, path + mapReader.getString("Hitsound4"))
    );
    JSONArray beatmapNotes = diffReader.getJSONArray("notes");
    for (int i = 0; i < beatmapNotes.size(); i++) {
      JSONObject beatmapNote = beatmapNotes.getJSONObject(i);
      // double time, int note, double duration, double scrollSpeed, Samples sample
      notes.add(new Note(
        beatmapNote.getInt("offset"),
        beatmapNote.getInt("key"),
        beatmapNote.getInt("duration"),
        scrollSpeed,
        s
        ));
    }
  }

  public Note[] getNotes(){
    return notes.toArray(new Note[notes.size()]);
  }
}
