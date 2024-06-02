import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Player {
    private final List<Playlist> playlists;

    public Player() {
        this.playlists = new ArrayList<>();
    }

    public List<Playlist> getPlaylists() {
        return new ArrayList<>(playlists);
    }
    private int currentPlaylistIndex = 0;
    private int currentSongIndex = 0;

    public void showSongList() {
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

    public void playSong(int playlistIndex, int songIndex) {
        if (playlistIndex >= 0 && playlistIndex < playlists.size()) {
            Playlist playlist = playlists.get(playlistIndex);
            if (songIndex >= 0 && songIndex < playlist.getSongs().size()) {
                Song song = playlist.getSongs().get(songIndex);
                playPlaylist(songIndex);
                System.out.println("Воспроизведение: " + song.getTitle() + " - " + song.getArtist());
                currentSongIndex = songIndex;
            } else {
                System.out.println("Ошибка: неправильный индекс плейлиста");//JOptionPane.showMessageDialog(frame, "Ошибка: неправильный индекс песни", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Ошибка");//JOptionPane.showMessageDialog(frame, "Ошибка: неправильный индекс плейлиста", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Имя плейлиста не может быть пустым");
        }
        for (Playlist existingPlaylist : playlists) {
            if (existingPlaylist.getName().equals(name)) {
                throw new IllegalArgumentException("Плейлист с таким именем уже существует");
            }
        }
        playlists.add(new Playlist(name));
    }

    public boolean loadPlaylist(int index) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(System.getProperty("user.home") + "/Desktop/playlist" + index + ".dat"))) {
            Playlist playlist = (Playlist) in.readObject();
            playlists.add(playlist);
            return true;
        } catch (IOException | ClassNotFoundException e) {
            return false;
        }

    }

    public boolean savePlaylist(int index) {
        if (index >= 0 && index < playlists.size()) {
            Playlist playlist = playlists.get(index);
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(System.getProperty("user.home") + "/Desktop/playlist" + index + ".dat"))) {
                out.writeObject(playlist);
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean deletePlaylist(int index) {
        if (index >= 0 && index < playlists.size()) {
            playlists.remove(index);
            return true;
        } else {
            return false;
        }
    }

    public boolean addSongToPlaylist(int playlistIndex, String title, String artist, int duration) {
        if (playlistIndex >= 0 && playlistIndex < playlists.size()) {
            Playlist playlist = playlists.get(playlistIndex);
            playlist.addSong(new Song(title, artist, duration));
            return true;
        } else {
            return false;
        }
    }

    public boolean removeSongFromPlaylist(int playlistIndex, int songIndex) {
        if (playlistIndex >= 0 && playlistIndex < playlists.size()) {
            Playlist playlist = playlists.get(playlistIndex);
            if (songIndex >= 0 && songIndex < playlist.getSongs().size()) {
                playlist.removeSong(songIndex);
                return true;
            }
        }
        return false;
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

    public List<Song> getPlaylistSongs(int index) {
        if (index < 0 || index >= playlists.size()) {
            throw new IndexOutOfBoundsException("Индекс плейлиста вне диапазона");
        }
        Playlist playlist = playlists.get(index);
        return playlist.getSongs();
    }

}
