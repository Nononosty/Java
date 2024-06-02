import java.io.Serializable;


public class Song implements Serializable {
    private final String title;
    private final String artist;
    private final int duration;

    public Song(String title, String artist, int duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getDuration() {
        return duration;
    }



    @Override
    public String toString() {
        return title + " - " + artist + " - " + duration;
    }
}
