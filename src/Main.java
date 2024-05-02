import java.util.*;

class MusicPlayerApp {
    private static final Player musicPlayer = new Player();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("Меню:");
            System.out.println("1. Создать плейлист");
            System.out.println("2. Воспроизвести плейлист");
            System.out.println("3. Сохранить плейлист");
            System.out.println("4. Загрузить плейлист");
            System.out.println("5. Удалить плейлист");
            System.out.println("6. Добавить песню в плейлист");
            System.out.println("7. Удалить песню из плейлиста");
            System.out.println("8. Воспроизвести предыдущую песню");
            System.out.println("9. Воспроизвести следующую песню");
            System.out.println("10. Повтор текущей песни");
            System.out.println("11. Показать все песни");
            System.out.println("12. Показать песни из плейлиста");
            System.out.println("0. Выйти из приложения");

            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Введите название плейлиста: ");
                    String playlistName = scanner.next();
                    musicPlayer.createPlaylist(playlistName);
                    break;
                case 2:
                    System.out.print("Введите индекс плейлиста: ");
                    int playlistIndex = scanner.nextInt();
                    musicPlayer.playPlaylist(playlistIndex);
                    break;
                case 3:
                    System.out.print("Введите индекс плейлиста: ");
                    int playlistIndexToSave = scanner.nextInt();
                    musicPlayer.savePlaylist(playlistIndexToSave);
                    break;
                case 4:
                    //0_o_-_-_o_0
                    System.out.println("Введите индекс плейлиста для загрузки:");
                    int index = scanner.nextInt();
                    musicPlayer.loadPlaylist(index);
                    break;
                case 5:
                    System.out.print("Введите индекс плейлиста: ");
                    int playlistIndexToDelete = scanner.nextInt();
                    musicPlayer.deletePlaylist(playlistIndexToDelete);
                    break;
                case 6:
                    System.out.print("Введите индекс плейлиста: ");
                    int playlistIndexToAddSong = scanner.nextInt();
                    System.out.print("Введите название песни: ");
                    String songTitle = scanner.next();
                    System.out.print("Введите исполнителя: ");
                    String songArtist = scanner.next();
                    System.out.print("Введите длительность трека в секундах: ");
                    int songDuration = scanner.nextInt();
                    musicPlayer.addSongToPlaylist(playlistIndexToAddSong, songTitle, songArtist, songDuration);
                    break;
                case 7:
                    System.out.print("Введите индекс плейлиста: ");
                    int playlistIndexToRemoveSong = scanner.nextInt();
                    System.out.print("Введите индекс песни: ");
                    int songIndexToRemove = scanner.nextInt();
                    musicPlayer.removeSongFromPlaylist(playlistIndexToRemoveSong, songIndexToRemove);
                    break;
                case 8:
                    musicPlayer.playPreviousSong();
                    break;
                case 9:
                    musicPlayer.playNextSong();
                    break;
                case 10:
                    musicPlayer.repeatCurrentSong();
                    break;
                case 11:
                    musicPlayer.showSongList();
                    break;
                case 12:
                    System.out.print("Введите индекс плейлиста: ");
                    int playlistIndexX = scanner.nextInt();
                    musicPlayer.showPlayPlaylist(playlistIndexX);
                    break;
                case 0:
                    running = false;
                    System.out.println("До свидания!");
                    break;
                default:
                    System.out.println("Некорректный выбор");
                    break;
            }
        }
        scanner.close();
    }
}
