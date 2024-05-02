import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<Playlist> playlists;

    public Player() {
        this.playlists = new ArrayList<>();
    }
    private int currentPlaylistIndex = 0;
    private int currentSongIndex = 0;

    public void showSongList() {
        //(int i = 0; i < playlists.size(); i++)
        for (Playlist playlist : playlists) {
            System.out.println("Плейлист " + playlist.getName() + ":");
            playlist.showPlaylist();
        }
    }

    public void showPlayPlaylist(int index) {
            if (index >= 0 && index < playlists.size()) {
                Playlist playlist = playlists.get(index);
                if (playlist.getSongs().isEmpty()) {
                    System.out.println("Плейлист пуст");
                } else {
                System.out.println("Воспроизведение плейлиста " + playlist.getName() + ":");
                for (Song song : playlist.getSongs()) {
                    System.out.println("'"+ song.getTitle() +"'"+ " - " + song.getArtist() + " - " + song.getDuration() + " секунд");
                }}
            } else {
                System.out.println("Ошибка: неправильный индекс плейлиста");
            }
        }

    public void playPlaylist(int index) {
        if (index >= 0 && index < playlists.size()) {
            currentPlaylistIndex = index;
            currentSongIndex = 0;
            Playlist playlist = playlists.get(currentPlaylistIndex);
            if (playlist.getSongs().isEmpty()) {
                System.out.println("Плейлист пуст");
            } else {
                System.out.println("Воспроизведение плейлиста " + playlist.getName() + ":");
                playSong();
            }
        } else {
            System.out.println("Ошибка: неправильный индекс плейлиста");
        }
    }

    public void createPlaylist(String name) {
        playlists.add(new Playlist(name));
    }

    public void loadPlaylist(int index) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(System.getProperty("user.home") + "/Desktop/playlist" + index + ".dat"))) {
            Playlist playlist = (Playlist) in.readObject();
            playlists.add(playlist);
            System.out.println("Плейлист успешно загружен.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке плейлиста: " + e.getMessage());
        }
    }

    public void savePlaylist(int index) {
        if (index >= 0 && index < playlists.size()) {
            Playlist playlist = playlists.get(index);
            //           try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("playlist" + index + ".dat"))) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.home") + "/Desktop/playlist" + index + ".dat"))) {
                out.writeObject(playlist);
                System.out.println("Плейлист сохранен успешно.");
            } catch (IOException e) {
                System.out.println("Ошибка при сохранении плейлиста: " + e.getMessage());
            }
        } else {
            System.out.println("Ошибка: неправильный индекс плейлиста");
        }
    }

    public void deletePlaylist(int index) {
        if (index >= 0 && index < playlists.size()) {
            playlists.remove(index);
        } else {
            System.out.println("Ошибка: неправильный индекс плейлиста");
        }
    }

    public void addSongToPlaylist(int playlistIndex, String title, String artist, int duration) {
        if (playlistIndex >= 0 && playlistIndex < playlists.size()) {
            Playlist playlist = playlists.get(playlistIndex);
            playlist.addSong(new Song(title, artist, duration));
        } else {
            System.out.println("Ошибка: неправильный индекс плейлиста");
        }
    }

    public void removeSongFromPlaylist(int playlistIndex, int songIndex) {
        if (playlistIndex >= 0 && playlistIndex < playlists.size()) {
            Playlist playlist = playlists.get(playlistIndex);
            playlist.removeSong(songIndex);
        } else {
            System.out.println("Ошибка: неправильный индекс плейлиста");
        }
    }

    public void playPreviousSong() {
        if (currentSongIndex > 0) {
            currentSongIndex--;
            playSong();
        } else if (currentPlaylistIndex > 0) {
            currentPlaylistIndex--;
            currentSongIndex = playlists.get(currentPlaylistIndex).getSongs().size() - 1;
            playSong();
        } else {
            System.out.println("Это первая песня в первом плейлисте.");
        }
    }

    public void playNextSong() {
        if (currentSongIndex < playlists.get(currentPlaylistIndex).getSongs().size() - 1) {
            currentSongIndex++;
            playSong();
        } else if (currentPlaylistIndex < playlists.size() - 1) {
            currentPlaylistIndex++;
            currentSongIndex = 0;
            playSong();
        } else {
            System.out.println("Это последняя песня в последнем плейлисте.");
        }
    }

    public void repeatCurrentSong() {
        if (playlists.isEmpty() || playlists.get(currentPlaylistIndex).getSongs().isEmpty()) {
            System.out.println("Плейлист пуст.");
            return;
        }
        playSong();
    }

    private void playSong() {

        Playlist currentPlaylist = playlists.get(currentPlaylistIndex);
        Song currentSong = currentPlaylist.getSongs().get(currentSongIndex);
        System.out.println("Играет: " + "'" + currentSong.getTitle() + "'" + " - " + currentSong.getArtist() + " - " + currentSong.getDuration() + " секунд");
    }

}
