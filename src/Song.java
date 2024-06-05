import java.io.Serializable;

public class Song implements Serializable {
    private final String title;
    private final String artist;
    private final int duration;
    private String filePath;

    public Song(String title, String artist, int duration, String filePath) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.filePath = filePath;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return title + " - " + artist + " - " + duration + " (Файл: " + filePath + ")";
    }
}
