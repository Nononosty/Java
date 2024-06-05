import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MusicPlayerGUI {
    private JFrame frame;
    private JList<String> songList;
    private DefaultListModel<String> playlistModel;
    private DefaultListModel<String> songModel;
    private final Player musicPlayer;
    private int currentPlaylistIndex = -1;
    private int currentSongIndex = -1;

    public MusicPlayerGUI(Player player) {
        this.musicPlayer = player;
        initGUI();
    }

    private void initGUI() {
        frame = new JFrame("Music Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 500);

        JPanel panel = new JPanel();
        JButton playButton = new JButton("PLAY");
        JButton createPlaylistButton = new JButton("Create Playlist");
        JButton deletePlaylistButton = new JButton("Delete Playlist");
        JButton openButton = new JButton("Load");
        JButton saveButton = new JButton("Save");
        JButton addSongButton = new JButton("Add Song");
        JButton removeSongButton = new JButton("Remove Song");
        JButton nextSongButton = new JButton("Next Song");
        JButton prevSongButton = new JButton("Previous Song");
        JButton repSongButton = new JButton("Repeat Song");
        JButton showAllSongs = new JButton("All Songs");
        JButton showSongsPlaylist = new JButton("Playlist Songs");

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter playlist index to play: ");
                if (input != null && !input.trim().isEmpty()) {
                    try {
                        int playlistIndex = Integer.parseInt(input);
                        if (playlistIndex >= 0 && playlistIndex < musicPlayer.getPlaylists().size()) {
                            currentPlaylistIndex = playlistIndex;
                            currentSongIndex = 0;
                            Playlist playlist = musicPlayer.getPlaylists().get(playlistIndex);
                            songModel.clear();
                            if (!playlist.getSongs().isEmpty()) {
                                Song firstSong = playlist.getSongs().getFirst();
                                songModel.addElement("Play playlist " + playlist.getName() + ":");
                                songModel.addElement(firstSong.getTitle() + " - " + firstSong.getArtist() + " - " + firstSong.getDuration() + " сек");
                                musicPlayer.play(playlistIndex, 0);
                            } else {
                                songModel.addElement("Playlist " + playlist.getName() + " is empty");
                            }
                            songList.setModel(songModel);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Error: Wrong playlist index.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Error: It's not a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        nextSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (currentPlaylistIndex != -1 && currentSongIndex != -1) {
                    musicPlayer.stop();
                    currentSongIndex++;
                    if (currentSongIndex >= musicPlayer.getPlaylists().get(currentPlaylistIndex).getSongs().size()) {
                        currentSongIndex = 0;
                    }
                    Playlist playlist = musicPlayer.getPlaylists().get(currentPlaylistIndex);
                    songModel.clear();
                    if (!playlist.getSongs().isEmpty()) {
                        Song currentSong = musicPlayer.getPlaylists().get(currentPlaylistIndex).getSongs().get(currentSongIndex);
                        songModel.addElement("Playlist " + playlist.getName());
                        songModel.addElement(currentSong.getTitle() + " - " + currentSong.getArtist() + " - " + currentSong.getDuration() + " сек");
                        songList.setModel(songModel);

                        musicPlayer.play(currentPlaylistIndex, currentSongIndex);
                    } else {
                        songModel.addElement("Playlist " + playlist.getName() + " is empty");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Playlist not selected", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        prevSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPlaylistIndex != -1 && currentSongIndex != -1) {
                    musicPlayer.stop();
                    currentSongIndex--;
                    if (currentSongIndex < 0) {
                        currentSongIndex = musicPlayer.getPlaylists().get(currentPlaylistIndex).getSongs().size() - 1;
                    }
                    Playlist playlist = musicPlayer.getPlaylists().get(currentPlaylistIndex);
                    songModel.clear();
                    songModel.addElement("Playlist " + playlist.getName());
                    Song currentSong = playlist.getSongs().get(currentSongIndex);
                    songModel.addElement(currentSong.getTitle() + " - " + currentSong.getArtist() + " - " + currentSong.getDuration() + " сек");
                    songList.setModel(songModel);
                    musicPlayer.play(currentPlaylistIndex, currentSongIndex);
                } else {
                    JOptionPane.showMessageDialog(frame, "Playlist not selected", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        repSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPlaylistIndex != -1 && currentSongIndex != -1) {
                    Playlist playlist = musicPlayer.getPlaylists().get(currentPlaylistIndex);
                    songModel.clear();
                    songModel.addElement("Playlist " + playlist.getName());
                    Song currentSong = playlist.getSongs().get(currentSongIndex);
                    songModel.addElement(currentSong.getTitle() + " - " + currentSong.getArtist() + " - " + currentSong.getDuration() + " сек");
                    songList.setModel(songModel);
                    musicPlayer.play(currentPlaylistIndex, currentSongIndex);
                } else {
                    JOptionPane.showMessageDialog(frame, "Song not selected", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String indexInput = JOptionPane.showInputDialog(frame, "Enter playlist index to load: ");
                String nameInput = JOptionPane.showInputDialog(frame, "Enter playlist name: ");
                if (indexInput != null && !indexInput.trim().isEmpty() && nameInput != null && !nameInput.trim().isEmpty()) {
                    try {
                        int currentPlaylistIndex = Integer.parseInt(indexInput);
                        boolean isLoaded = musicPlayer.loadPlaylist(currentPlaylistIndex, nameInput);
                        if (isLoaded) {
                            JOptionPane.showMessageDialog(frame, "Playlist '" + nameInput + "' was loaded.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Error: Could not load the playlist.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Error: Index is not a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Error: Missing information.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        saveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter playlist index to save:");
                if (input != null && !input.trim().isEmpty()) {
                    try {
                        int currentPlaylistIndex = Integer.parseInt(input);
                        boolean isSaved = musicPlayer.savePlaylistWithPaths(currentPlaylistIndex);
                        if (isSaved) {
                            JOptionPane.showMessageDialog(frame, "Playlist was saved.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Error: Wrong playlist index.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Error: It's not a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        showSongsPlaylist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter playlist index to show songs list: ");
                if (input != null && !input.trim().isEmpty()) {
                    try {
                        int currentPlaylistIndex = Integer.parseInt(input);
                        if (currentPlaylistIndex >= 0 && currentPlaylistIndex < musicPlayer.getPlaylists().size()) {
                            Playlist selectedPlaylist = musicPlayer.getPlaylists().get(currentPlaylistIndex);
                            songModel.clear();
                            if (!selectedPlaylist.getSongs().isEmpty()) {
                                songModel.addElement("Playlist " + selectedPlaylist.getName() + ":");
                                for (Song song : selectedPlaylist.getSongs()) {
                                    songModel.addElement(song.getTitle() + " - " + song.getArtist() + " - " + song.getDuration() + " сек");
                                }
                            } else {
                                songModel.addElement("Playlist " + selectedPlaylist.getName() + " is empty");
                            }
                            songList.setModel(songModel);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Error: Wrong playlist index.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Error: It's not a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        showAllSongs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                songModel.clear();
                List<Playlist> playlists = musicPlayer.getPlaylists();
                if (playlists.isEmpty()) {
                    songModel.addElement("All playlists are empty");
                } else {
                    for (Playlist playlist : playlists) {
                        if (playlist.getSongs().isEmpty()) {
                            songModel.addElement("Playlist " + playlist.getName() + " is empty");
                        } else {
                            songModel.addElement("Playlist " + playlist.getName() + ":");
                            for (Song song : playlist.getSongs()) {
                                songModel.addElement(song.getTitle() + " - " + song.getArtist() + " - " + song.getDuration() + " сек");
                            }
                        }
                    }
                }
            }
        });

        addSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = JOptionPane.showInputDialog(frame, "Enter playlist index to add song: ");
                    int playlistIndex = Integer.parseInt(input);
                    String titleSong = JOptionPane.showInputDialog(frame, "Enter name of song: ");
                    String artistSong = JOptionPane.showInputDialog(frame, "Enter artist of song: ");
                    String dur = JOptionPane.showInputDialog(frame, "Enter duration of song (in seconds): ");
                    int durSong = Integer.parseInt(dur);
                    String filePath = JOptionPane.showInputDialog(frame, "Enter file path of song: ");
                    boolean isAdded = musicPlayer.addSongToPlaylist(playlistIndex, titleSong, artistSong, durSong, filePath);
                    if (isAdded) {
                        songModel.clear();
                        songModel.addElement("Playlist " + musicPlayer.getPlaylists().get(playlistIndex).getName() + ":");
                        songModel.addElement(titleSong + " - " + artistSong + " - " + durSong + " сек");
                        songList.setModel(songModel);
                        JOptionPane.showMessageDialog(frame, "Song was added.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error: Wrong playlist or song index.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: It's not a number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String inputPlaylist = JOptionPane.showInputDialog(frame, "Enter playlist index to remove song: ");
                    int currentPlaylistIndex = Integer.parseInt(inputPlaylist);
                    String inputSong = JOptionPane.showInputDialog(frame, "Enter song index to remove: ");
                    int songIndex = Integer.parseInt(inputSong);
                    boolean isRemoved = musicPlayer.removeSongFromPlaylist(currentPlaylistIndex, songIndex);
                    if (isRemoved) {
                        List<Song> songs = musicPlayer.getPlaylistSongs(currentPlaylistIndex);
                        Playlist playlist = musicPlayer.getPlaylists().get(currentPlaylistIndex);
                        songModel.clear();
                        songModel.addElement("Playlist " + playlist.getName() + ":");
                        for (Song song : songs) {
                            songModel.addElement(song.getTitle() + " - " + song.getArtist() + " - " + song.getDuration() + " сек");
                        }
                        JOptionPane.showMessageDialog(frame, "Song was deleted from playlist.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error: Wrong playlist or song index.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: It's not a number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        createPlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playlistName = JOptionPane.showInputDialog(frame, "Enter playlist name: ");
                if (playlistName != null && !playlistName.trim().isEmpty()) {
                    boolean exists = false;
                    for (Playlist playlist : musicPlayer.getPlaylists()) {
                        if (playlist.getName().equalsIgnoreCase(playlistName.trim())) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        musicPlayer.createPlaylist(playlistName.trim());
                        playlistModel.clear();
                        songModel.clear();
                        for (Playlist playlist : musicPlayer.getPlaylists()) {
                            playlistModel.addElement("Playlist name: ");
                            playlistModel.addElement(playlist.getName());
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error: Playlist name is already used", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Error: Playlist name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deletePlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter playlist index to delete:");
                try {
                    int playlistIndex = Integer.parseInt(input);
                    boolean isDeleted = musicPlayer.deletePlaylist(playlistIndex);
                    if (isDeleted) {
                        playlistModel.clear();
                        songModel.clear();
                        for (Playlist playlist : musicPlayer.getPlaylists()) {
                            playlistModel.addElement("Playlist: " + playlist.getName());
                        }
                        JOptionPane.showMessageDialog(frame, "Playlist was deleted.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Error: Wrong playlist index.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Error: It's not a number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(playButton);
        panel.add(openButton);
        panel.add(saveButton);
        panel.add(createPlaylistButton);
        panel.add(deletePlaylistButton);
        panel.add(showAllSongs);
        panel.add(showSongsPlaylist);

        playlistModel = new DefaultListModel<>();
        JList<String> playlistList = new JList<>(playlistModel);
        JScrollPane playlistScrollPane = new JScrollPane(playlistList);
        panel.add(playlistScrollPane);

        songModel = new DefaultListModel<>();
        songList = new JList<>(songModel);
        JScrollPane songScrollPane = new JScrollPane(songList);
        panel.add(songScrollPane);

        panel.add(addSongButton);
        panel.add(removeSongButton);
        panel.add(nextSongButton);
        panel.add(prevSongButton);
        panel.add(repSongButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Player player = new Player();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MusicPlayerGUI(player);
            }
        });
    }
}

